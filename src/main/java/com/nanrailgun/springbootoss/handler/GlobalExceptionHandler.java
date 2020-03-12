package com.nanrailgun.springbootoss.handler;

import com.nanrailgun.springbootoss.vo.ResultVo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 拦截文件过大异常
     * @param e
     * @return
     */
    @ExceptionHandler(MultipartException.class)
    public ResultVo SizeLimitExceededExceptionHandler(Exception e){
        return ResultVo.builder().status_code(1).message("上传失败 文件过大").build();
    }

}
