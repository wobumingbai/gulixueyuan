package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-19
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    // TODO 删除视频文件
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();

        videoWrapper.eq("course_id", courseId);
        videoWrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(videoWrapper);

        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            EduVideo eduVideo = eduVideoList.get(i);

            String videoSourceId = eduVideo.getVideoSourceId();

            if(!StringUtils.isEmpty(videoSourceId)){

                videoIds.add(videoSourceId);
            }
        }

        if(videoIds.size() > 0){
            vodClient.deleteBatch(videoIds);
        }

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("course_id", courseId);

        baseMapper.delete(queryWrapper);
    }
}
