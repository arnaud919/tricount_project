package com.hb.tricount.business.impl;

import com.hb.tricount.business.GroupService;
import com.hb.tricount.dto.CreateGroupDTO;
import com.hb.tricount.dto.GroupDTO;
import com.hb.tricount.entity.Group;
import com.hb.tricount.entity.Person;
import com.hb.tricount.repository.GroupRepository;
import com.hb.tricount.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final PersonRepository personRepository;

    public GroupServiceImpl(GroupRepository groupRepository, PersonRepository personRepository) {
        this.groupRepository = groupRepository;
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public GroupDTO createGroup(CreateGroupDTO dto) {
        List<Person> members = personRepository.findAllById(dto.getMemberIds());

        Group group = Group.builder()
                .name(dto.getName())
                .members(members)
                .build();

        Group saved = groupRepository.save(group);

        return GroupDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .memberIds(saved.getMembers().stream()
                        .map(Person::getId)
                        .toList())
                .build();
    }

    @Override
    public List<GroupDTO> getGroupsByPerson(Long personId) {
        return groupRepository.findByMembers_Id(personId)
                .stream()
                .map(group -> GroupDTO.builder()
                        .id(group.getId())
                        .name(group.getName())
                        .memberIds(group.getMembers().stream()
                                .map(Person::getId)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getMemberIds(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Groupe introuvable"))
                .getMembers()
                .stream()
                .map(Person::getId)
                .collect(Collectors.toList());
    }
}
