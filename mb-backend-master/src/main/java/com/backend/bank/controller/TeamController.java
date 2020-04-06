package com.backend.bank.controller;

import com.backend.bank.dto.response.team.TeamDTO;
import com.backend.bank.dto.response.team.PaginationTeam;
import com.backend.bank.dto.response.team.TeamReponseDTO;
import com.backend.bank.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{local}/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Secured({"ROLE_GET GROUP", "ROLE_XEM NHÓM"})
    @GetMapping
    public List<TeamReponseDTO> getAllTeam(@PathVariable("local") String local) {
        return teamService.getTeams(local);
    }

    @Secured({"ROLE_GET GROUP", "ROLE_XEM NHÓM"})
    @GetMapping("/findall")
    public List<TeamDTO> findAll(@PathVariable("local") String local) {
        return teamService.findAll(local);
    }


    @Secured({"ROLE_GET GROUP", "ROLE_XEM NHÓM"})
    @GetMapping("/pagination")
    public PaginationTeam findAllPage(@PathVariable("local") String local,
                                      @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                      @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return teamService.findAllPagin(local, page, number);
    }

    @Secured({"ROLE_GET GROUP", "ROLE_XEM NHÓM"})
    @GetMapping("/search")
    public PaginationTeam searchTeam(@PathVariable("local") String local,
                                     @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                     @RequestParam(name = "number", required = false, defaultValue = "10") Integer number,
                                     @RequestParam(name = "search") String search) {
        return teamService.searchTeam(local, page, number, search);
    }

    @Secured({"ROLE_GET GROUP", "ROLE_XEM NHÓM"})
    @GetMapping("/{id}")
    public TeamReponseDTO getTeamById(@PathVariable("local") String local, @PathVariable("id") int id) {
        return teamService.getTeamById(id, local);
    }

    @Secured({"ROLE_GET GROUP", "ROLE_XEM NHÓM"})
    @GetMapping("/user")
    public List<TeamReponseDTO> getTeamByUser(@PathVariable("local") String local) {
        return teamService.getTeamByUser(local);
    }

    @Secured({"ROLE_GET GROUP", "ROLE_XEM NHÓM"})
    @GetMapping("/findallbyuser")
    public List<TeamDTO> findAllTeamByUser(@PathVariable("local") String local) {
        return teamService.findAllTeamByUser(local);
    }

    @Secured({"ROLE_ADD GROUP", "ROLE_THÊM NHÓM"})
    @PostMapping
    public TeamDTO addTeam(@PathVariable("local") String local, @RequestBody TeamDTO teamDTO) {
        return teamService.addTeam(teamDTO, local);
    }

    @Secured({"ROLE_EDIT GROUP", "ROLE_SỬA NHÓM"})
    @PutMapping("/{id}")
    public TeamDTO editTeam(@PathVariable("local") String local, @PathVariable("id") int id, @RequestBody TeamDTO teamDTO) {
        return teamService.editTeam(id, teamDTO, local);
    }

    @Secured({"ROLE_DELETE GROUP", "ROLE_XÓA NHÓM"})
    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable("id") int id) {
        teamService.deleteTeam(id);
    }

    @Secured({"ROLE_DELETE GROUP", "ROLE_XÓA NHÓM"})
    @DeleteMapping("/deleteIds")
    public void deleteListTeam(@PathVariable("local") String local, @RequestBody List<Integer> ids) {
        teamService.deleteIds(ids, local);
    }
}
