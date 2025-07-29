package com.hb.tricount.business;

import com.hb.tricount.dto.RegisterDTO;
import com.hb.tricount.dto.LoginDTO;
import com.hb.tricount.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterDTO dto);
    AuthResponseDTO login(LoginDTO dto);
}
