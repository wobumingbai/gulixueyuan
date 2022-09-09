package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-19
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();

        wrapperChapter.eq("course_id", courseId);

        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);


        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();

        wrapperVideo.eq("course_id", courseId);

        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        List<ChapterVo> finalList = new ArrayList<>();

        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);

            ChapterVo chapterVo = new ChapterVo();

            BeanUtils.copyProperties(eduChapter, chapterVo);

            finalList.add(chapterVo);

            List<VideoVo> videoList = new ArrayList<>();

            for (int m = 0; m < eduVideoList.size(); m++) {

                // i 忘记改成 m 了，差点害死人
                EduVideo eduVideo = eduVideoList.get(m);

                if(eduVideo.getChapterId().equals(eduChapter.getId())){

                    VideoVo videoVo = new VideoVo();

                    BeanUtils.copyProperties(eduVideo, videoVo);

                    videoList.add(videoVo);
                }
            }

            chapterVo.setChildren(videoList);
        }


        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        // 根据章节查小节

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();

        wrapper.eq("chapter_id", chapterId);

        int count = videoService.count(wrapper);

        if(count > 0) {
            throw new GuliException(20001, "不能删除");
        } else{
            int result = baseMapper.deleteById(chapterId);

            return result > 0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();

        wrapper.eq("course_id", courseId);

        baseMapper.delete(wrapper);
    }
}
