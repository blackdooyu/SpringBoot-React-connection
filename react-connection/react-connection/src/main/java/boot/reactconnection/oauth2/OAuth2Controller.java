package boot.reactconnection.oauth2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;


@Controller
public class OAuth2Controller {

    KakaoProfile kakaoProfile;
    KakaoToken kakaoToken;

    @ResponseBody
    @GetMapping("/auth/kakao/callback")
    public KakaoProfile kakaoCallback(String code){

        MultiValueMap<String, String> prams = new LinkedMultiValueMap<>();
        prams.add("grant_type","authorization_code");
        prams.add("client_id","f6a476d30aa5d9e81be42be6fb17c5e5");
        prams.add("redirect_uri","http://localhost:8080/auth/kakao/callback");
        prams.add("code",code);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(prams, headers);

        RestTemplate rt = new RestTemplate();

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();


        try {
            this.kakaoToken = objectMapper.readValue(response.getBody(), KakaoToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+ kakaoToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response2 = rt.exchange(
                "https://kapi.kakao.com/v1/api/talk/profile",
                HttpMethod.GET,
                kakaoProfileRequest,
                String.class
        );

        objectMapper = new ObjectMapper();


        try {
            kakaoProfile = objectMapper.readValue(response2.getBody(),KakaoProfile.class);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }


        return kakaoProfile;
    }

    @ResponseBody
    @GetMapping("/view")
    public KakaoProfile view() {
        return this.kakaoProfile;
    }

    @GetMapping("/test")
    public String test(HttpServletResponse response) {
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id=f6a476d30aa5d9e81be42be6fb17c5e5&redirect_uri=http://localhost:8080/auth/kakao/callback&response_type=code";
    }

    @Data
    static class KakaoToken{
        private String access_token;
        private String token_type;
        private String refresh_token;
        private int expires_in;
        private String scope;
        private int refresh_token_expires_in;
    }


}
