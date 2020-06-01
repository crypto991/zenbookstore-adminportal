package com.adminportal;


import com.adminportal.domain.User;
import com.adminportal.domain.security.Role;
import com.adminportal.domain.security.UserRole;
import com.adminportal.service.UserAdminService;
import com.adminportal.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AdminportalApplication implements CommandLineRunner {

    @Autowired
    private UserAdminService userAdminService;

    public static void main(String[] args) {
        SpringApplication.run(AdminportalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
        admin.setFirstName("John");
        admin.setLastName("Winkins");
        admin.setUsername("admin");
        admin.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
        admin.setEmail("admin@gmail.com");
        Set<UserRole> userRoleSet = new HashSet<>();
        Role role = new Role();
        role.setId(0);
        role.setName("ROLE_ADMIN");

        userRoleSet.add(new UserRole(admin, role));

        userAdminService.createUser(admin, userRoleSet);
    }


}
