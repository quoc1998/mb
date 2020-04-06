package com.backend.bank.service;

import com.backend.bank.dto.response.user.PrivilegeDTO;
import com.backend.bank.dto.response.user.PrivilegeResponse;
import com.backend.bank.model.Privilege;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PrivilegeService {
    Privilege addPrivilege(String name);
    List<Privilege> getAllPrivilege(String local);
    List<PrivilegeResponse> getPrivilegesByRole(int idRole, String local);
    List<PrivilegeDTO> getPrivilegesByGroup(String local);

}
