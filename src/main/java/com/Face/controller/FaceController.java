package com.Face.controller;

import com.Face.service.FaceService;
import com.Face.service.QrcService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/app/face")
public class FaceController {
    @Autowired
    FaceService faceService;
    @Autowired
    QrcService qrcService;
    @PostMapping("/register")
    public String FaceRegister(MultipartFile file) throws Exception {
        String imgBase64 = Base64.encode(file.getBytes());
        String  id = IdWorker.getId()+"";
        System.out.println(id);
        String result = faceService.FaceRegister(id, imgBase64);
        return new String("图片注册"+result);
    }
    @PostMapping("/detect")
    public String FaceDetect(MultipartFile file) throws Exception {
        String imgBase64 = Base64.encode(file.getBytes());
        String result = faceService.FaceDetect(imgBase64);
        return new String("图片检测"+result);
    }
    @PostMapping("/login")
    public String FaceLogin(MultipartFile file) throws Exception {
        String imgBase64 = Base64.encode(file.getBytes());
        String result = faceService.FaceLogin(imgBase64);
        return new String("登录验证"+result);
    }
    @GetMapping("/code")
    public  String QrcCode() throws Exception {
        String res = qrcService.MakeQrc();
        return new String(res);
    }
    @GetMapping("/baseUrl")
    public String QrcCodeByBaseUrl() throws Exception {
        String res = qrcService.MakeQrcByBaseUrl();
        return res;
    }
}
