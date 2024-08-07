package org.jeecg.modules.system.model.quark;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class RenameFileBo {

    @JSONField(name = "file_name")
    private String fileName;

    private String fid;

}