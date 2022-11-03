package com.Face.service.serviceImpl;

import com.Face.service.FaceService;
import com.baidu.aip.face.AipFace;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
@PropertySource("config.properties")
public class FaceServiceImpl implements FaceService {
    @Value("${aipFace.appId}")
    private String appId;
    @Value("${aipFace.apiKey}")
    private String apiKey;
    @Value("${aipFace.secretKey}")
    private String secretType;
    @Value("${aipFace.imageType}")
    private String imageType;
    @Value("${aipFace.groupId}")
    private String groupId;
    private HashMap<String, String> map = new HashMap<>();
    private AipFace aipFace;

    public FaceServiceImpl() {
        map.put("quality_control", "NORMAL");
        map.put("liveness_control", "LOW");
    }

    @PostConstruct
    public void init() {
        aipFace = new AipFace(appId, apiKey, secretType);
    }

    @Override
    public String FaceRegister(String UserId, String image) {
        JSONObject result = aipFace.addUser(image, imageType, groupId, UserId, map);
        System.out.println(result);
        Integer error_code = result.getInt("error_code");
        return error_code == 0 ? "成功" : "失败";
    }

    @Override
    public String FaceDetect(String image) {
        JSONObject result = aipFace.detect(image, imageType, map);
        System.out.println(result);
        if (result.has("error_code") && result.getInt("error_code") == 0) {
            JSONObject jsonObject = result.getJSONObject("result");
            Integer face_num = jsonObject.getInt("face_num");
            return face_num == 1 ? "成功" : "失败";
        } else {
            return "失败";
        }
    }

    @Override
    public String FaceLogin(String image) {
        JSONObject searchResult = aipFace.search(image, imageType, groupId, map);
        System.out.println(searchResult);
        if (searchResult.has("error_code") && searchResult.getInt("error_code") == 0) {
            JSONObject result = searchResult.getJSONObject("result");
            JSONArray userList = result.getJSONArray("user_list");
            if (userList.length() > 0) {
                JSONObject user = userList.getJSONObject(0);
                double score = user.getDouble("score");
                if (score > 80) {
                    return "成功" + " " + "用户ID为:" + user.getString("user_id");
                }
            }
        }
        return "失败";
    }
}
