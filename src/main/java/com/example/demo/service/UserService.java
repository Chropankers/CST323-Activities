package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(@Valid User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setSex(userDTO.getSex());
        user.setAge(userDTO.getAge());
        user.setState(userDTO.getState());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public User saveOrUpdateUserFromOAuth2(OAuth2User oauth2User) {
        String discordId = oauth2User.getAttribute("id");
        Optional<User> existingUser = userRepository.findByDiscordId(discordId);

        User user = existingUser.orElse(new User());
        user.setDiscordId(discordId);
        user.setUsername(oauth2User.getAttribute("username"));
        user.setAvatarUrl(oauth2User.getAttribute("avatar"));
        user.setDiscriminator(oauth2User.getAttribute("discriminator"));
        user.setUpdatedAt(java.time.LocalDateTime.now());

        return userRepository.save(user);
    }
}
