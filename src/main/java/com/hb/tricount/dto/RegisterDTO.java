package com.hb.tricount.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
