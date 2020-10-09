package com.web.blog.service;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.stereotype.Service;

@Service
public class KakaoService {
    
   public String getAccessToken (final String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        final String reqURL = "https://kauth.kakao.com/oauth/token";
        System.out.println("[logger - getAccessToken method]");
        System.out.println(authorize_code);
        try {
            final URL url = new URL(reqURL);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            final StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=5a3a01519efdeba53b7a039bffafd62d");
            // sb.append("&redirect_uri=http://localhost:8080/account/kakaoLogin");
            sb.append("&redirect_uri=http://i3b203.p.ssafy.io:8080/account/kakaoLogin");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();
//            System.out.println(sb);
            
            //    결과 코드가 200이라면 성공
            final int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
 
            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            final BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            
            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            final JsonParser parser = new JsonParser();
            final JsonElement element = parser.parse(result);
            
            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
            
            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);
            
            br.close();
            bw.close();
        } catch (final IOException e) {
            e.printStackTrace();
        } 
        
        return access_Token;
    }
    
   public HashMap<String, Object> getUserInfo (final String access_Token) {
       
       //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
       final HashMap<String, Object> userInfo = new HashMap<>();
       final String reqURL = "https://kapi.kakao.com/v2/user/me";
      
       System.out.println("[logger - getUserInfo method]");
       System.out.println(access_Token);
       try {
           final URL url = new URL(reqURL);
           final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           conn.setRequestMethod("POST");
           System.out.println(conn.toString());
           
           //    요청에 필요한 Header에 포함될 내용
           // System.out.println("!! "+access_Token);
         
           conn.setRequestProperty("Authorization", "Bearer " + access_Token.trim());
           
           final int responseCode = conn.getResponseCode();
           System.out.println("responseCode : " + responseCode);
           
           final BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           
           String line = "";
           String result = "";
           
           while ((line = br.readLine()) != null) {
               result += line;
           }
           System.out.println("response body : " + result);
           
           final JsonParser parser = new JsonParser();
           final JsonElement element = parser.parse(result);
           
           final JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
           final JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
           
           final String nickname = properties.getAsJsonObject().get("nickname").getAsString();
           final String email = kakao_account.getAsJsonObject().get("email").getAsString();
           
           userInfo.put("nickname", nickname);
           userInfo.put("email", email);
           System.out.println(userInfo);
           System.out.println(userInfo.toString());
       } catch (final IOException e) {
           e.printStackTrace();
       }
       
       return userInfo;
   }

}