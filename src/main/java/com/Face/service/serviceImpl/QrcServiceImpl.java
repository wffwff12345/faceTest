package com.Face.service.serviceImpl;

import com.Face.service.QrcService;
import com.baidu.aip.util.Base64Util;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;

@PropertySource("config.properties")
@Service
public class QrcServiceImpl implements QrcService {
    @Value("${apiFace.QrcBaseUrl:http://10.1.11.44:4202/}")
    private String url;

    @Override
    public String MakeQrc() throws Exception {
        String id = IdWorker.getId() + "";
        //String content = "https://www.baidu.com/";
        String content = "http://10.1.11.44:4202/first?code=" + id;
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix encode = writer.encode(content, BarcodeFormat.QR_CODE, 200, 200);
        Path path = new File("D:\\FaceTest\\code\\test1.png").toPath();
        MatrixToImageWriter.writeToPath(encode, "png", path);
        return "成功";

    }

    @Override
    public String MakeQrcByBaseUrl() throws Exception {
        String id = IdWorker.getId() + "";
        //String content = "https://www.baidu.com/";
        //String content = "http://10.1.11.44:4202/";
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix encode = writer.encode(url, BarcodeFormat.QR_CODE, 200, 200);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        BufferedImage image = MatrixToImageWriter.toBufferedImage(encode);
        ImageIO.write(image, "png", os);
        String encodes = Base64Util.encode(os.toByteArray());
        return new String("data:image/png;base64," + encodes);
    }
}
