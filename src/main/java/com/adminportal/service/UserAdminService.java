package com.adminportal.service;


import com.adminportal.domain.User;
import com.adminportal.domain.security.UserRole;

import java.util.Set;

public interface UserAdminService {

    User createUser(User user, Set<UserRole> userRoleSet) throws Exception;


}
