package com.backend.bank.service;

import com.backend.bank.dto.response.PaginationRole;
import com.backend.bank.dto.response.user.RoleDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleService {
    RoleDTO getRoleById(int roleId,String local);
    List<RoleDTO> getAllRole(String local);

    PaginationRole findAllPage(String local, Integer page, Integer number);
    PaginationRole searchRole(String local, Integer page, Integer number, String search);
    RoleDTO addRole(RoleDTO roleDTO,String local);
    RoleDTO editRole(int id,RoleDTO roleDTO,String local);
    List<RoleDTO> addPrivilegeToRole(int idRole, List<Integer> idPrivileges);
    List<RoleDTO> removePrivilegeToRole(int idRole, List<Integer> idPrivileges);
    List<RoleDTO> addFullPrivilegeToRole(int idRole);
    List<RoleDTO> addGroupPrivilegeToRole(int idRole,String group,String local);
    boolean deleteRole(int id);
    boolean deleteRole(List<Integer> roles);
    List<RoleDTO> addFullTeamToRole(int idRole);
}
