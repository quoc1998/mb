package com.backend.bank.dto.response.team;

import com.backend.bank.model.Team;
import lombok.Data;

@Data
public class TeamDTO {
    private int id;
    private String name;
    private String local;
    private int idTeam;


    public TeamDTO() {
    }

    public TeamDTO(Team team) {
        this.id=team.getId();
        this.name=team.getName();
        this.local=team.getLocal();
        this.idTeam=team.getTeamId();
    }
}
