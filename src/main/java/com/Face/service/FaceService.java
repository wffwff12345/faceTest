package com.Face.service;

import org.springframework.web.multipart.MultipartFile;

public interface FaceService {
    String FaceRegister(String UserId,String image);
    String FaceDetect(String image);
    String FaceLogin(String image);
}
