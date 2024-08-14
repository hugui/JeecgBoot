package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.entity.QuarkSubscribeRecord;

import java.util.List;

/**
 * @Description: 夸克订阅记录
 * @Author: jeecg-boot
 * @Date: 2024-08-02
 * @Version: V1.0
 */
public interface IQuarkSubscribeRecordService extends IService<QuarkSubscribeRecord> {

    void sync(List<Long> ids);

    void saveRecord(QuarkSubscribeRecord quarkSubscribeRecord);

}
