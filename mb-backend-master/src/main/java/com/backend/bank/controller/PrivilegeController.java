package com.backend.bank.controller;

import com.backend.bank.dto.response.user.PrivilegeDTO;
import com.backend.bank.dto.response.user.PrivilegeResponse;
import com.backend.bank.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("{local}/api/privileges")
public class PrivilegeController {
    @Autowired
    private PrivilegeService privilegeService;

    @GetMapping
    public List<PrivilegeDTO> getPrivilegesByGroup(@PathVariable("local") String local) {
        return privilegeService.getPrivilegesByGroup(local);
    }

    @GetMapping("/role/{id}")
    public List<PrivilegeResponse> getPrivilegeByRole(@PathVariable("local") String local, @PathVariable("id") int id) {
        return privilegeService.getPrivilegesByRole(id, local);
    }
}
