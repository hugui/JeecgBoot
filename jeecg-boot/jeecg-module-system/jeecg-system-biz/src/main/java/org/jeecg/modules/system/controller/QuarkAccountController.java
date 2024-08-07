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

import org.jeecg.modules.system.entity.QuarkAccount;
import org.jeecg.modules.system.service.IQuarkAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 夸克账户
 * @Author: jeecg-boot
 * @Date: 2024-08-01
 * @Version: V1.0
 */
@Api(tags = "夸克账户")
@RestController
@RequestMapping("/quark/quarkAccount")
@Slf4j
public class QuarkAccountController extends JeecgController<QuarkAccount, IQuarkAccountService> {
    @Autowired
    private IQuarkAccountService quarkAccountService;

    /**
     * 分页列表查询
     *
     * @param quarkAccount
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "夸克账户-分页列表查询")
    @ApiOperation(value = "夸克账户-分页列表查询", notes = "夸克账户-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<QuarkAccount>> queryPageList(QuarkAccount quarkAccount,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        QueryWrapper<QuarkAccount> queryWrapper = QueryGenerator.initQueryWrapper(quarkAccount, req.getParameterMap());
        Page<QuarkAccount> page = new Page<QuarkAccount>(pageNo, pageSize);
        IPage<QuarkAccount> pageList = quarkAccountService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param quarkAccount
     * @return
     */
    @AutoLog(value = "夸克账户-添加")
    @ApiOperation(value = "夸克账户-添加", notes = "夸克账户-添加")
    @RequiresPermissions("quark:quark_account:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody QuarkAccount quarkAccount) {
        quarkAccountService.save(quarkAccount);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param quarkAccount
     * @return
     */
    @AutoLog(value = "夸克账户-编辑")
    @ApiOperation(value = "夸克账户-编辑", notes = "夸克账户-编辑")
    @RequiresPermissions("quark:quark_account:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody QuarkAccount quarkAccount) {
        quarkAccountService.updateById(quarkAccount);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "夸克账户-通过id删除")
    @ApiOperation(value = "夸克账户-通过id删除", notes = "夸克账户-通过id删除")
    @RequiresPermissions("quark:quark_account:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        quarkAccountService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "夸克账户-批量删除")
    @ApiOperation(value = "夸克账户-批量删除", notes = "夸克账户-批量删除")
    @RequiresPermissions("quark:quark_account:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.quarkAccountService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "夸克账户-通过id查询")
    @ApiOperation(value = "夸克账户-通过id查询", notes = "夸克账户-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<QuarkAccount> queryById(@RequestParam(name = "id", required = true) String id) {
        QuarkAccount quarkAccount = quarkAccountService.getById(id);
        if (quarkAccount == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(quarkAccount);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param quarkAccount
     */
    @RequiresPermissions("quark:quark_account:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, QuarkAccount quarkAccount) {
        return super.exportXls(request, quarkAccount, QuarkAccount.class, "夸克账户");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("quark:quark_account:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, QuarkAccount.class);
    }

}
