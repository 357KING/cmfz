package com.baizhi.cjw;

import com.baizhi.cjw.dao.GuruDao;
import com.baizhi.cjw.entity.Guru;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@SpringBootTest
public class TestPoi {

    @Autowired
    GuruDao guruDao;
    @Test
    public void test1(){
        //创建一个excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作簿
        HSSFSheet sheet = workbook.createSheet();
        //创建标题行 参数：行下标从0开始
        HSSFRow row = sheet.createRow(0);
        String[] s = {"ID","姓名","头像","状态","法号"};
        //创建一个的单元格 参数：下标从0开始
        HSSFCell cell = null;
        //将标题添加到单元格中
        for (int i = 0; i < s.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(s[i]);
        }
        List<Guru> gurus = guruDao.selectAll();
        for (int i = 0; i < gurus.size(); i++) {
            //遍历一次创建一行
            HSSFRow row1 = sheet.createRow(i + 1);
            //将数据添加到对应的单元格
            row1.createCell(0).setCellValue(gurus.get(i).getId());
            row1.createCell(1).setCellValue(gurus.get(i).getName());
            row1.createCell(2).setCellValue(gurus.get(i).getPhoto());
            row1.createCell(3).setCellValue(gurus.get(i).getStatus());
            row1.createCell(4).setCellValue(gurus.get(i).getNickName());
        }

        //导出单元格
        try {
            workbook.write(new FileOutputStream(new File("D://桌面//poi.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
