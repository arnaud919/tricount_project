package com.hb.tricount.controller;

import com.hb.tricount.business.GroupService;
import com.hb.tricount.dto.CreateGroupDTO;
import com.hb.tricount.dto.GroupDTO;
import com.hb.tricount.entity.Person;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<GroupDTO> createGroup(@RequestBody CreateGroupDTO dto,
            @AuthenticationPrincipal Person creator) {
        GroupDTO createdGroup = groupService.createGroup(dto, creator);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    /**
     * 
     * Récupération des groupes d'une personne
     * 
     * @param personId
     * @return
     */
    @GetMapping("/person/{personId}")
    public ResponseEntity<List<GroupDTO>> getGroupsByPerson(@PathVariable Long personId) {
        return ResponseEntity.ok(groupService.getGroupsByPerson(personId));
    }

    /**
     * 
     * Récupérations des membres d'un groupes
     * 
     * @param groupId
     * @return
     */
    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<Long>> getMemberIds(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.getMemberIds(groupId));
    }

    /**
     * 
     * Récupération de ses groupes en s'identifiant
     * 
     * @param person
     * @return
     */
    @GetMapping("/my")
    public ResponseEntity<List<GroupDTO>> getGroupsForCurrentUser(@AuthenticationPrincipal Person person) {
        return ResponseEntity.ok(groupService.getGroupsForUser(person));
    }

}
