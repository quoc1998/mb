package com.backend.bank.service;

import com.backend.bank.dto.response.team.TeamDTO;
import com.backend.bank.dto.response.team.PaginationTeam;
import com.backend.bank.dto.response.team.TeamReponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeamService {
    List<TeamReponseDTO> getTeams(String local);

    List<TeamDTO> findAll(String local);

    PaginationTeam findAllPagin(String local, Integer page, Integer number);
    PaginationTeam searchTeam(String local, Integer page, Integer number, String search);


    List<TeamReponseDTO> getTeamByUser(String local);
    List<TeamDTO> findAllTeamByUser(String local);
    TeamReponseDTO getTeamById(int id,String local);
    TeamDTO addTeam(TeamDTO teamDTO,String local);
    TeamDTO editTeam(int id, TeamDTO teamDTO,String local);
    void deleteTeam(int id);
    void deleteIds(List<Integer> ids, String local);
}
