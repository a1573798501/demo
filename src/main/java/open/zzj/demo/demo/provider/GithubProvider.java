package open.zzj.demo.demo.provider;



import com.alibaba.fastjson.JSON;
import okhttp3.*;

import open.zzj.demo.demo.dto.AccessTokenDTO;
import open.zzj.demo.demo.dto.GithubUser;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String url="https://github.com/login/oauth/access_token";

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String cachestr = response.body().string();
//            System.out.println(cachestr);
            String token = cachestr.split("&")[0].split("=")[1];

            return token;
//            System.out.println(response.body().string());
//            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){


        String url = "https://api.github.com/user?access_token=" + accessToken;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
           String cachestr = response.body().string();
            GithubUser githubUser = JSON.parseObject(cachestr, GithubUser.class);
            return githubUser;
            //System.out.println(cachestr);
            //return cachestr;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}
