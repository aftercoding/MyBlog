package com.sean.service;

import com.sean.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-20 17:59
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);

}
