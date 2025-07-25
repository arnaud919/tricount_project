package com.hb.tricount.business;

import com.hb.tricount.dto.CreateGroupDTO;
import com.hb.tricount.dto.GroupDTO;

import java.util.List;

public interface GroupService {
    GroupDTO createGroup(CreateGroupDTO dto);
    List<GroupDTO> getGroupsByPerson(Long personId);
    List<Long> getMemberIds(Long groupId);
}
