package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-09-06
 */
@RestController
@RequestMapping("/eduorder/paylog")

public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    // 生成微信支付二维码
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        // 返回信息，包含二维码地址，还有其他信息
        Map map = payLogService.createNative(orderNo);

        return R.ok().data(map);
    }

    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){

        Map<String, String> map = payLogService.queryPayStatus(orderNo);

        if(map == null){
            return R.error().message("支付出错");
        }

        // 如果返回map里面为空,通过map获取订单状态
        if(map.get("trade_state").equals("SUCCESS")){
            // 添加记录到支付表，更新订单表订单状态
            payLogService.updateOrderStatus(map);

            return R.ok().message("支付成功");
        }

        return R.ok().code(25000).message("支付中");
    }

}

