package org.jeecg.modules.system.queue;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.system.model.quark.*;

@Slf4j
public class QuarkPanFileManager {
    private static final String COOKIE = "_UP_A4A_11_=wb9611d1e967474abe870bde627b44b7; CwsSessionId=a0ce3a06-ee3a-4b10-a3d2-fd57e317f23d; b-user-id=0b8c1c29-3648-02f6-2ca0-3212f6e60e53; xlly_s=1; _UP_D_=pc; __pus=cc3020435393659bbd657540a6d7c5b0AASlGprTBvRZVJlw527SHhXBruPnnQ4CPBJetDHLpcZpgtrKeHFYi7SYDx7OOjUpJ5Ji9ZIljN6qFHssqJVIYGci; __kp=67b66d50-4ee9-11ef-bd3b-7ba7ae21d987; __kps=AATf3G+olR2p8l7kRf+aOsXf; __ktd=SB1Ot9wItRg0GVrzGU67gg==; __uid=AATf3G+olR2p8l7kRf+aOsXf; __puus=ed8a09c1cd43881b2073f66994313ff9AASM1DycRLdTmC9FNVcahXJ2aEI1azWV8BXNViTzA3o41F+f5+VkQFzuT3qiaMOVUI2V0mLDd3nxhm3PnTQx15/KKMzbIGs0a8l1kjj7NTnE3g1NVIJS9BoEl+NvVKwDHAJtIDpHkzxq8CSR+UoLKkOvX7EjTewZlPkVMs0n4zfkx+tjYAprB/JMyiwRHUIBeNRPtCOz9ctL7M7vyKIwD+4m; tfstk=faHmYNtVTz_QxgI9oY2X68S4qIdJv-wUQFuTWPna7o47uPdjXb2ZvVqTHc8js4maPizw1IZgalgHcjEwlz0oPqXAB-zAjRmi-rkO3s5yUkrh71-__arzYkrqSctbbPuZSPhvwpnjcRwwJcvppmTZxWMrnR7t4v-ZQ3Tpppn2gowt4eKszgg87lya0Sr443qQjOya_ooyqlrOu1Wq70-ufzrVusPVa0rU0o7wvx88krzsUEP3ZZYrZBT0VS4EmB6NIYXLiyo4rO8EEoYg8mzlQOzXMqsIbV8GBJlIrAVUNdX7HDczUockb_z4tjlU3AYhu0gmlxGEWh5350h3_XFPoUUt90H4Z28REVNigXyqIevP4T5PTHcdCu-tU11_guZuRrAyAo0SZy5Wq3fUmSr70TKkq190guZuJ3xlTHN4VoWR.";

    private String cookie;

    public QuarkPanFileManager(String cookie) {
        this.cookie = cookie;
    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36 Core/1.94.225.400 QQBrowser/12.2.5544.400");
        headers.put("origin", "https://pan.quark.cn");
        headers.put("referer", "https://pan.quark.cn/");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("cookie", cookie);
        return headers;
    }

    public static int generateRandomInteger() {
        Random random = new Random();
        return random.nextInt((9999 - 100) + 1) + 100;
    }

    public static String getPwdId(String shareUrl) {
        String[] parts = shareUrl.split("\\?");
        String[] subParts = parts[0].split("/s/");
        return subParts[1];
    }

    public String getStoken(String shareUrl) {
        Map<String, Object> params = new HashMap<>();
        String pwdId = getPwdId(shareUrl);
        params.put("pwd_id", pwdId);
        params.put("passcode", "");
        String api = "https://drive-pc.quark.cn/1/clouddrive/share/sharepage/token";
        HttpRequest request = HttpUtil.createPost(api)
                .form("pr", "ucpro")
                .form("fr", "pc")
                .form("uc_param_str", "")
                .form("__dt", generateRandomInteger())
                .form("__t", System.currentTimeMillis())
                .body(JSONObject.toJSONString(params))
                .addHeaders(getHeaders());
        // 发送请求并接收响应
        HttpResponse response = request.execute();

        // 获取响应状态码
        int statusCode = response.getStatus();

        // 获取响应内容
        String body = response.body();

        JSONObject jsonObject = JSON.parseObject(body);

        log.info("getStoken:{}", jsonObject.toJSONString());
        if (statusCode == 200) {
            return jsonObject.getJSONObject("data").getString("stoken");
        }
        return null;
    }

