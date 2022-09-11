import request from '@/utils/request'
export default {

    //添加小节
    addVideo(video) {
        return request({
            url: '/eduservice/video/addVideo',
            method: 'post',
            data: video
          })
    },
    
    //删除小节
    deleteVideo(id) {
        return request({
            url: '/eduservice/video/'+id,
            method: 'delete'
          })
    },

    getVideo(id) {
        return request({
            url: '/eduservice/video/'+id,
            method: 'get'
          })
    },
    updateVideo(video) {
        return request({
            url: '/eduservice/video/updateVideo',
            method: 'post',
            data: video
          })
    },
    deleteAliyunvod(id) {
        return request({
            url: '/eduvod/video/removeAlyVideo',
            method: 'delete',
          })
    },
}