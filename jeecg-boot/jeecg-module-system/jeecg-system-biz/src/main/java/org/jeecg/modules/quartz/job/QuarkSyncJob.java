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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 夸克订阅同步定时任务
 */
@Slf4j
@Component
public class QuarkSyncJob implements Job {
    @Autowired
    private IQuarkSubscribeRecordService quarkSubscribeRecordService;

    /**
     * 若参数变量名修改 QuartzJobController中也需对应修改
     */
    private String parameter;

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    /**
     * 每小时执行一次
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void subscribeSyncTask() {
        log.info("subscribeSyncTask start");
        LambdaQueryWrapper<QuarkSubscribeRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(QuarkSubscribeRecord::getStatus, 1);
        List<QuarkSubscribeRecord> subscribeRecordList = quarkSubscribeRecordService.list(queryWrapper);
        if (!CollectionUtils.isEmpty(subscribeRecordList)) {
            log.info("订阅记录subscribeRecordList:{}", JSONObject.toJSONString(subscribeRecordList));
            List<Long> subscribeIds = subscribeRecordList.stream().map(QuarkSubscribeRecord::getId).collect(Collectors.toList());
            quarkSubscribeRecordService.sync(subscribeIds);
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info(" Job Execution key：" + jobExecutionContext.getJobDetail().getKey());
        subscribeSyncTask();
    }
}