    public QuarkShareDetailBo getDetail(String stoken, String pwdId, String fid) throws Exception {
        String api = "https://drive-h.quark.cn/1/clouddrive/share/sharepage/detail";
        HttpRequest request = HttpUtil.createGet(api).addHeaders(getHeaders())
                .form("pr", "ucpro")
                .form("fr", "pc")
                .form("uc_param_str", "")
                .form("__dt", generateRandomInteger())
                .form("__t", System.currentTimeMillis())
                .form("stoken", URLEncoder.encode(stoken, "UTF-8"))
                .form("pwd_id", pwdId)
                .form("pdir_fid", fid)
                .form("force", 0)
                .form("_page", 1)
                .form("_size", 50)
                .form("_fetch_banner", 1)
                .form("_fetch_share", 1)
                .form("_fetch_total", 1)
                .form("_sort", "file_type:asc,updated_at:desc");
        HttpResponse response = request.execute();
//        System.out.println("response: " + JSON.toJSONString(response));
        int statusCode = response.getStatus();
        if (statusCode != 200) {
            log.error("response:{}", JSON.toJSONString(response));
            return null;
        }
        String body = response.body();
//        System.out.println("Body: " + body);

        QuarkApiResponse<QuarkShareDetailBo> result = JSON.parseObject(body, new TypeReference<QuarkApiResponse<QuarkShareDetailBo>>() {
        });

        return result.getData();
    }

    public QuarkShareDetailBo find(String stoken, String pwdId, String fid, String sourceDirFid) throws Exception {
        QuarkShareDetailBo detailBo = getDetail(stoken, pwdId, fid);
        if (Strings.isNullOrEmpty(sourceDirFid)) {
            return getDetail(stoken, pwdId, detailBo.getShare().getFirstFid());
        }

        List<QuarkShareDetailBo.ListItem> fileItemList = detailBo.getList();
        while (true) {
            List<QuarkShareDetailBo.ListItem> dirList = fileItemList.stream().filter(t -> Boolean.TRUE.equals(t.isDir())).collect(Collectors.toList());

            QuarkShareDetailBo.ListItem listItem = dirList.stream().filter(t -> t.isDir() && t.getFid().equals(sourceDirFid))
                    .findFirst().orElse(null);
            if (listItem != null) {
                return getDetail(stoken, pwdId, sourceDirFid);
            }
            for (QuarkShareDetailBo.ListItem item : dirList) {
                return find(stoken, pwdId, item.getFid(), sourceDirFid);
            }
        }
    }

    public List<QuarkUpdateListBo.QuarkUpdateListItemBo> updateList(List<Integer> readStatues) {
        Map<String, Object> params = new HashMap<>();

        params.put("page_size", 30);
        params.put("page", 1);
        params.put("fetch_max_file_update_pos", 1);
        params.put("fetch_total", 1);
        params.put("fetch_update_files", 1);
        params.put("needTotalNum", 1);
        params.put("share_read_statues", readStatues);
        String api = "https://drive-pc.quark.cn/1/clouddrive/share/update_list?pr=ucpro&fr=pc&uc_param_str=";
        HttpRequest request = HttpRequest.post(api)
                .body(JSONObject.toJSONString(params))
                .addHeaders(getHeaders());
        HttpResponse response = request.execute();

        String body = response.body();

        QuarkApiResponse<QuarkUpdateListBo> result = JSON.parseObject(body, new TypeReference<QuarkApiResponse<QuarkUpdateListBo>>() {
        });
        return result.getData().getList();
    }

