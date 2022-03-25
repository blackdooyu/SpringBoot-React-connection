package boot.reactconnection.securityoauth2;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class OAuth2Attributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    public OAuth2Attributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public OAuth2Attributes() {
    }

    public static OAuth2Attributes of(String registrationId,String userNameAttributeName,Map<String,Object> attributes){
        return ofKakao(userNameAttributeName, attributes);
    }

    public static OAuth2Attributes ofKakao(String userNameAttributeName,Map<String,Object> attributes){
        System.out.println("==============================");
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");

        return new OAuth2Attributes(attributes,
                    userNameAttributeName,
                (String) profile.get("nickname"),
                (String) kakao_account.get("email"),
                (String) profile.get("profile_image_url"));
    }

    public User toEntity() {
        return new User(name, email, picture);
    }
}
