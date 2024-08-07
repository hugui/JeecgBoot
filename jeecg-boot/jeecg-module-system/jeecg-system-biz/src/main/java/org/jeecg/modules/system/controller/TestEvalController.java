package org.jeecg.modules.system.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;

import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.entity.TestEval;
import org.jeecg.modules.system.service.ITestEvalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 测评表
 * @Author: jeecg-boot
 * @Date: 2024-07-29
 * @Version: V1.0
 */
@Api(tags = "测评表")
@RestController
@RequestMapping("/eval/testEval")
@Slf4j
public class TestEvalController extends JeecgController<TestEval, ITestEvalService> {
    @Autowired
    private ITestEvalService testEvalService;

    /**
     * 分页列表查询
     *
     * @param testEval
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "测评表-分页列表查询")
    @ApiOperation(value = "测评表-分页列表查询", notes = "测评表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<TestEval>> queryPageList(TestEval testEval, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        QueryWrapper<TestEval> queryWrapper = QueryGenerator.initQueryWrapper(testEval, req.getParameterMap());
        Page<TestEval> page = new Page<TestEval>(pageNo, pageSize);
        IPage<TestEval> pageList = testEvalService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param testEval
     * @return
     */
    @AutoLog(value = "测评表-添加")
    @ApiOperation(value = "测评表-添加", notes = "测评表-添加")
    @RequiresPermissions("eval:test_eval:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody TestEval testEval) {
        testEvalService.save(testEval);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param testEval
     * @return
     */
    @AutoLog(value = "测评表-编辑")
    @ApiOperation(value = "测评表-编辑", notes = "测评表-编辑")
    @RequiresPermissions("eval:test_eval:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody TestEval testEval) {
        testEvalService.updateById(testEval);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "测评表-通过id删除")
    @ApiOperation(value = "测评表-通过id删除", notes = "测评表-通过id删除")
    @RequiresPermissions("eval:test_eval:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        testEvalService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "测评表-批量删除")
    @ApiOperation(value = "测评表-批量删除", notes = "测评表-批量删除")
    @RequiresPermissions("eval:test_eval:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.testEvalService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "测评表-通过id查询")
    @ApiOperation(value = "测评表-通过id查询", notes = "测评表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<TestEval> queryById(@RequestParam(name = "id", required = true) String id) {
        TestEval testEval = testEvalService.getById(id);
        if (testEval == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(testEval);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param testEval
     */
    @RequiresPermissions("eval:test_eval:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TestEval testEval) {
        return super.exportXls(request, testEval, TestEval.class, "测评表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("eval:test_eval:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TestEval.class);
    }

}
