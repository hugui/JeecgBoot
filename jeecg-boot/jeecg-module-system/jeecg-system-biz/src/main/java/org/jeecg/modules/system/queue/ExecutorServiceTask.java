package org.jeecg.modules.system.queue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.system.entity.QuarkSyncRecord;
import org.jeecg.modules.system.model.quark.QuarkFileListBo;
import org.jeecg.modules.system.model.quark.RenameFileBo;
import org.jeecg.modules.system.service.IQuarkSyncRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExecutorServiceTask {
    @Autowired
    private IQuarkSyncRecordService quarkSyncRecordService;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void scheduleTask(long delayInSeconds, QuarkPanFileManager manager, List<QuarkSyncRecord> quarkSyncRecords) {
        scheduler.schedule(() -> {
            log.info("=============Task executed after delay=============,subscribeId:{}", quarkSyncRecords.get(0).getSubscribeId());
            String fid = quarkSyncRecords.get(0).getDirFid();
            List<QuarkFileListBo.QuarkFileListItemBo> fileListBos;
            try {
                fileListBos = manager.sort(fid);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Map<String, QuarkFileListBo.QuarkFileListItemBo> map = fileListBos.stream().collect(Collectors.toMap(QuarkFileListBo.QuarkFileListItemBo::getFileName, Function.identity()));

            for (QuarkSyncRecord syncRecord : quarkSyncRecords) {
                QuarkFileListBo.QuarkFileListItemBo quarkFileListBo = map.get(syncRecord.getFileName());
                if (quarkFileListBo != null) {
                    syncRecord.setFid(quarkFileListBo.getFid());

                    RenameFileBo bo = new RenameFileBo();
                    bo.setFileName(syncRecord.getName());
                    bo.setFid(syncRecord.getFid());
                    if (!syncRecord.getFileName().equals(syncRecord.getName())) {
                        JSONObject jsonObject = manager.rename(bo);

                        log.info("rename:{}", JSON.toJSONString(jsonObject));
                    }
                }
            }
            quarkSyncRecordService.saveBatch(quarkSyncRecords);
        }, delayInSeconds, TimeUnit.SECONDS);
    }
}