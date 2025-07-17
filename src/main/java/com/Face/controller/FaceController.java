package com.Face.controller;

import com.Face.service.FaceService;
import com.Face.service.QrcService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/app/face")
public class FaceController {

    @Resource
    FaceService faceService;

    @Resource
    QrcService qrcService;

    @PostMapping("/objectDetect")
    public String objectDetect(@RequestParam("files") List<MultipartFile> files){
        for (int i = 0; i < files.size(); i++) {
            String filename = files.get(i).getOriginalFilename();
        }
        return "test";
    }

    @PostMapping("/register")
    public String FaceRegister(MultipartFile file) throws Exception {
        String imgBase64 = Base64.encode(file.getBytes());
        String id = IdWorker.getId() + "";
        System.out.println(id);
        String result = faceService.FaceRegister(id, imgBase64);
        return new String("图片注册" + result);
    }

    @PostMapping("/detect")
    public String FaceDetect(MultipartFile file) throws Exception {
        String imgBase64 = Base64.encode(file.getBytes());
        String result = faceService.FaceDetect(imgBase64);
        return new String("图片检测" + result);
    }

    @PostMapping("/login")
    public String FaceLogin(MultipartFile file) throws Exception {
        String imgBase64 = Base64.encode(file.getBytes());
        String result = faceService.FaceLogin(imgBase64);
        return new String("登录验证" + result);
    }

    @GetMapping("/code")
    public String QrcCode() throws Exception {
        String res = qrcService.MakeQrc();
        return new String(res);
    }

    @GetMapping("/baseUrl")
    public String QrcCodeByBaseUrl() throws Exception {
        String res = qrcService.MakeQrcByBaseUrl();
        return res;
    }
}
