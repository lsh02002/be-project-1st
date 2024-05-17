package me.seho.beproject1st.web.controller;

import lombok.AllArgsConstructor;
import me.seho.beproject1st.service.JwtService;
import me.seho.beproject1st.domain.User;
import me.seho.beproject1st.service.AuthService;
import me.seho.beproject1st.web.dto.ResultResponse;
import me.seho.beproject1st.web.dto.auth.LoginRequest;
import me.seho.beproject1st.web.dto.auth.LoginResponse;
import me.seho.beproject1st.web.dto.auth.SignupRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {
    JwtService jwtService;
    AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<ResultResponse> signup(@RequestBody SignupRequest signupRequest){
        Boolean result = authService.signup(signupRequest);
        ResultResponse resultResponse;

        if(result){
            resultResponse = ResultResponse.builder()
                    .message("회원가입이 완료되었습니다.")
                    .build();

            return ResponseEntity.ok().body(resultResponse);
        }

        resultResponse = ResultResponse.builder()
                .message("회원가입이 잘 되지 않았습니다.")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        Optional<User> user = authService.login(loginRequest);

        LoginResponse loginResponse;

        if(user.isEmpty()){
            loginResponse = LoginResponse.builder()
                    .message("로그인에 실패 했습니다.").build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponse);
        }

        String token = jwtService.encode(Long.valueOf(user.get().getId()));

        HttpHeaders headers =new HttpHeaders();
        headers.set("Authorization", token);

        loginResponse = LoginResponse.builder()
                .message("로그인이 성공적으로 완료되었습니다.").build();

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(loginResponse);
    }

    @GetMapping("/is-existed-email/{author_email}")
    public Boolean isExistedEmail(@PathVariable(value = "author_email") String authorEmail) {
        return authService.isExistedEmail(authorEmail);
    }
}
