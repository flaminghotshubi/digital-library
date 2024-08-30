package com.example.demo_jpa.dto;

import com.example.demo_jpa.model.Admin;
import com.example.demo_jpa.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public Admin to() {
        return Admin.builder()
                .name(name)
                .user(User.builder().password(password).username(username).build())
                .build();
    }
}
