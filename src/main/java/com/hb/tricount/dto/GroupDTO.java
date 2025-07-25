package com.hb.tricount.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GroupDTO {
    private Long id;
    private String name;
    private List<Long> memberIds;
}
