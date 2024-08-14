package org.jeecg.modules.system.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.system.entity.QuarkAccount;
import org.jeecg.modules.system.model.quark.QuarkFileListBo;
import org.jeecg.modules.system.queue.QuarkPanFileManager;
import org.jeecg.modules.system.service.IQuarkAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

        List<QuarkFileListBo.QuarkFileListItemBo> fileListItemBoList = Lists.newArrayList();
        try {
            fileListItemBoList = manager.sort(pdirFid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Result.OK(fileListItemBoList);
    }

}
