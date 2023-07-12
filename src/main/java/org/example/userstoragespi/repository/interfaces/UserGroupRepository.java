package org.example.userstoragespi.repository.interfaces;

import org.example.userstoragespi.domain.User;
import org.example.userstoragespi.domain.UserGroup;

import java.util.List;

public interface UserGroupRepository {
    List<UserGroup> getAllUserGroups();
}
