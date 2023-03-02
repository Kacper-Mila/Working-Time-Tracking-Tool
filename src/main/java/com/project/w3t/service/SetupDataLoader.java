package com.project.w3t.service;

import com.project.w3t.model.user.Privilege;
import com.project.w3t.model.user.Role;
import com.project.w3t.model.user.RoleName;
import com.project.w3t.repository.PrivilegeRepository;
import com.project.w3t.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

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
        Privilege myCalendarPrivilege = createPrivilegeIfNotFound("MY-CALENDAR_PRIVILEGE");
        Privilege myRequests = createPrivilegeIfNotFound("MY-REQUESTS_PRIVILEGE");
        Privilege myEmployeesRequestsPrivilege = createPrivilegeIfNotFound("MY-EMPLOYEES-REQUESTS_PRIVILEGE");
        Privilege manageUsersPrivilege = createPrivilegeIfNotFound("MANAGE-USERS_PRIVILEGE");

        Role userRole = createRoleifNotFound(RoleName.ROLE_USER, Arrays.asList(myCalendarPrivilege, myRequests));
        Role managerRole = createRoleifNotFound(RoleName.ROLE_MANAGER, Arrays.asList(myCalendarPrivilege, myRequests, myEmployeesRequestsPrivilege));
        Role adminRole = createRoleifNotFound(RoleName.ROLE_ADMIN, Arrays.asList(manageUsersPrivilege));

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
    Role createRoleifNotFound(RoleName roleName, Collection<Privilege> privileges) {
        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            role = new Role();
            role.setRoleName(roleName);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
