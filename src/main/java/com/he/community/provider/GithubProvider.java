package com.he.community.provider;

import com.alibaba.fastjson.JSON;
import com.he.community.dto.AccessTokenDTO;
import com.he.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediatype = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediatype, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string  =  response.body().string();
            String token =  string.split("&")[0].split("=")[1];
            /*System.out.println(token);*/
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            /*System.out.println("https://api.github.com/user?access_token=" + accessToken);*/
            Response response = client.newCall(request).execute();
            String allstring  =  response.body().string();
            GithubUser githubUser = JSON.parseObject(allstring, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
