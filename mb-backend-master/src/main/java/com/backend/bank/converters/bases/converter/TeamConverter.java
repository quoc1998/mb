package com.backend.bank.converters.bases.converter;

import com.backend.bank.dto.response.news.NewsReponseDto;
import com.backend.bank.dto.response.team.TeamDTO;
import com.backend.bank.dto.response.team.TeamReponseDTO;
import com.backend.bank.model.Category;
import com.backend.bank.model.News;
import com.backend.bank.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class TeamConverter {
    @Autowired
    PageConverter pageConverter;

    @Autowired
    CategoryConverter categoryConverter;

    @Autowired
    NewConverter newConverter;

    public TeamReponseDTO converterTeamEntity(Team team, String local) {
        TeamReponseDTO teamReponseDTO = new TeamReponseDTO();
        teamReponseDTO.setId(team.getId());
        teamReponseDTO.setName(team.getName());
        teamReponseDTO.setLocal(team.getLocal());
        teamReponseDTO.setIdTeam(team.getTeamId());
        teamReponseDTO.setCategory(categoryConverter.converterListCategoryEntity(team.getCategories(), local));
        teamReponseDTO.setPages(pageConverter.convertListEntityToDto(team.getPages(), local));
        teamReponseDTO.setNews(converterNewsToTeam(local, team));
        return teamReponseDTO;
    }

    public List<TeamReponseDTO> converterListTeamEntity(List<Team> teams, String local) {
        List<TeamReponseDTO> teamReponseDTOS = new ArrayList<>();
        for (Team team : teams
        ) {
            TeamReponseDTO teamReponseDTO = converterTeamEntity(team, local);
            teamReponseDTOS.add(teamReponseDTO);
        }
        return teamReponseDTOS;
    }

    public HashSet<NewsReponseDto> converterNewsToTeam(String local, Team team) {
        HashSet<NewsReponseDto> news = new HashSet<>();

        for (Category category : team.getCategories()
        ) {
            for (News newsp : category.getNews()
            ) {
                news.add(newConverter.converterDto(local, newsp));
            }
        }
        return news;
    }

    public TeamDTO converterTeamDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setName(team.getName());
        teamDTO.setLocal(team.getLocal());
        teamDTO.setIdTeam(team.getTeamId());
        return teamDTO;
    }

    public List<TeamDTO> converterListTeamDTO(List<Team> teams, String local) {
        List<TeamDTO> teamDTOList = new ArrayList<>();
        for (Team team : teams
        ) {
            if (team.getLocal().equalsIgnoreCase(local)) {
                TeamDTO teamDTO = converterTeamDTO(team);
                teamDTOList.add(teamDTO);
            }

        }
        return teamDTOList;
    }
}