package com.hb.tricount.business;

import com.hb.tricount.dto.CreateGroupDTO;
import com.hb.tricount.dto.GroupDTO;
import com.hb.tricount.entity.Person;

import java.util.List;

public interface GroupService {
    GroupDTO createGroup(CreateGroupDTO dto, Person creator);
    List<GroupDTO> getGroupsByPerson(Long personId);
    List<Long> getMemberIds(Long groupId);
    List<GroupDTO> getGroupsForUser(Person person);

}
