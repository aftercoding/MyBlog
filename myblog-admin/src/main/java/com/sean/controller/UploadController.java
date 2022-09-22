package com.sean.controller;

import com.sean.domain.ResponseResult;
import com.sean.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-20 17:51
 */

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile multipartFile) throws IOException {
        return uploadService.uploadImg(multipartFile);
    }
//    @PostMapping("/upload")
//    public ResponseResult uploadImg(@RequestParam("img") MultipartFile multipartFile) {
//        try {
//            return uploadService.uploadImg(multipartFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("文件上传上传失败");
//        }
//    }
}
