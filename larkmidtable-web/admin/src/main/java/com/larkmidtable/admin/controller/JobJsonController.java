package com.larkmidtable.admin.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.larkmidtable.admin.dto.JsonBuildDto;
import com.larkmidtable.admin.service.JsonService;
import com.larkmidtable.admin.core.util.I18nUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/flinkxJson")
@Api(tags = "组装flinkx  json的控制器")
public class JobJsonController extends BaseController {

    @Autowired
    private JsonService jsonService;


    @PostMapping("/buildJson")
    @ApiOperation("JSON构建")
    public R<String> buildJobJson(@RequestBody JsonBuildDto dto) {
        String key = "system_please_choose";
        if (dto.getReaderDatasourceId() == null) {
            return failed(I18nUtil.getString(key) + I18nUtil.getString("jobinfo_field_readerDataSource"));
        }
        if (dto.getWriterDatasourceId() == null) {
            return failed(I18nUtil.getString(key) + I18nUtil.getString("jobinfo_field_writerDataSource"));
        }
        if (CollectionUtils.isEmpty(dto.getReaderColumns())) {
            return failed(I18nUtil.getString(key) + I18nUtil.getString("jobinfo_field_readerColumns"));
        }
        if (CollectionUtils.isEmpty(dto.getWriterColumns())) {
            return failed(I18nUtil.getString(key) + I18nUtil.getString("jobinfo_field_writerColumns"));
        }

        if("flinkx".equals(dto.getType())) {
            return success(jsonService.buildJobFlinkxJson(dto));
        }else {
            return success(jsonService.buildJobDataxJson(dto));
        }
    }
}
