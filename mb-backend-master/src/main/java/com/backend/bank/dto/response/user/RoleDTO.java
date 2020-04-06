package com.backend.bank.dto.response.user;

import com.backend.bank.model.Privilege;
import com.backend.bank.model.Role;
import com.backend.bank.model.Team;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleDTO {
    private int id;
    private String name;
    private String local;
    private int idRole;
    private List<Integer> privileges;
    private List<Integer> teams=new ArrayList<>();

    public RoleDTO() {
    }

    public RoleDTO(String name) {
        this.name = name;
    }

    public RoleDTO(Role role) {
        this.id=role.getId();
        this.name=role.getName();
        this.local=role.getLocal();
        this.idRole=role.getRoleId();
        this.privileges=new ArrayList<>();
        List<Privilege> privileges=role.getPrivileges();
        if(privileges!=null){
            for(Privilege privilege:privileges){
                this.privileges.add(privilege.getPrivilegeId());
            }
        }
        this.teams=new ArrayList<>();
        List<Team> teams=role.getTeams();
        if(teams!=null){
            for(Team team:teams){
                this.teams.add(team.getTeamId());
            }
        }

    }

}

