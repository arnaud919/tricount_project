package com.hb.tricount.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreateGroupDTO {
    private String name;
    private List<Long> memberIds;
}
