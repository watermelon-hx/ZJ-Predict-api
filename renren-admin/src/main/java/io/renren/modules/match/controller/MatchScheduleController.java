package io.renren.modules.match.controller;

import io.renren.common.annotation.LogOperation;
import io.renren.common.constant.Constant;
import io.renren.common.page.PageData;
import io.renren.common.utils.ExcelUtils;
import io.renren.common.utils.Result;
import io.renren.common.validator.AssertUtils;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.DefaultGroup;
import io.renren.modules.match.dto.MatchScheduleDTO;
import io.renren.modules.match.excel.MatchScheduleExcel;
import io.renren.modules.match.service.MatchScheduleService;
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
 * 日程
 *
 * @author sss
 * @since 3.0 2023-03-04
 */
@RestController
@RequestMapping("match/matchschedule")
@Api(tags = "日程")
public class MatchScheduleController {
    @Autowired
    private MatchScheduleService matchScheduleService;

//    @GetMapping("page")
//    @ApiOperation("分页")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
//            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
//            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
//    })
//    @RequiresPermissions("match:matchschedule:page")
//    public Result<PageData<MatchScheduleDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
//
////        PageData<MatchScheduleDTO> page = matchScheduleService.page(params);
//
//        return new Result<PageData<MatchScheduleDTO>>().ok(page);
//    }

//    @GetMapping("{id}")
//    @ApiOperation("信息")
//    @RequiresPermissions("match:matchschedule:info")
//    public Result<MatchScheduleDTO> get(@PathVariable("id") Long id) {
//        MatchScheduleDTO data = matchScheduleService.get(id);
//
//        return new Result<MatchScheduleDTO>().ok(data);
//    }

//    @PostMapping
//    @ApiOperation("保存")
//    @LogOperation("保存")
//    @RequiresPermissions("match:matchschedule:save")
//    public Result save(@RequestBody MatchScheduleDTO dto) {
//        //效验数据
//        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
//
//        matchScheduleService.save(dto);
//
//        return new Result();
//    }

//    @PostMapping("getMatchList")
//    @ApiOperation("获取赛程")
//    @LogOperation("获取赛程")
//    public void getMatchList() throws Exception {
//        matchScheduleService.getMatchList();
//    }


    @PostMapping("realTimeStatistics")
    @ApiOperation("实时统计")
    @LogOperation("实时统计")
    public void realTimeStatistics() throws Exception {
        matchScheduleService.realTimeStatistics();

    }


//    @GetMapping("export")
//    @ApiOperation("导出")
//    @LogOperation("导出")
//    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
//        List<MatchScheduleDTO> list = matchScheduleService.list(params);
//        try {
//            ExcelUtils.exportExcelToTarget(response, null, "日程", list, MatchScheduleExcel.class);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}