package me.seho.beproject1st.service;

import lombok.AllArgsConstructor;
import me.seho.beproject1st.domain.User;
import me.seho.beproject1st.repository.UserRepository;
import me.seho.beproject1st.web.dto.auth.LoginRequest;
import me.seho.beproject1st.web.dto.auth.SignupRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public Boolean signup(SignupRequest signupRequest) {
        try {

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            User userFound = User.builder()
                    .email(signupRequest.getEmail())
                    .password(passwordEncoder.encode(signupRequest.getPassword()))
                    .build();

            userRepository.save(userFound);

            return true;
        } catch (RuntimeException e){
            e.printStackTrace();
            return false;
        }
    }

    public Optional<User> login(LoginRequest request){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if(user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            return user;
        }

        return Optional.empty();
    }

    public Boolean isExistedEmail(String authorEmail){
        try {
            Optional<User> user = userRepository.findByEmail(authorEmail);
            return user.isPresent();

        } catch (RuntimeException e){
            e.printStackTrace();

            return false;
        }
    }
}
