package com.disqo.notes.business.user.control;

import java.util.Set;

import com.disqo.notes.business.user.entity.RoleName;
import com.disqo.notes.business.user.entity.User;

public interface UserService {

    User createUser(User user, Set<RoleName> roleNames);

}
