package com.Face.service;

public interface FaceService {

    String FaceRegister(String UserId,String image);

    String FaceDetect(String image);

    String FaceLogin(String image);
}
