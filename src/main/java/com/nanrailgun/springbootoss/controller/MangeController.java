package com.nanrailgun.springbootoss.controller;

import com.nanrailgun.springbootoss.service.OssService;
import com.nanrailgun.springbootoss.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MangeController {

    @Autowired
    private OssService service;

    @GetMapping("/keys")
    public ResultVo getKeyList(){
        List<String> keyList = service.getKeyList();
        return ResultVo.builder().status_code(0).data(keyList).build();
    }
}
