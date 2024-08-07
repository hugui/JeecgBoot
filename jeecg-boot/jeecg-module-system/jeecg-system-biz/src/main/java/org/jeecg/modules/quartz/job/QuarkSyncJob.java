package org.jeecg.modules.quartz.job;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.system.queue.QuarkPanFileManager;
import org.jeecg.modules.system.entity.QuarkAccount;
import org.jeecg.modules.system.entity.QuarkSubscribeRecord;
import org.jeecg.modules.system.model.quark.QuarkUpdateListBo;
import org.jeecg.modules.system.service.IQuarkAccountService;
import org.jeecg.modules.system.service.IQuarkSubscribeRecordService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 夸克订阅同步定时任务
 */
@Slf4j
@Component
public class QuarkSyncJob implements Job {
    @Autowired
    private IQuarkAccountService quarkAccountService;
    @Autowired
    private IQuarkSubscribeRecordService quarkSubscribeRecordService;

    /**
     * 若参数变量名修改 QuartzJobController中也需对应修改
     */
    private String parameter;

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info(" Job Execution key：" + jobExecutionContext.getJobDetail().getKey());
        QuarkAccount account = quarkAccountService.getById(1L);
        QuarkPanFileManager manager = new QuarkPanFileManager(account.getCookie());

        List<Integer> readStatues = Arrays.asList(0, 1);
        List<QuarkUpdateListBo.QuarkUpdateListItemBo> list = manager.updateList(readStatues);
        log.info("updateList:{}", JSONObject.toJSONString(list));
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (QuarkUpdateListBo.QuarkUpdateListItemBo itemBo : list) {
            LambdaQueryWrapper<QuarkSubscribeRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(QuarkSubscribeRecord::getShareUrl, itemBo.getShareUrl());
            QuarkSubscribeRecord subscribeRecord = quarkSubscribeRecordService.getOne(queryWrapper);
            if (subscribeRecord != null) {
                quarkSubscribeRecordService.sync(Collections.singletonList(subscribeRecord.getId()));
            }
        }

    }
}
