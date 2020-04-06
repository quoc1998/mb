package com.backend.bank.serviceImpl;

import com.backend.bank.converters.bases.converter.TeamConverter;
import com.backend.bank.dto.response.team.TeamDTO;
import com.backend.bank.dto.response.team.PaginationTeam;
import com.backend.bank.dto.response.team.TeamReponseDTO;
import com.backend.bank.exception.BadRequestException;
import com.backend.bank.exception.InternalServerError;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.Role;
import com.backend.bank.model.Team;
import com.backend.bank.model.User;
import com.backend.bank.repository.TeamRepository;
import com.backend.bank.repository.UserRepository;
import com.backend.bank.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamConverter teamConverter;

    @Override
    public List<TeamReponseDTO> getTeams(String local) {
        List<Team> teams = teamRepository.findAllByLocalAndDeletedAt(local, null);
        List<TeamReponseDTO> teamDTOS = new ArrayList<>();
        for (Team team : teams) {
            TeamReponseDTO teamDTO = teamConverter.converterTeamEntity(team, local);
            ;
            teamDTOS.add(teamDTO);
        }
        return teamDTOS;
    }

    @Override
    public List<TeamDTO> findAll(String local) {
        List<Integer> listId = teamRepository.findAllId();
        List<Team> teams = new ArrayList<>();
        List<Team> teamsByTeamId;
        Team team;
        for (Integer id : listId
        ) {
            teamsByTeamId = teamRepository.findAllByTeamIdAndDeletedAt(id, null);
            if (teamsByTeamId.size() > 0) {
                team = checkTeamByLocal(teamsByTeamId, local);
                if (team == null) {
                    team = new Team();
                    StringBuilder stringBuilder = new StringBuilder(teamsByTeamId.get(0).getName());
                    stringBuilder.append(" ");
                    stringBuilder.append(local);
                    team.setName(stringBuilder.toString());
                    team.setLocal(local);
                    team.setCreatedAt(new Date());
                    team.setTeamId(id);
                    teamRepository.save(team);
                }
                teams.add(team);
            }

        }
        List<TeamDTO> teamDTOList = teamConverter.converterListTeamDTO(teams, local);
        return teamDTOList;
    }

    @Override
    public PaginationTeam findAllPagin(String local, Integer page, Integer number) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<Team> pageAll = teamRepository.getAllBy(pageable);
        List<TeamReponseDTO> pagesResponseDtos = teamConverter.converterListTeamEntity(pageAll.getContent(), local);
        PaginationTeam paginationPage = new PaginationTeam();
        paginationPage.setTeams(pagesResponseDtos);
        paginationPage.setSize(pageAll.getTotalPages());
        return paginationPage;
    }

    @Override
    public PaginationTeam searchTeam(String local, Integer page, Integer number, String search) {
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<Team> pageAll = teamRepository.getBlocksSearch(pageable, local, search);
        List<TeamReponseDTO> pagesResponseDtos = teamConverter.converterListTeamEntity(pageAll.getContent(), local);
        PaginationTeam paginationPage = new PaginationTeam();
        paginationPage.setTeams(pagesResponseDtos);
        paginationPage.setSize(pageAll.getTotalPages());
        return paginationPage;
    }

    @Override
    public List<TeamReponseDTO> getTeamByUser(String local) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUserName(username);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found!");
        }
        List<TeamReponseDTO> teamDTOS = new ArrayList<>();
        for (Role role : user.get().getRoles()) {
            if (role.getLocal().equals(local)) {
                for (Team team : role.getTeams()) {
                    TeamReponseDTO teamDTO = getTeamById(team.getTeamId(), local);
                    teamDTOS.add(teamDTO);
                }
            }
        }
        return teamDTOS;
    }

    @Override
    public List<TeamDTO> findAllTeamByUser(String local) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Team> teamList = teamRepository.findAllByUsername(username, local);
        return teamConverter.converterListTeamDTO(teamList, local);
    }

    @Override
    public TeamReponseDTO getTeamById(int id, String local) {
        List<Team> teams = teamRepository.findAllByTeamId(id);
        if (teams == null) {
            throw new NotFoundException("Team not found!");
        }

        Optional<Team> team = teamRepository.findByTeamIdAndLocalAndDeletedAt(id, local, null);
        if (team.isPresent()) {
            return teamConverter.converterTeamEntity(team.get(), local);
        } else {
            Team teamNew = new Team();
            teamNew.setName(teams.get(0).getName());
            teamNew.setTeamId(teams.get(0).getTeamId());
            teamNew.setLocal(local);
            teamRepository.save(teamNew);
            return teamConverter.converterTeamEntity(team.get(), local);
        }
    }

    @Override
    public TeamDTO addTeam(TeamDTO teamDTO, String local) {
        Optional<Team> team = teamRepository.findByName(teamDTO.getName());
        if (team.isPresent()) {
            throw new BadRequestException("Team is existed!");
        }
        Team teamNew = new Team();
        teamNew.setLocal(local);
        teamNew.setName(teamDTO.getName());
        teamNew.setCreatedAt(new Date());
        teamRepository.save(teamNew);
        teamNew.setTeamId(teamNew.getId());
        teamRepository.save(teamNew);
        return new TeamDTO(teamNew);
    }

    @Override
    public TeamDTO editTeam(int id, TeamDTO teamDTO, String local) {
        Optional<Team> team = teamRepository.findByTeamIdAndLocalAndDeletedAt(id, local, null);
        if (teamRepository.findByName(teamDTO.getName()).isPresent()) {
            throw new BadRequestException("Team is existed");
        }
        if (team.isPresent()) {
            team.get().setName(teamDTO.getName());
            teamRepository.save(team.get());
            teamDTO = new TeamDTO(team.get());
        } else {
            Team teamNew = new Team();
            teamNew.setLocal(local);
            teamNew.setName(teamDTO.getName());
            teamNew.setTeamId(id);
            teamRepository.save(teamNew);
            teamDTO = new TeamDTO(teamNew);
        }
        return teamDTO;
    }

    @Override
    public void deleteTeam(int id) {
        List<Team> teams = teamRepository.findAllByTeamId(id);
        if (teams.size() == 0) {
            throw new NotFoundException("Team not found!");
        }
        for (Team team : teams
        ) {
            team.setDeletedAt(new Date());
            teamRepository.save(team);
        }

    }

    @Override
    public void deleteIds(List<Integer> ids, String local) {
        try {
            for (Integer id : ids) {
                deleteTeam(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError(e.getMessage());
        }

    }

    public Team checkTeamByLocal(List<Team> teams, String local) {
        try {
            for (Team team : teams
            ) {
                if (team.getLocal().equalsIgnoreCase(local)) {
                    return team;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
