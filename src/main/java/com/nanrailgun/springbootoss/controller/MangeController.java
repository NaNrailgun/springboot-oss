package com.nanrailgun.springbootoss.controller;

import com.nanrailgun.springbootoss.service.OssService;
import com.nanrailgun.springbootoss.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MangeController {

    @Autowired
    private OssService service;

    @GetMapping("/keys")
    public ResultVo getKeyList(){
        return ResultVo.builder().status_code(0).data(service.getKeyMap()).build();
    }

    @DeleteMapping("/keys")
    public ResultVo deleteKeys(@RequestParam("image_name") String key){
        if (service.deleteKeys(key)){
            return ResultVo.builder().status_code(0).message("删除成功").build();
        }
        return ResultVo.builder().status_code(1).message("删除失败").build();
    }
}
