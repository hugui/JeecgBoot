package org.jeecg.modules.system.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.system.entity.QuarkAccount;
import org.jeecg.modules.system.model.quark.QuarkFileListBo;
import org.jeecg.modules.system.queue.QuarkPanFileManager;
import org.jeecg.modules.system.service.IQuarkAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Api(tags = "夸克订阅记录")
@RestController
@RequestMapping("/quark/")
@Slf4j
public class QuarkController {
    @Autowired
    private IQuarkAccountService quarkAccountService;


    @GetMapping(value = "/folders")
    public Result<?> folders(String pdirFid) {
        QuarkAccount account = quarkAccountService.getById(1L);
        QuarkPanFileManager manager = new QuarkPanFileManager(account.getCookie());
        List<QuarkFileListBo.QuarkFileListItemBo> fileListItemBoList = getQuarkFileListItemBos(pdirFid, manager);
        return Result.OK(fileListItemBoList);
    }

    private List<QuarkFileListBo.QuarkFileListItemBo> getQuarkFileListItemBos(String pdirFid, QuarkPanFileManager manager) {
        // 初始调用时从第1级开始
        return getQuarkFileListItemBos(pdirFid, manager, 1);
    }

    private List<QuarkFileListBo.QuarkFileListItemBo> getQuarkFileListItemBos(String pdirFid, QuarkPanFileManager manager, int currentDepth) {
        // 最大深度为3，超过则不再继续递归
        final int maxDepth = 2;

        List<QuarkFileListBo.QuarkFileListItemBo> fileListItemBoList;
        try {
            // 获取当前文件夹下的文件列表
            fileListItemBoList = manager.sort(pdirFid);

            // 只有在当前深度小于最大深度时才递归处理子文件夹
            if (currentDepth < maxDepth) {
                for (QuarkFileListBo.QuarkFileListItemBo fileItem : fileListItemBoList) {
                    if (Boolean.TRUE.equals(fileItem.getDir())) {
                        // 递归调用，传递当前深度+1
                        List<QuarkFileListBo.QuarkFileListItemBo> subFiles = getQuarkFileListItemBos(fileItem.getFid(), manager, currentDepth + 1);
                        subFiles = subFiles.stream().filter(t->Boolean.TRUE.equals(t.getDir())).collect(Collectors.toList());
                        fileItem.setSub(subFiles);
                    }
                }
            }
        } catch (Exception e) {
            // 使用自定义异常或其他处理逻辑
            throw new RuntimeException("Failed to retrieve file list for fid: " + pdirFid, e);
        }
        return fileListItemBoList;
    }

}