    public JSONObject save(SaveFileBo saveFileBo) throws Exception {
        String api = "https://drive-pc.quark.cn/1/clouddrive/share/sharepage/save?pr=ucpro&fr=pc&uc_param_str=&__dt=" + generateRandomInteger() + "&__t=" + System.currentTimeMillis();
        HttpRequest request = HttpRequest.post(api)
                .body(JSONObject.toJSONString(saveFileBo))
                .addHeaders(getHeaders());
        HttpResponse response = request.execute();

        String body = response.body();
        return JSON.parseObject(body);
    }

    public JSONObject rename(RenameFileBo bo) {
        String api = "https://drive-pc.quark.cn/1/clouddrive/file/rename?pr=ucpro&fr=pc&uc_param_str=";
        HttpRequest request = HttpRequest.post(api)
                .body(JSONObject.toJSONString(bo))
                .addHeaders(getHeaders());
        HttpResponse response = request.execute();

        String body = response.body();
        return JSON.parseObject(body);
    }

    public List<QuarkFileListBo.QuarkFileListItemBo> sort(String pdirFid) throws Exception {
        if (Strings.isNullOrEmpty(pdirFid)) {
            pdirFid = "0";
        }
        String api = "https://drive-pc.quark.cn/1/clouddrive/file/sort";
        HttpRequest request = HttpUtil.createGet(api).addHeaders(getHeaders())
                .form("pr", "ucpro")
                .form("fr", "pc")
                .form("uc_param_str", "")
                .form("pdir_fid", pdirFid)
                .form("_page", 1)
                .form("_size", 100)
                .form("_fetch_total", 1)
                .form("_fetch_sub_dirs", 1)
                .form("_sort", "file_type:asc,updated_at:desc");
        HttpResponse response = request.execute();
        int statusCode = response.getStatus();
        if (statusCode != 200) {
            log.error("response:{}", JSON.toJSONString(response));
            return null;
        }
        String body = response.body();
        log.info("sort,body:{}", body);
        QuarkApiResponse<QuarkFileListBo> result = JSON.parseObject(body, new TypeReference<QuarkApiResponse<QuarkFileListBo>>() {
        });

        return result.getData().getList();
    }


    public QuarkNewFileBo newFile(String pdirFid, String fileName, String dirPath) {
        if (Strings.isNullOrEmpty(pdirFid)) {
            pdirFid = "0";
        }
        JSONObject params = new JSONObject();
        params.put("pdir_fid", pdirFid);
        params.put("file_name", fileName);
        params.put("dir_path", dirPath);
        params.put("dir_init_lock", false);

        String api = "https://drive-pc.quark.cn/1/clouddrive/file?pr=ucpro&fr=pc&uc_param_str=";
        HttpRequest request = HttpUtil.createPost(api)
                .body(params.toJSONString())
                .addHeaders(getHeaders());
        HttpResponse response = request.execute();
        int statusCode = response.getStatus();
        if (statusCode != 200) {
            log.error("response:{}", JSON.toJSONString(response));
            return null;
        }
        String body = response.body();
        log.info("newFile,body:{}", body);
        QuarkApiResponse<QuarkNewFileBo> result = JSON.parseObject(body, new TypeReference<QuarkApiResponse<QuarkNewFileBo>>() {
        });

        return result.getData();
    }

    public static String getSeriesOrder(String fileName) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(fileName);

        while (matcher.find()) {
            return matcher.group();
        }
        return fileName;
    }

    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return ""; // No extension found
        }

        return fileName.substring(lastDotIndex + 1);
    }

    public static void main(String[] args) throws Exception {
        String shareUrl = "https://pan.quark.cn/s/c5fa327fe68c";
        String stoken = "cCOyma6zdOllGPp76zH6Peqn6ifT4CuldGTQIwFjF2w=";

        QuarkPanFileManager manager = new QuarkPanFileManager(COOKIE);

        List<Integer> readStatues = Arrays.asList(0, 1);
        List<QuarkUpdateListBo.QuarkUpdateListItemBo> list = manager.updateList(readStatues);

        System.out.println(JSONObject.toJSONString(list));
    }
}