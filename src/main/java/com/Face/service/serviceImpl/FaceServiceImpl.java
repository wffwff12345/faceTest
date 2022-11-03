package com.Face.service.serviceImpl;

import com.Face.service.FaceService;
import com.baidu.aip.face.AipFace;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

@Service
public class FaceServiceImpl implements FaceService {
    private HashMap<String, String> map = new HashMap<>();
    private AipFace aipFace;

    public FaceServiceImpl() {
        map.put("quality_control", "NORMAL");
        map.put("liveness_control", "LOW");
    }

    @PostConstruct
    public void init() {
        aipFace = new AipFace("28208907", "LGpL5SWmL01WRcMcOmvzXyDx", "VHmutq14cmEMw1qXK7OvdzlFPjwp6j7H");
    }

    @Override
    public String FaceRegister(String UserId, String image) {
        JSONObject result = aipFace.addUser(image, "BASE64", "test", UserId, map);
        System.out.println(result);
        Integer error_code = result.getInt("error_code");
        return error_code == 0 ? "成功" : "失败";
    }

    @Override
    public String FaceDetect(String image) {
        JSONObject result = aipFace.detect(image, "BASE64", map);
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
        JSONObject searchResult = aipFace.search(image, "BASE64", "test", map);
        System.out.println(searchResult);
        if (searchResult.has("error_code") && searchResult.getInt("error_code") == 0) {
            JSONObject result = searchResult.getJSONObject("result");
            JSONArray userList = result.getJSONArray("user_list");
            if (userList.length() > 0) {
                JSONObject user = userList.getJSONObject(0);
                double score = user.getDouble("score");
                if (score > 80) {
                    return "成功"+" "+"用户ID为:"+user.getString("user_id");
                }
            }
        }
        return "失败";
    }
}
