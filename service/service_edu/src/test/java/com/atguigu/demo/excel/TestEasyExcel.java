package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {

//        // 1.文件写入的位置
//        String filename = "E:\\install\\easyExcel\\write.xlsx";
//
//        // 2.调用easyExcel
//        // 文件路径名称，实体类的class
//        EasyExcel.write(filename, DemoData.class)
//                 .sheet("学生列表")
//                 .doWrite(getData());

         // 2.读操作
        String filename = "E:\\install\\easyExcel\\write.xlsx";

        EasyExcel.read(filename, DemoData.class, new ExcelListener())
                 .sheet()
                 .doRead();

    }

    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();

            data.setSno(i);
            data.setSname("lucy" + i);
            list.add(data);
        }

        return list;
    }
}
