package com.project.w3t.security.auth;

import com.project.w3t.model.user.Privilege;
import com.project.w3t.model.user.Role;
import com.project.w3t.repository.PrivilegeRepository;
import com.project.w3t.repository.RoleRepository;
import com.project.w3t.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    public static final String MY_CALENDAR_PRIVILEGE = "MY-CALENDAR_PRIVILEGE";
    public static final String MY_REQUESTS_PRIVILEGE = "MY-REQUESTS_PRIVILEGE";
    public static final String MY_EMPLOYEES_REQUESTS_PRIVILEGE = "MY-EMPLOYEES-REQUESTS_PRIVILEGE";
    public static final String MANAGE_USERS_PRIVILEGE = "MANAGE-USERS_PRIVILEGE";
    boolean alreadySetup = false;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    public SetupDataLoader(RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        Privilege myCalendarPrivilege = createPrivilegeIfNotFound(MY_CALENDAR_PRIVILEGE);
        Privilege myRequests = createPrivilegeIfNotFound(MY_REQUESTS_PRIVILEGE);
        Privilege myEmployeesRequestsPrivilege = createPrivilegeIfNotFound(MY_EMPLOYEES_REQUESTS_PRIVILEGE);
        Privilege manageUsersPrivilege = createPrivilegeIfNotFound(MANAGE_USERS_PRIVILEGE);

        Role userRole = createRoleIfNotFound(UserService.ROLE_USER, Arrays.asList(myCalendarPrivilege, myRequests));
        Role managerRole = createRoleIfNotFound(UserService.ROLE_MANAGER, Arrays.asList(myCalendarPrivilege, myRequests, myEmployeesRequestsPrivilege));
        Role adminRole = createRoleIfNotFound(UserService.ROLE_ADMIN, Arrays.asList(manageUsersPrivilege));

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
