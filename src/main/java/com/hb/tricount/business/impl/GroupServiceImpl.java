package com.hb.tricount.business.impl;

import com.hb.tricount.business.GroupService;
import com.hb.tricount.dto.CreateGroupDTO;
import com.hb.tricount.dto.GroupDTO;
import com.hb.tricount.entity.Group;
import com.hb.tricount.entity.Person;
import com.hb.tricount.repository.GroupRepository;
import com.hb.tricount.repository.PersonRepository;
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
        public GroupDTO createGroup(CreateGroupDTO dto, Person creator) {
                List<Person> members = personRepository.findAllById(dto.getMemberIds());

                // On s'assure que le créateur est aussi membre
                if (!members.contains(creator)) {
                        members.add(creator);
                }

                Group group = Group.builder()
                                .name(dto.getName())
                                .members(members)
                                .creator(creator)
                                .build();

                Group saved = groupRepository.save(group);

                // Construction manuelle du DTO en sortie
                return GroupDTO.builder()
                                .id(saved.getId())
                                .name(saved.getName())
                                .creatorId(creator.getId())
                                .creatorName(creator.getName())
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
                                                                .toList())
                                                .build())
                                .toList();
        }

        @Override
        public List<Long> getMemberIds(Long groupId) {
                return groupRepository.findById(groupId)
                                .orElseThrow(() -> new IllegalArgumentException("Groupe introuvable"))
                                .getMembers()
                                .stream()
                                .map(Person::getId)
                                .toList();
        }

        @Override
        public List<GroupDTO> getGroupsForUser(Person person) {
                List<Group> groups = groupRepository.findByMembersContaining(person);
                return groups.stream().map(group -> GroupDTO.builder()
                                .id(group.getId())
                                .name(group.getName())
                                .creatorId(group.getCreator().getId())
                                .creatorName(group.getCreator().getName())
                                .memberIds(group.getMembers().stream()
                                                .map(Person::getId)
                                                .toList())
                                .build())
                                .toList();
        }

}
