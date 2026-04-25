package com.institute.auth.service;

import com.institute.auth.dto.AuthResponse;
import com.institute.auth.dto.LoginRequest;
import com.institute.auth.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}