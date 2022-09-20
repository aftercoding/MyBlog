package com.sean.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.sean.domain.ResponseResult;
import com.sean.enums.AppHttpCodeEnum;
import com.sean.exception.SystemException;
import com.sean.service.UploadService;
import com.sean.utils.PathUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @description: some desc
 * @author: congjun
 * @email: 66@7788.com
 * @date: 2022-09-20 18:00
 */
@ConfigurationProperties(prefix = "oss")
@Data
@Service
public class OssUploadServiceImpl implements UploadService {
    private String accessKey;
    private String secretKey;
    private String bucket;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //Todo 判断文件类型或者文件大小
        //获取原始文件名
        String originFileName = img.getOriginalFilename();
        //对原始文件名进行判断
        if(!originFileName.endsWith(".png") && !originFileName.endsWith(".jpg")){
            throw new SystemException(AppHttpCodeEnum.IMG_TYPE_ERROR);
        }
        String filePath = PathUtils.generateFilePath(originFileName);
        //如果判断通过上传文件到oss
        String url = uploadOSS(img, filePath);
        return ResponseResult.okResult(url);
    }

    private  String uploadOSS( MultipartFile imgFile, String filePath) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;

        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            InputStream inputStream = imgFile.getInputStream();

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
//                Response response = uploadManager.put(byteInputStream,key,upToken,null, null);
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return "http://rii08454z.hb-bkt.clouddn.com/" + key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
//            catch (UnsupportedEncodingException ex) {
            //ignore
        }
        return "www";
    }
}
