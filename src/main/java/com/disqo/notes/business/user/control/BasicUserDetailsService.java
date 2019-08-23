package com.disqo.notes.business.user.control;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.disqo.notes.business.user.entity.Role;
import com.disqo.notes.business.user.entity.User;
import com.disqo.notes.business.user.entity.UserPrincipal;

@Service
public class BasicUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userHolder = userRepository.findUserWithRolesByEmail(email);
        if (!userHolder.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        User user = userHolder.get();

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            String name = role.getName().name();
            grantedAuthorities.add(new SimpleGrantedAuthority(name));
        }

        return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword(), grantedAuthorities);
    }

}
