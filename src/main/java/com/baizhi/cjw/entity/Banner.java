package com.baizhi.cjw.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.fastjson.annotation.JSONField;
import com.baizhi.cjw.util.ImageConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ContentRowHeight(30) //内容行高
@HeadRowHeight(20)    //头部行高
@ColumnWidth(25)      //列宽
public class Banner implements Serializable {

  @Id
  @ExcelProperty(value = {"轮播图","ID"})
  private String id;
  @ExcelProperty(value = {"轮播图","标题"})
  private String title;

  @ExcelProperty(value = {"轮播图","图片"},converter =ImageConverter.class)
  private String url;
  @ExcelProperty(value = {"轮播图","链接"})
  private String href;
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @ExcelProperty(value = {"轮播图","时间"})
  private java.util.Date createDate;
  @ExcelProperty(value = {"轮播图","描述"})
  private String description;
  @ExcelProperty(value = {"轮播图","状态"})
  private String status;

}
