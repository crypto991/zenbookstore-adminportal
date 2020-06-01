package com.adminportal.service.impl;

import com.adminportal.domain.User;
import com.adminportal.domain.security.UserRole;
import com.adminportal.repository.RoleRepository;
import com.adminportal.repository.UserRepository;
import com.adminportal.service.UserAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserAdminServiceImpl implements UserAdminService {


    public static final Logger LOG = LoggerFactory.getLogger(UserAdminService.class);

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserAdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User createUser(User user, Set<UserRole> userRoleSet) throws Exception {
        User localUser = userRepository.findByUsername(user.getUsername());

        if(localUser != null) {
            LOG.info("user {} already exists", user.getUsername());
        }else {
            for (UserRole ur: userRoleSet) {
                roleRepository.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoleSet);

            localUser = userRepository.save(user);
        }


        return localUser;
    }
}
