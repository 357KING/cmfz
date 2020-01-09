package com.baizhi.cjw.util;

import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ImageConverter extends StringImageConverter {
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws IOException {
        //需要将value 有相对路径|网络路径 改为 绝对路径
        //固定的参数“user.dir”
        String property = System.getProperty("user.dir");
        String[] split = value.split("/");
        value = split[split.length - 1];
        String url = property + "\\src\\main\\webapp\\upload\\img\\" + value;
        return new CellData(FileUtils.readFileToByteArray(new File(url)));
    }

}
