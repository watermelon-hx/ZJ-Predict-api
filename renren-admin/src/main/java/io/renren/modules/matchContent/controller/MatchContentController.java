package io.renren.modules.matchContent.controller;

import io.renren.common.annotation.LogOperation;
import io.renren.common.constant.Constant;
import io.renren.common.page.PageData;
import io.renren.common.utils.ExcelUtils;
import io.renren.common.utils.Result;
import io.renren.common.validator.AssertUtils;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.DefaultGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.matchContent.dto.MatchContentDTO;
import io.renren.modules.matchContent.excel.MatchContentExcel;
import io.renren.modules.matchContent.service.MatchContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
* 比赛动态数据内容
*
* @author Mark sunlightcs@gmail.com
* @since 3.0 2023-07-14
*/
@RestController
@RequestMapping("MatchContent/matchcontent")
@Api(tags="比赛动态数据内容")
public class MatchContentController {
    @Autowired
    private MatchContentService matchContentService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("MatchContent:matchcontent:page")
    public Result<PageData<MatchContentDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<MatchContentDTO> page = matchContentService.page(params);

        return new Result<PageData<MatchContentDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("MatchContent:matchcontent:info")
    public Result<MatchContentDTO> get(@PathVariable("id") Long id){
        MatchContentDTO data = matchContentService.get(id);

        return new Result<MatchContentDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("MatchContent:matchcontent:save")
    public Result save(@RequestBody MatchContentDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        matchContentService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("MatchContent:matchcontent:update")
    public Result update(@RequestBody MatchContentDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        matchContentService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("MatchContent:matchcontent:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        matchContentService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("MatchContent:matchcontent:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<MatchContentDTO> list = matchContentService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, "比赛动态数据内容", list, MatchContentExcel.class);
    }

}