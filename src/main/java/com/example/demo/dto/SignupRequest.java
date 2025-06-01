// src/main/java/com/example/demo/dto/SignupRequest.java
package com.example.demo.dto;

public class SignupRequest {
    private String username;
    private String password;
    private String passwordConfirm;

    // 기본 생성자 (Spring이 @ModelAttribute로 바인딩할 때 필요)
    public SignupRequest() {
    }

    // username
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    // password
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // passwordConfirm
    public String getPasswordConfirm() {
        return passwordConfirm;
    }
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
