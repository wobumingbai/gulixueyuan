package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoData> {


    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println("*******" + data);
    }

    // 读取表头内容
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头： " + headMap);
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
