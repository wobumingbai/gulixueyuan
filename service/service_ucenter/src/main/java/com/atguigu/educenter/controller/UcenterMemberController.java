package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.UcenterMemberOrder;
import com.atguigu.commonutils.vo.UcenterMemberVo;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-09-02
 */
@RestController
@RequestMapping("/educenter/member")

public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member){

        String token = ucenterMemberService.login(member);

        return R.ok().data("token", token);
    }

    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);

        return R.ok();
    }

    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){

        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        UcenterMember member = ucenterMemberService.getById(memberId);

        return R.ok().data("userInfo", member);
    }

    // 远程调用接口，根据用户id，封装vo类，返回用户的头像和昵称
    @GetMapping("getUcenter/{memberId}")
    public UcenterMemberVo getUcenterById(@PathVariable("memberId") String memberId){
        UcenterMember member = ucenterMemberService.getById(memberId);
        UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();

        BeanUtils.copyProperties(member, ucenterMemberVo);
        return ucenterMemberVo;
    }

    // 远程调用接口，根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id){

        UcenterMember member = ucenterMemberService.getById(id);

        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();

        BeanUtils.copyProperties(member, ucenterMemberOrder);

        return ucenterMemberOrder;
    }

    // 远程调用接口，查询某一天注册的人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable("day") String day){
        Integer count = ucenterMemberService.countRegister(day);

        return R.ok().data("countRegister", count);
    }

}

