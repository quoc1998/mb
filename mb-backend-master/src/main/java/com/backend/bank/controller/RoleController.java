package com.backend.bank.controller;


import com.backend.bank.dto.response.PaginationRole;
import com.backend.bank.dto.response.user.RoleDTO;
import com.backend.bank.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{local}/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Secured({"ROLE_GET ROLE", "ROLE_XEM VAI TRÒ"})
    @GetMapping
    public List<RoleDTO> getAllRole(@PathVariable("local") String local) {
        return roleService.getAllRole(local);
    }

    @Secured({"ROLE_GET ROLE", "ROLE_XEM VAI TRÒ"})
    @GetMapping("/pagination")
    public PaginationRole findAllPage(@PathVariable("local") String local,
                                      @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                      @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return roleService.findAllPage(local, page, number);
    }

    @Secured({"ROLE_GET ROLE", "ROLE_XEM VAI TRÒ"})
    @GetMapping("/search")
    public PaginationRole searchRole(@PathVariable("local") String local,
                                     @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                     @RequestParam(name = "number", required = false, defaultValue = "10") Integer number,
                                     @RequestParam(name = "search") String search) {
        return roleService.searchRole(local, page, number, search);
    }

    @Secured({"ROLE_GET ROLE", "ROLE_XEM VAI TRÒ"})
    @GetMapping("/{id}")
    public RoleDTO getAllRole(@PathVariable("local") String local, @PathVariable("id") int id) {
        return roleService.getRoleById(id, local);
    }

    @Secured({"ROLE_ADD ROLE", "ROLE_THÊM VAI TRÒ"})
    @PostMapping
    public RoleDTO addRole(@PathVariable("local") String local, @RequestBody RoleDTO roleDTO) {
        return roleService.addRole(roleDTO, local);
    }

    @Secured({"ROLE_EDIT ROLE", "ROLE_SỬA VAI TRÒ"})
    @PutMapping("/{id}")
    public RoleDTO editRole(@PathVariable("local") String local, @PathVariable("id") int id, @RequestBody RoleDTO roleDTO) {
        return roleService.editRole(id, roleDTO, local);
    }

    @Secured({"ROLE_EDIT ROLE", "ROLE_SỬA VAI TRÒ"})
    @PutMapping("accept_privilege/{idRole}")
    public List<RoleDTO> acceptPrivilege(@PathVariable("local") String local, @PathVariable("idRole") int idRole, @RequestBody List<Integer> privileges) {
        return roleService.addPrivilegeToRole(idRole, privileges);
    }

    @Secured({"ROLE_EDIT ROLE", "ROLE_SỬA VAI TRÒ"})
    @PutMapping("remove_privilege/{idRole}")
    public List<RoleDTO> removePrivilege(@PathVariable("local") String local, @PathVariable("idRole") int idRole, @RequestBody List<Integer> privileges) {
        return roleService.removePrivilegeToRole(idRole, privileges);
    }

    @PutMapping("/add_full_privilege/{id}")
    public List<RoleDTO> addFullPrivilegeToROle(@PathVariable("id") int id, @PathVariable("local") String local) {
        return roleService.addFullPrivilegeToRole(id);
    }

    @PutMapping("/add_full_team/{id}")
    public List<RoleDTO> addFullTeamToROle(@PathVariable("id") int id, @PathVariable("local") String local) {
        return roleService.addFullTeamToRole(id);
    }

    @Secured({"ROLE_DELETE ROLE", "ROLE_XÓA VAI TRÒ"})
    @DeleteMapping("/{id}")
    public boolean deleteRole(@PathVariable("id") int id) {
        return roleService.deleteRole(id);
    }

    @Secured({"ROLE_DELETE ROLE", "ROLE_XÓA VAI TRÒ"})
    @DeleteMapping
    public boolean deleteListRole(@RequestBody List<Integer> roles) {
        return roleService.deleteRole(roles);
    }
}
