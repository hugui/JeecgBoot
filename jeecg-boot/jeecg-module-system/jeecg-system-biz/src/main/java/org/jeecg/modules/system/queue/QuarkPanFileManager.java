package org.jeecg.modules.system.queue;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
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
    private static final String COOKIE = "_UP_A4A_11_=wb9611d1e967474abe870bde627b44b7; CwsSessionId=a0ce3a06-ee3a-4b10-a3d2-fd57e317f23d; b-user-id=0b8c1c29-3648-02f6-2ca0-3212f6e60e53; _UP_D_=pc; xlly_s=1; __pus=19c01243c58cabb09e4a9ea312643681AATRLiBofCCLdX9AIamMO/+Un5SjBWqq0CiVp18+bi3Vv3/yvvldf+8AiHxyU3AkuPwj7neEWniox1zWDfXYoryo; __kp=b3efd220-a550-11ef-a73a-23b1767f453c; __kps=AATYrhUzZ9xlhBmvMrP47r9W; __ktd=yx/Qr9x1hvJGBUcFC9y69g==; __uid=AATYrhUzZ9xlhBmvMrP47r9W; __puus=64b7f0362bbce1b547ec67338a40fc84AAQyG8POdLyMbDQrbQx9NmDNb3tHHjH6b6p8vafF+C/ZMhrb+IMMrwhUmj3pPGVtThMff+nsUS7JdfkuaqFRcLq1Kc0Rxla62MGl4ZO2Jz5z/PS0OaQNLCl7DXkcBLybd4MWg1K/n60mzNsVDfb3DKOvKCfwKpWJWw//fkmUAklCMtuV0o5Kmitoe17AbIA771HuzwftU9LxNGhfn3GTd23O; isg=BAYG45vcnSpISEjKalMqAZh9V_qIZ0ohg9s1ZfAv8ikE86YNWPeaMeyFyy8_70I5; tfstk=fLltLpYaleQ9CMQdpt93mvON2fLnkj3Zvcu5ioqGhDnKume0hR4gM-i_xf201lmdMP405rZ_Gk6Qy0_s15iDMogzY1mDQ-fA7XnRi1xwQq1YiStkqQAo7dP4G3f9bxFQbruQcwp5pouagSth-s9kwVo77jCwWSTLOr4PcSZjcWsQYrzffr16JeU4AsabfSgQArU1hribGe3QYrTz46r2hufxnq8ARuRzYs1j1IyT2VBcGsGLWR3S5k3n-XUTBuwHtEmxw4gsarh2PtExzAig8mdJvWGtXfwLMICUs4HIXJH9D6eEd4lbpv-RU4usXYwS1BLUpfuu1kh2usrsIqG0Hft5olGZjXergGK3mY0n1JnWx6n4HAM71jKJOg-nZbE68gq8oOTpJtW4Cy-mEa2oCUFAZyEk-c6V3JBzJuYpQtW4CyzLqeXG3tydU";

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

    public QuarkShareDetailBo getDetail(String stoken, String pwdId, String fid) {
        String api = "https://drive-h.quark.cn/1/clouddrive/share/sharepage/detail";
        HttpRequest request = null;
        try {
            request = HttpUtil.createGet(api).addHeaders(getHeaders())
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
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
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

    public JSONObject save(SaveFileBo saveFileBo) {
        String api = "https://drive-pc.quark.cn/1/clouddrive/share/sharepage/save?pr=ucpro&fr=pc&uc_param_str=&__dt=" + generateRandomInteger() + "&__t=" + System.currentTimeMillis();
        HttpRequest request = HttpRequest.post(api)
                .body(JSONObject.toJSONString(saveFileBo))
                .addHeaders(getHeaders());
        HttpResponse response = request.execute();

        String body = response.body();
        return JSON.parseObject(body);
    }

    /**
     * 删除
     *
     * @param filelist
     * @return
     */
    public JSONObject delete(List<String> filelist) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action_type", 2);
        jsonObject.put("filelist", filelist);
        jsonObject.put("exclude_fids", Collections.emptyList());

        String api = "https://drive-pc.quark.cn/1/clouddrive/file/delete?pr=ucpro&fr=pc&uc_param_str=";
        HttpRequest request = HttpRequest.post(api)
                .body(JSONObject.toJSONString(jsonObject))
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
                .form("_size", 50)
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
        String shareUrl = "https://pan.quark.cn/s/93e6f2a89c3a";
        String pwdId = getPwdId(shareUrl);

        QuarkPanFileManager manager = new QuarkPanFileManager(COOKIE);
        String stoken = manager.getStoken(shareUrl);
        QuarkShareDetailBo detailBo = manager.getDetail(stoken, pwdId, "0");

        SaveFileBo saveFileBo = new SaveFileBo();
        saveFileBo.setScene("link");
        saveFileBo.setPdirFid("0");
        saveFileBo.setPwdId(pwdId);
        saveFileBo.setStoken(stoken);
        saveFileBo.setToPdirFid("f527cf3794af453b93e67d98a7a52c39");
        List<QuarkShareDetailBo.ListItem> itemList = detailBo.getList();

        QuarkShareDetailBo.ListItem listItem = itemList.get(0);
        saveFileBo.setFidList(Collections.singletonList(listItem.getFid()));
        saveFileBo.setFidTokenList(Collections.singletonList(listItem.getShareFidToken()));

        JSONObject jsonObject = manager.save(saveFileBo);

        System.out.println(JSONObject.toJSONString(detailBo));
        System.out.println(JSONObject.toJSONString(jsonObject));
    }
}