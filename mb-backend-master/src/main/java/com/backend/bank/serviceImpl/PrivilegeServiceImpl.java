package com.backend.bank.serviceImpl;

import com.backend.bank.common.Constants;
import com.backend.bank.dto.response.user.PrivilegeDTO;
import com.backend.bank.dto.response.user.PrivilegeResponse;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.Privilege;
import com.backend.bank.model.Role;
import com.backend.bank.repository.PrivilegeRepository;
import com.backend.bank.repository.RoleRepository;
import com.backend.bank.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Privilege addPrivilege(String name) {
        Optional<Privilege> privilege1 = privilegeRepository.findByName(name);
        if (privilege1.isPresent()) {
            return null;
        }
        Privilege privilege = new Privilege();
        privilege.setName(name);
        privilegeRepository.save(privilege);
        return privilege;
    }

    @Override
    public List<Privilege> getAllPrivilege(String local) {
        List<Privilege> privileges = privilegeRepository.findByLocal(local);
        return privileges;
    }

    @Override
    public List<PrivilegeResponse> getPrivilegesByRole(int idRole, String local) {
        Constants.checkLocal(local);
        Optional<Role> role = roleRepository.findByRoleIdAndLocal(idRole, local);
        if (!role.isPresent()) {
            throw new NotFoundException("Role not found!");
        }
        List<Privilege> privileges = role.get().getPrivileges();
        List<PrivilegeResponse> privilegeDTOS = new ArrayList<>();
        for (Privilege privilege : privileges) {
            PrivilegeResponse privilegeDTO = new PrivilegeResponse(privilege);
            privilegeDTOS.add(privilegeDTO);
        }
        return privilegeDTOS;
    }

    @Override
    public List<PrivilegeDTO> getPrivilegesByGroup(String local) {
        List<Privilege> privileges = privilegeRepository.findByLocal(local);
        List<PrivilegeDTO> privilegeList1 = new ArrayList<>();
        HashSet<String> hashSet = new HashSet<>();
        for (Privilege privilege : privileges) {
            hashSet.add(privilege.getGroupRole());
        }
        for (String hs : hashSet) {
            PrivilegeDTO privilegeDTO = new PrivilegeDTO();
            List<Privilege> list = privilegeRepository.findByLocalAndGroupRole(local, hs);
            List<PrivilegeResponse> privilegeResponses = new ArrayList<>();
            for (Privilege privilege : list) {
                privilegeResponses.add(new PrivilegeResponse(privilege));
            }
            privilegeDTO.setGroupRole(hs);
            privilegeDTO.setPrivileges(privilegeResponses);
            privilegeList1.add(privilegeDTO);
        }

        return privilegeList1;
    }

}
