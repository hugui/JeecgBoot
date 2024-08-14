package org.jeecg.modules.system.service.impl;

import java.util.Collections;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.system.model.quark.QuarkNewFileBo;
import org.jeecg.modules.system.queue.QuarkPanFileManager;
import org.jeecg.modules.system.entity.QuarkAccount;
import org.jeecg.modules.system.entity.QuarkSubscribeRecord;
import org.jeecg.modules.system.entity.QuarkSyncRecord;
import org.jeecg.modules.system.mapper.QuarkSubscribeRecordMapper;
import org.jeecg.modules.system.model.quark.QuarkShareDetailBo;
import org.jeecg.modules.system.model.quark.SaveFileBo;
import org.jeecg.modules.system.queue.ExecutorServiceTask;
import org.jeecg.modules.system.service.IQuarkAccountService;
import org.jeecg.modules.system.service.IQuarkSubscribeRecordService;
import org.jeecg.modules.system.service.IQuarkSyncRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 夸克订阅记录
 * @Author: jeecg-boot
 * @Date: 2024-08-02
 * @Version: V1.0
 */
@Slf4j
@Service
public class QuarkSubscribeRecordServiceImpl extends ServiceImpl<QuarkSubscribeRecordMapper, QuarkSubscribeRecord> implements IQuarkSubscribeRecordService {
    @Autowired
    private IQuarkAccountService quarkAccountService;
    @Autowired
    private IQuarkSyncRecordService quarkSyncRecordService;
    @Autowired
    private ExecutorServiceTask executorServiceTask;

    // 定义视频文件扩展名的正则表达式
    String regex = ".*\\.(mp4|avi|mkv|mov|flv|wmv|m4v)$";

    // 创建Pattern对象
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

    @Override
    public void saveRecord(QuarkSubscribeRecord quarkSubscribeRecord) {
        // 创建文件夹
        Integer accountId = quarkSubscribeRecord.getAccountId();
        QuarkAccount account = quarkAccountService.getById(accountId);

        QuarkPanFileManager manager = new QuarkPanFileManager(account.getCookie());
        QuarkNewFileBo newFile = manager.newFile(quarkSubscribeRecord.getToDirFid(), quarkSubscribeRecord.getName(), "");

        quarkSubscribeRecord.setToDirFid(newFile.getFid());
        save(quarkSubscribeRecord);

        sync(Collections.singletonList(quarkSubscribeRecord.getId()));
    }

    @Override
    public void sync(List<Long> ids) {
        for (Long id : ids) {
            QuarkSubscribeRecord subscribeRecord = getById(id);

            Integer accountId = subscribeRecord.getAccountId();
            QuarkAccount account = quarkAccountService.getById(accountId);

            LambdaQueryWrapper<QuarkSyncRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(QuarkSyncRecord::getSubscribeId, subscribeRecord.getId());
            List<QuarkSyncRecord> syncRecordList = quarkSyncRecordService.list(queryWrapper);

            QuarkPanFileManager manager = new QuarkPanFileManager(account.getCookie());
            String stoken = manager.getStoken(subscribeRecord.getShareUrl());
            if (Strings.isNullOrEmpty(stoken)) {
                // 失效
                subscribeRecord.setStatus(3);
                updateById(subscribeRecord);
                return;
            }

            String pwdId = QuarkPanFileManager.getPwdId(subscribeRecord.getShareUrl());
            try {
                QuarkShareDetailBo detail = manager.find(stoken, pwdId, "0", subscribeRecord.getSourceDirFid());
                log.info("detail:{}", JSON.toJSONString(detail));

                if (detail != null) {
                    List<QuarkSyncRecord> quarkSyncRecords = new ArrayList<>();

                    List<String> fidList = new ArrayList<>();
                    List<String> fidTokenList = new ArrayList<>();
                    for (QuarkShareDetailBo.ListItem file : detail.getList()) {
                        Matcher matcher = pattern.matcher(file.getFileName());
                        if (!matcher.matches()) {
                            log.info("fileName:{},非视频文件", file.getFileName());
                            continue;
                        }

                        String sourceFid = file.getFid();

                        QuarkSyncRecord syncRecord = new QuarkSyncRecord();
                        syncRecord.setSubscribeId(subscribeRecord.getId());
                        syncRecord.setSourceFid(sourceFid);
                        syncRecord.setFileName(file.getFileName());
                        syncRecord.setFileType(file.getFileType());
                        if (!Strings.isNullOrEmpty(subscribeRecord.getPrefixName()) && !file.isDir()) {
                            syncRecord.setName(subscribeRecord.getPrefixName()
                                    + QuarkPanFileManager.getSeriesOrder(file.getFileName())
                                    + "." + QuarkPanFileManager.getFileExtension(file.getFileName()));
                        } else {
                            syncRecord.setName(file.getFileName());
                        }
                        boolean match = syncRecordList.stream().anyMatch(t -> t.getSourceFid().equals(sourceFid) || t.getName().equals(syncRecord.getName()));
                        if (match) {
                            log.info("该文件已存在,sourceFid:{}", sourceFid);
                            continue;
                        }

                        fidList.add(sourceFid);
                        fidTokenList.add(file.getShareFidToken());

                        syncRecord.setDirFid(subscribeRecord.getToDirFid());
                        syncRecord.setIsDir(file.isDir() ? 1 : 0);
                        syncRecord.setStatus(1);

                        quarkSyncRecords.add(syncRecord);
                    }
                    if (!CollectionUtils.isEmpty(fidList)) {
                        SaveFileBo saveFileBo = new SaveFileBo();
                        saveFileBo.setScene("link");
                        saveFileBo.setPdirFid("0");
                        saveFileBo.setPwdId(detail.getShare().getPwdId());
                        saveFileBo.setStoken(stoken);
                        saveFileBo.setToPdirFid(subscribeRecord.getToDirFid());
                        saveFileBo.setFidList(fidList);
                        saveFileBo.setFidTokenList(fidTokenList);

                        JSONObject save = manager.save(saveFileBo);
                        log.info("save:{}", JSON.toJSONString(save));
                    }
                    executorServiceTask.scheduleTask(10, manager, quarkSyncRecords);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            log.info("=============sync end=============,subscribeId:{}", id);
        }
    }
}
