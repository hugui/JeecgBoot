package org.jeecg.modules.system.model.quark;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class QuarkShareDetailBo {
    @JSONField(name = "is_owner")
    private int isOwner;

    private Share share;

    private List<ListItem> list;

    @Data
    public static class Share {
        private String title;

        @JSONField(name = "share_type")
        private int shareType;

        @JSONField(name = "share_id")
        private String shareId;

        @JSONField(name = "pwd_id")
        private String pwdId;

        @JSONField(name = "share_url")
        private String shareUrl;

        @JSONField(name = "url_type")
        private int urlType;

        private String passcode;

        @JSONField(name = "first_fid")
        private String firstFid;

        @JSONField(name = "expired_type")
        private int expiredType;

        @JSONField(name = "file_num")
        private int fileNum;

        @JSONField(name = "created_at")
        private long createdAt;

        @JSONField(name = "updated_at")
        private long updatedAt;

        @JSONField(name = "expired_at")
        private long expiredAt;

        @JSONField(name = "expired_left")
        private long expiredLeft;

        @JSONField(name = "audit_status")
        private int auditStatus;

        private int status;

        @JSONField(name = "click_pv")
        private int clickPv;

        @JSONField(name = "save_pv")
        private int savePv;

        @JSONField(name = "download_pv")
        private int downloadPv;

        @JSONField(name = "first_file")
        private FirstFile firstFile;

        @JSONField(name = "path_info")
        private String pathInfo;

        @JSONField(name = "partial_violation")
        private boolean partialViolation;

        private long size;

        @JSONField(name = "first_layer_file_categories")
        private List<Integer> firstLayerFileCategories;

        @JSONField(name = "is_owner")
        private int isOwner;

        @JSONField(name = "download_pvlimited")
        private boolean downloadPvLimited;
    }


    @Data
    public static class FirstFile {
        private String fid;

        @JSONField(name = "file_type")
        private int fileType;

        @JSONField(name = "format_type")
        private String formatType;

        @JSONField(name = "name_space")
        private int nameSpace;

        @JSONField(name = "series_dir")
        private boolean seriesDir;

        @JSONField(name = "upload_camera_root_dir")
        private boolean uploadCameraRootDir;

        private double fps;

        private int like;

        @JSONField(name = "risk_type")
        private int riskType;

        @JSONField(name = "file_name_hl_start")
        private int fileNameHlStart;

        @JSONField(name = "file_name_hl_end")
        private int fileNameHlEnd;

        private int duration;

        @JSONField(name = "scrape_status")
        private int scrapeStatus;

        private boolean ban;

        @JSONField(name = "cur_version_or_default")
        private int curVersionOrDefault;

        @JSONField(name = "save_as_source")
        private boolean saveAsSource;

        @JSONField(name = "backup_source")
        private boolean backupSource;

        @JSONField(name = "owner_drive_type_or_default")
        private int ownerDriveTypeOrDefault;

        @JSONField(name = "offline_source")
        private boolean offlineSource;

        private boolean dir;

        private boolean file;

        @JSONField(name = "_extra")
        private Object extra;
    }

    @Data
    public static class ListItem {
        private String fid;

        @JSONField(name = "file_name")
        private String fileName;

        @JSONField(name = "pdir_fid")
        private String pdirFid;

        private int category;

        @JSONField(name = "file_type")
        private int fileType;

        private long size;

        @JSONField(name = "format_type")
        private String formatType;

        private int status;

        private String tags;

        @JSONField(name = "owner_ucid")
        private String ownerUcid;

        @JSONField(name = "l_created_at")
        private long lCreatedAt;

        @JSONField(name = "l_updated_at")
        private long lUpdatedAt;

        private String extra;

        private String source;

        @JSONField(name = "file_source")
        private String fileSource;

        @JSONField(name = "name_space")
        private int nameSpace;

        @JSONField(name = "l_shot_at")
        private long lShotAt;

        @JSONField(name = "series_id")
        private String seriesId;

        @JSONField(name = "source_display")
        private String sourceDisplay;

        @JSONField(name = "include_items")
        private int includeItems;

        @JSONField(name = "series_dir")
        private boolean seriesDir;

        @JSONField(name = "upload_camera_root_dir")
        private boolean uploadCameraRootDir;

        private double fps;

        private int like;

        @JSONField(name = "operated_at")
        private long operatedAt;

        @JSONField(name = "risk_type")
        private int riskType;

        @JSONField(name = "backup_sign")
        private int backupSign;

        @JSONField(name = "file_name_hl_start")
        private int fileNameHlStart;

        @JSONField(name = "file_name_hl_end")
        private int fileNameHlEnd;

        @JSONField(name = "file_struct")
        private FileStruct fileStruct;

        private int duration;

        @JSONField(name = "event_extra")
        private EventExtra eventExtra;

        @JSONField(name = "scrape_status")
        private int scrapeStatus;

        @JSONField(name = "update_view_at")
        private long updateViewAt;

        @JSONField(name = "last_update_at")
        private long lastUpdateAt;

        @JSONField(name = "share_fid_token")
        private String shareFidToken;

        private boolean ban;

        @JSONField(name = "raw_name_space")
        private int rawNameSpace;

        @JSONField(name = "cur_version_or_default")
        private int curVersionOrDefault;

        @JSONField(name = "save_as_source")
        private boolean saveAsSource;

        @JSONField(name = "backup_source")
        private boolean backupSource;

        @JSONField(name = "owner_drive_type_or_default")
        private int ownerDriveTypeOrDefault;

        @JSONField(name = "offline_source")
        private boolean offlineSource;

        private boolean dir;

        private boolean file;

        @JSONField(name = "created_at")
        private long createdAt;

        @JSONField(name = "updated_at")
        private long updatedAt;
    }

    @Data
    public static class FileStruct {
        @JSONField(name = "fir_source")
        private String firSource;

        @JSONField(name = "sec_source")
        private String secSource;

        @JSONField(name = "thi_source")
        private String thiSource;

        @JSONField(name = "platform_source")
        private String platformSource;
    }

    @Data
    public static class EventExtra {
        @JSONField(name = "recent_created_at")
        private long recentCreatedAt;
    }
}
