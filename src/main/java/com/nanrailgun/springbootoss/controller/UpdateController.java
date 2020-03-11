package com.nanrailgun.springbootoss.controller;

import com.nanrailgun.springbootoss.vo.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UpdateController {


    @PostMapping("/image")
    @ResponseBody
    public ResultVo updateImage(@RequestParam("image") MultipartFile file){

        if (file!=null && !file.isEmpty()){
            String name=file.getOriginalFilename();
            if (name!=null){
                int i = name.lastIndexOf(".");
                String fileType=name.substring(i);
                if (fileType.equals(".jpg")||fileType.equals(".png")||fileType.equals(".gif")){


                    return ResultVo.builder().status_code(0).message("上传成功!").build();
                }
            }
        }
        return ResultVo.builder().status_code(1).message("文件格式错误").build();
    }
}
