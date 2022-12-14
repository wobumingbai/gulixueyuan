package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    // 该类不能交给spring管理，需要手动new

    public EduSubjectService subjectService;

    public SubjectExcelListener(){}

    public SubjectExcelListener(EduSubjectService subjectService){
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext context) {
        if(subjectData == null) {
            throw new GuliException(20001, "文件数据为空");
        }

        // 一行一行读取 每次读取两个值 分别为一级分类和二级分类
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());

        if(existOneSubject == null){
            existOneSubject = new EduSubject();

            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());

            subjectService.save(existOneSubject);
        }

        // 获取一级分类ID值
        String pid = existOneSubject.getId();

        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null){
            existTwoSubject = new EduSubject();

            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());

            subjectService.save(existTwoSubject);
        }

    }
    
    // 判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService, String  name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();

        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");

        EduSubject oneSubject = subjectService.getOne(wrapper);

        return oneSubject;
    }

    // 判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService, String name, String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();

        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);

        EduSubject twoSubject = subjectService.getOne(wrapper);

        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
