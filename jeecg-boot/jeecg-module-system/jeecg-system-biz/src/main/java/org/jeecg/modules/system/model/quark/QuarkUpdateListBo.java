package org.jeecg.modules.system.model.quark;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class QuarkUpdateListBo {

    @JSONField(name = "list")
    private List<QuarkUpdateListItemBo> list;

    @Data
    public static class QuarkUpdateListItemBo {
        @JSONField(name = "share_id")
        private String shareId;

        @JSONField(name = "share_url")
        private String shareUrl;

        private String title;

        private String stoken;

        @JSONField(name = "read_status")
        private String readStatus;

        @JSONField(name = "update_files")
        private List<QuarkShareDetailBo.ListItem> updateFiles;
    }
}