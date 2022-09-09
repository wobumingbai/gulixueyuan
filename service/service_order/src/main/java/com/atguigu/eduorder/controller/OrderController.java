package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-09-06
 */
@RestController
@RequestMapping("/eduorder/order")

public class OrderController {

    @Autowired
    private OrderService orderService;

    // 生成订单
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String  courseId, HttpServletRequest request){
        // 用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        // 创建订单,返回订单号
        String orderNo = orderService.createOrders(courseId,memberId);

        return R.ok().data("orderId", orderNo);
    }

    // 根据订单号查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable("orderId") String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();

        wrapper.eq("order_no", orderId);

        Order order = orderService.getOne(wrapper);

        return R.ok().data("item", order);
    }
    // 根据课程id和用户id查询订单表中的状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();

        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status", 1);

        int count = orderService.count(wrapper);

        if(count > 0){
            return true;
        }else {
            return false;
        }
    }

}

