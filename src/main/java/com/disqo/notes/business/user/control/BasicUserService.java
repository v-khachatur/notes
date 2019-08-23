package com.disqo.notes.business.user.control;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disqo.notes.business.user.entity.Role;
import com.disqo.notes.business.user.entity.RoleName;
import com.disqo.notes.business.user.entity.User;

@Service
public class BasicUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(User user, Set<RoleName> roleNames) {
        Objects.requireNonNull(user, "user must not be null");
        Objects.requireNonNull(roleNames, "Role names must not be null");

        user.getRoles().addAll(roleNames.stream().map(rn -> {
            Role role = new Role();
            role.setName(rn);
            role.setUser(user);
            return role;
        }).collect(Collectors.toSet()));

        return userRepository.save(user);
    }

}
