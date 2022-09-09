package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-18
 */
@RestController
@RequestMapping("/eduservice/subject")

public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    // 获取上传的文件，读取文件内容
    @PostMapping("addSubject")
    public R addSubjcet(MultipartFile file){

        eduSubjectService.saveSubjcet(file, eduSubjectService);

        return R.ok();
    }
    
    // 课程分类列表
    @GetMapping("getAllSubject")
    public R getAllSubject(){

        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();

        return R.ok().data("list", list);
    }
    
    
}

