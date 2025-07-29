package com.hb.tricount.business;

import java.util.List;

import com.hb.tricount.dto.ParticipantShareDTO;

public interface ParticipantShareService {
    public List<ParticipantShareDTO> getSharesByGroupId(Long groupId);
}
