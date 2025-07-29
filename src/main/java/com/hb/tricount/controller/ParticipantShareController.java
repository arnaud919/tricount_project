package com.hb.tricount.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.tricount.business.ParticipantShareService;
import com.hb.tricount.dto.ParticipantShareDTO;

@RestController
@RequestMapping("/api/shares")
public class ParticipantShareController {

    private final ParticipantShareService participantShareService;

    public ParticipantShareController(ParticipantShareService participantShareService) {
        this.participantShareService = participantShareService;
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ParticipantShareDTO>> getShares(@PathVariable Long groupId) {
        return ResponseEntity.ok(participantShareService.getSharesByGroupId(groupId));
    }
}
