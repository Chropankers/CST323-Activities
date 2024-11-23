package main.java.com.example.demo.service;

@Service
public class DiscordUserService {

    public String getUsername(OAuth2User oauth2User) {
        return oauth2User.getAttribute("username");
    }

    public String getAvatarUrl(OAuth2User oauth2User) {
        String id = oauth2User.getAttribute("id");
        String avatar = oauth2User.getAttribute("avatar");
    
        if (id == null || avatar == null) {
            return "https://cdn.discordapp.com/embed/avatars/0.png";
        }
    
        return "https://cdn.discordapp.com/avatars/" + id + "/" + avatar + ".png";
    }
    

    public String getDiscriminator(OAuth2User oauth2User) {
        return oauth2User.getAttribute("discriminator");
    }
}
