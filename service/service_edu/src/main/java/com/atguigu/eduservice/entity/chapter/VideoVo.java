package com.atguigu.eduservice.entity.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoVo {

    private String id;

    private String title;

    @ApiModelProperty(value = "云端视频资源")
    private String videoSourceId;
}
