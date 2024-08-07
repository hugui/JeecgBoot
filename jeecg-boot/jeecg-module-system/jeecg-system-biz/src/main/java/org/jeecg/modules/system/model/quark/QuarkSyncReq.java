package org.jeecg.modules.system.model.quark;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class QuarkSyncReq {
    private List<Long> ids;
}