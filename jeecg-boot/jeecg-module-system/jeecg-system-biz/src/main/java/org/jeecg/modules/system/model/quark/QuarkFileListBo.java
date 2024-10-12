package org.jeecg.modules.system.model.quark;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class QuarkFileListBo {

    @JSONField(name = "last_view_list")
    private List<JSONObject> lastViewList;

    @JSONField(name = "recent_file_list")
    private List<JSONObject> recentFileList;


    @JSONField(name = "list")
    private List<QuarkFileListItemBo> list;

    @Data
    public static class QuarkFileListItemBo {
//        @JSONField(name = "file_name")
        private String fileName;

        private String fid;

        private Boolean dir;

        private List<QuarkFileListItemBo> sub;
    }
}