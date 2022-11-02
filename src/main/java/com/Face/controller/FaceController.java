package com.Face.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/face")
public class FaceController {
    @PostMapping("/detect")
    public String FaceDetect(){
        return new String("检测开始");
    }
}
