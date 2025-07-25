package com.hb.tricount.controller;

import com.hb.tricount.business.GroupService;
import com.hb.tricount.dto.CreateGroupDTO;
import com.hb.tricount.dto.GroupDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestBody CreateGroupDTO dto) {
        GroupDTO created = groupService.createGroup(dto);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<List<GroupDTO>> getGroupsByPerson(@PathVariable Long personId) {
        return ResponseEntity.ok(groupService.getGroupsByPerson(personId));
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<Long>> getMemberIds(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.getMemberIds(groupId));
    }
}
