package com.baizhi.cjw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BannerPageDto implements Serializable {
    //当前页
    private Integer page;
    //总页数
    private Integer total;
    //总行数
    private Integer records;
    //当前页的数据行
    private List<Banner> rows;
}
