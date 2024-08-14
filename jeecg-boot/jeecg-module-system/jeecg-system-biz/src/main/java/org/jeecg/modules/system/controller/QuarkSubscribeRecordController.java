package org.jeecg.modules.system.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.entity.QuarkSubscribeRecord;
import org.jeecg.modules.system.model.quark.QuarkSyncReq;
import org.jeecg.modules.system.service.IQuarkSubscribeRecordService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 夸克订阅记录
 * @Author: jeecg-boot
 * @Date: 2024-08-02
 * @Version: V1.0
 */
@Api(tags = "夸克订阅记录")
@RestController
@RequestMapping("/quark/quarkSubscribeRecord")
@Slf4j
public class QuarkSubscribeRecordController extends JeecgController<QuarkSubscribeRecord, IQuarkSubscribeRecordService> {
    @Autowired
    private IQuarkSubscribeRecordService quarkSubscribeRecordService;

    /**
     * 分页列表查询
     *
     * @param quarkSubscribeRecord
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "夸克订阅记录-分页列表查询")
    @ApiOperation(value = "夸克订阅记录-分页列表查询", notes = "夸克订阅记录-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<QuarkSubscribeRecord>> queryPageList(QuarkSubscribeRecord quarkSubscribeRecord,
                                                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                             HttpServletRequest req) {
        QueryWrapper<QuarkSubscribeRecord> queryWrapper = QueryGenerator.initQueryWrapper(quarkSubscribeRecord, req.getParameterMap());
        Page<QuarkSubscribeRecord> page = new Page<QuarkSubscribeRecord>(pageNo, pageSize);
        IPage<QuarkSubscribeRecord> pageList = quarkSubscribeRecordService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param quarkSubscribeRecord
     * @return
     */
    @AutoLog(value = "夸克订阅记录-添加")
    @ApiOperation(value = "夸克订阅记录-添加", notes = "夸克订阅记录-添加")
    @RequiresPermissions("quark:quark_subscribe_record:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody QuarkSubscribeRecord quarkSubscribeRecord) {
        quarkSubscribeRecordService.saveRecord(quarkSubscribeRecord);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param quarkSubscribeRecord
     * @return
     */
    @AutoLog(value = "夸克订阅记录-编辑")
    @ApiOperation(value = "夸克订阅记录-编辑", notes = "夸克订阅记录-编辑")
    @RequiresPermissions("quark:quark_subscribe_record:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody QuarkSubscribeRecord quarkSubscribeRecord) {
        quarkSubscribeRecordService.updateById(quarkSubscribeRecord);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "夸克订阅记录-通过id删除")
    @ApiOperation(value = "夸克订阅记录-通过id删除", notes = "夸克订阅记录-通过id删除")
    @RequiresPermissions("quark:quark_subscribe_record:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        quarkSubscribeRecordService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "夸克订阅记录-批量删除")
    @ApiOperation(value = "夸克订阅记录-批量删除", notes = "夸克订阅记录-批量删除")
    @RequiresPermissions("quark:quark_subscribe_record:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.quarkSubscribeRecordService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "夸克订阅记录-通过id查询")
    @ApiOperation(value = "夸克订阅记录-通过id查询", notes = "夸克订阅记录-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<QuarkSubscribeRecord> queryById(@RequestParam(name = "id", required = true) String id) {
        QuarkSubscribeRecord quarkSubscribeRecord = quarkSubscribeRecordService.getById(id);
        if (quarkSubscribeRecord == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(quarkSubscribeRecord);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param quarkSubscribeRecord
     */
    @RequiresPermissions("quark:quark_subscribe_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, QuarkSubscribeRecord quarkSubscribeRecord) {
        return super.exportXls(request, quarkSubscribeRecord, QuarkSubscribeRecord.class, "夸克订阅记录");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("quark:quark_subscribe_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, QuarkSubscribeRecord.class);
    }

    @PostMapping(value = "/sync")
    public Result<?> sync(@RequestBody QuarkSyncReq req) {
        if (req == null || CollectionUtils.isEmpty(req.getIds())) {
            return Result.error("参数错误");
        }
        quarkSubscribeRecordService.sync(req.getIds());
        return Result.OK();
    }

    @GetMapping(value = "/folders")
    public Result<?> folders(@RequestBody QuarkSyncReq req) {
        if (req == null || CollectionUtils.isEmpty(req.getIds())) {
            return Result.error("参数错误");
        }
        quarkSubscribeRecordService.sync(req.getIds());
        return Result.OK();
    }

}
