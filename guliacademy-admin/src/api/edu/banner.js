import request from '@/utils/request'
export default {
    pageBanner(current,limit) {
        return request({
            url: `/educms/banneradmin/pageBanner/${current}/${limit}`,
            method: 'get'
          })
    },
    addBanner(banner) {
        return request({
            url: `/educms/banneradmin/addBanner`,
            method: 'post',
            data: banner
          })
    },
    getbanner(id) {
        return request({
            url: `/educms/banneradmin/get/` + id,
            method: 'get'
          })
    },
    updatebanner(banner) {
        return request({
            url: `/educms/banneradmin/update`,
            method: 'put',
            data:banner
          })
    },
    removebanner(id) {
        return request({
            url: `/educms/banneradmin/remove/` + id,
            method: 'delete'
          })
    }

}