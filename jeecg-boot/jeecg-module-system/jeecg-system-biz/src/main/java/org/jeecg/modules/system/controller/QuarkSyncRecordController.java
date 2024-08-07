package org.jeecg.modules.system.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.entity.QuarkSyncRecord;
import org.jeecg.modules.system.service.IQuarkSyncRecordService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 夸克同步记录
 * @Author: jeecg-boot
 * @Date: 2024-08-02
 * @Version: V1.0
 */
@Api(tags = "夸克同步记录")
@RestController
@RequestMapping("/quark/quarkSyncRecord")
@Slf4j
public class QuarkSyncRecordController extends JeecgController<QuarkSyncRecord, IQuarkSyncRecordService> {
    @Autowired
    private IQuarkSyncRecordService quarkSyncRecordService;

    /**
     * 分页列表查询
     *
     * @param quarkSyncRecord
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "夸克同步记录-分页列表查询")
    @ApiOperation(value = "夸克同步记录-分页列表查询", notes = "夸克同步记录-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<QuarkSyncRecord>> queryPageList(QuarkSyncRecord quarkSyncRecord,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest req) {
        QueryWrapper<QuarkSyncRecord> queryWrapper = QueryGenerator.initQueryWrapper(quarkSyncRecord, req.getParameterMap());
        Page<QuarkSyncRecord> page = new Page<QuarkSyncRecord>(pageNo, pageSize);
        IPage<QuarkSyncRecord> pageList = quarkSyncRecordService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param quarkSyncRecord
     * @return
     */
    @AutoLog(value = "夸克同步记录-添加")
    @ApiOperation(value = "夸克同步记录-添加", notes = "夸克同步记录-添加")
    @RequiresPermissions("quark:quark_sync_record:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody QuarkSyncRecord quarkSyncRecord) {
        quarkSyncRecordService.save(quarkSyncRecord);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param quarkSyncRecord
     * @return
     */
    @AutoLog(value = "夸克同步记录-编辑")
    @ApiOperation(value = "夸克同步记录-编辑", notes = "夸克同步记录-编辑")
    @RequiresPermissions("quark:quark_sync_record:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody QuarkSyncRecord quarkSyncRecord) {
        quarkSyncRecordService.updateById(quarkSyncRecord);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "夸克同步记录-通过id删除")
    @ApiOperation(value = "夸克同步记录-通过id删除", notes = "夸克同步记录-通过id删除")
    @RequiresPermissions("quark:quark_sync_record:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        quarkSyncRecordService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "夸克同步记录-批量删除")
    @ApiOperation(value = "夸克同步记录-批量删除", notes = "夸克同步记录-批量删除")
    @RequiresPermissions("quark:quark_sync_record:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.quarkSyncRecordService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "夸克同步记录-通过id查询")
    @ApiOperation(value = "夸克同步记录-通过id查询", notes = "夸克同步记录-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<QuarkSyncRecord> queryById(@RequestParam(name = "id", required = true) String id) {
        QuarkSyncRecord quarkSyncRecord = quarkSyncRecordService.getById(id);
        if (quarkSyncRecord == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(quarkSyncRecord);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param quarkSyncRecord
     */
    @RequiresPermissions("quark:quark_sync_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, QuarkSyncRecord quarkSyncRecord) {
        return super.exportXls(request, quarkSyncRecord, QuarkSyncRecord.class, "夸克同步记录");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("quark:quark_sync_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, QuarkSyncRecord.class);
    }

}
