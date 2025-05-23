package com.hahaha.musicshare.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AIAssistantUtils {
    static String[] names = {
            "风", // 自然现象（单字）
            "石子", // 日常物品
            "云", // 自然现象（单字）
            "纸船",
            "光", // 抽象概念（单字）
            "木纹",
            "溪", // 自然地理（单字）
            "布褶",
            "影", // 光影现象（单字）
            "铃音",
            "痕", // 抽象痕迹（单字）
            "叶脉",
            "声", // 感官元素（单字）
            "台阶",
            "隙" // 空间概念（单字）
    };

    static class Message {
        String role;
        String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    static class RequestBody {
        String model;
        Message[] messages;

        public RequestBody(String model, Message[] messages) {
            this.model = model;
            this.messages = messages;
        }
    }

    // 获取取名风格
    public static String getNamingStyle() {
        try {
            // 创建请求体
            RequestBody requestBody = new RequestBody(
                    "qwen-plus",
                    new Message[] {
                            new Message("system", "You are a helpful assistant."),
                            new Message("user", "给我一个描述风格的词汇，和"+names[(int) (Math.random()* names.length)]+"有关")
                    }
            );

            String apiKey = "sk-d0de4f307b604dcbaba162070b01b1d0";
            String result = sendRequest(requestBody, apiKey);
            return result.substring(result.indexOf("content") + 10, result.indexOf("finish_reason") - 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "潇洒";  // 默认风格
    }

    // 创建名字，加载风格
    public static String createName() {
        try {
            // 获取取名风格
            String namingStyle = getNamingStyle();

            // 创建请求体，使用获取的风格
            RequestBody requestBody = new RequestBody(
                    "qwen-plus",
                    new Message[] {
                            new Message("system", "You are a helpful assistant."),
                            new Message("user", "简练回复，根据以下要求取名：和" + namingStyle + "有关，最终的名字有" + names[(int) (Math.random()* names.length)] + "字")
                    }
            );

            String apiKey = "sk-d0de4f307b604dcbaba162070b01b1d0";
            String result = sendRequest(requestBody, apiKey);
            return trimName(result.substring(result.indexOf("content") + 10, result.indexOf("finish_reason") - 4));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "AAA";
    }

    public static String trimName(String strs) {
        try {
            // 创建请求体
            RequestBody requestBody = new RequestBody(
                    "qwen-plus",
                    new Message[] {
                            new Message("system", "You are a helpful assistant."),
                            new Message("user", strs+"阅读本段文字，取出最恰当的名字美化后回复，只回复名字！")
                    }
            );

            String apiKey = "sk-d0de4f307b604dcbaba162070b01b1d0";
            String result = sendRequest(requestBody, apiKey);
            return result.substring(result.indexOf("content") + 10, result.indexOf("finish_reason") - 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "开个玩笑，再来一次";  // 默认风格
    }

    private static String sendRequest(RequestBody requestBody, String apiKey) throws Exception {
        // 将请求体转换为 JSON
        Gson gson = new Gson();
        String jsonInputString = gson.toJson(requestBody);

        // 创建 URL 对象
        URL url = new URL("https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        // 设置请求方法为 POST
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");

        String auth = "Bearer " + apiKey;
        httpURLConnection.setRequestProperty("Authorization", auth);

        // 启用输入输出流
        httpURLConnection.setDoOutput(true);

        // 写入请求体
        try (OutputStream os = httpURLConnection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 获取响应码
        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // 读取响应体
        try (BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("Response Body: " + response);
            String result = response.toString();
            return result;
        }
    }
}
