package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R Exceptionerror(Exception e){
        e.printStackTrace();

        return R.error().message("执行了全局异常处理");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R ArithmeticExceptionerror(ArithmeticException e){
        e.printStackTrace();

        return R.error().message("执行了算数异常处理");
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R GuliExceptionerror(GuliException e){

        log.error(e.getMessage());

        e.printStackTrace();

        return R.error().code(e.getCode()).message(e.getMsg());
    }




}
