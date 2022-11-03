package com.Face.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface QrcService {
    String MakeQrc() throws Exception;
    String MakeQrcByBaseUrl() throws Exception;
}
