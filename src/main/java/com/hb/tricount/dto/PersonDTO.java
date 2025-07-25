package com.hb.tricount.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PersonDTO {
    private Long id;
    private String name;
    private String email;
}
