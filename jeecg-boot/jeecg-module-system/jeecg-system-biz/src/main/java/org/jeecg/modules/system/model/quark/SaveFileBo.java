package org.jeecg.modules.system.model.quark;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class SaveFileBo {
    @JSONField(name = "fid_list")
    private List<String> fidList;

    @JSONField(name = "fid_token_list")
    private List<String> fidTokenList;

    @JSONField(name = "to_pdir_fid")
    private String toPdirFid;

    @JSONField(name = "pwd_id")
    private String pwdId;

    private String stoken;

    @JSONField(name = "pdir_fid")
    private String pdirFid;

    private String scene;
}