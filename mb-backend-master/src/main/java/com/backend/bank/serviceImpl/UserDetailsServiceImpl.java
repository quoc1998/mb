package com.backend.bank.serviceImpl;

import com.backend.bank.model.Privilege;
import com.backend.bank.model.Role;
import com.backend.bank.model.User;
import com.backend.bank.model.UserPrivilege;
import com.backend.bank.repository.PrivilegeRepository;
import com.backend.bank.repository.RoleRepository;
import com.backend.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("userDetail")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired

    UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).get();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPrivileges().stream()
                    .map(p -> new SimpleGrantedAuthority(p.getName()))
                    .forEach(grantedAuthorities::add);
        }
        for (UserPrivilege userPrivilege : user.getUserPrivileges()) {
            if (userPrivilege.getStatus() == 1) {
                grantedAuthorities.add(new SimpleGrantedAuthority(userPrivilege.getPrivilege().getName()));
            } else {
                for (GrantedAuthority grantedAuthority : grantedAuthorities) {
                    if (userPrivilege.getPrivilege().getName().equalsIgnoreCase(grantedAuthority.getAuthority())) {
                        grantedAuthorities.remove(grantedAuthority);
                        break;
                    }
                }
            }
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
