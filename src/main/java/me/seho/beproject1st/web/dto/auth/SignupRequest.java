package me.seho.beproject1st.web.dto.auth;

import lombok.*;
@Getter
@Setter
public class SignupRequest {
    private String email;
    private String password;
}
