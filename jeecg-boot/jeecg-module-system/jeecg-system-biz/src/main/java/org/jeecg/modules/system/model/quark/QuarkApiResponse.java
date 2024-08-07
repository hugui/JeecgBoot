package org.jeecg.modules.system.model.quark;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class QuarkApiResponse<T> {
    private int status;
    private int code;
    private String message;
    private long timestamp;
    private Metadata metadata;
    private T data;

    @Data
    public static class Metadata {
        @JSONField(name = "_size")
        private int size;

        @JSONField(name = "_page")
        private int page;

        @JSONField(name = "_count")
        private int count;

        @JSONField(name = "_total")
        private int total;

        @JSONField(name = "check_fid_token")
        private int checkFidToken;

        @JSONField(name = "_g_group")
        private String gGroup;

        @JSONField(name = "_t_group")
        private String tGroup;
    }
}