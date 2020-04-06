package com.backend.bank.serviceImpl;

import com.backend.bank.common.Constants;
import com.backend.bank.dto.response.PaginationRole;
import com.backend.bank.dto.response.user.RoleDTO;
import com.backend.bank.exception.BadRequestException;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.Privilege;
import com.backend.bank.model.Role;
import com.backend.bank.model.Team;
import com.backend.bank.model.User;
import com.backend.bank.repository.PrivilegeRepository;
import com.backend.bank.repository.RoleRepository;
import com.backend.bank.repository.TeamRepository;
import com.backend.bank.repository.UserRepository;
import com.backend.bank.service.RoleService;
import com.backend.bank.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public RoleDTO getRoleById(int roleId, String local) {
        List<Role> roles = roleRepository.findByRoleId(roleId);
        if (roles == null) {
            throw new NotFoundException("Role not found!");
        }
        Optional<Role> role = roleRepository.findByRoleIdAndLocal(roleId, local);
        if (role.isPresent()) {
            return new RoleDTO(role.get());
        } else {
            Role roleNew = new Role();
            List<Role> roleList = roleRepository.findByRoleId(roleId);
            roleNew.setName(roles.get(0).getName());
            roleNew.setRoleId(roles.get(0).getRoleId());
            roleNew.setLocal(local);
            List<Team> teams1 = createListTeam(roleList.get(0).getTeams());
            roleNew.setTeams(teams1);
            roleRepository.save(roleNew);
            return new RoleDTO(roleNew);
        }
    }

    @Override
    public List<RoleDTO> getAllRole(String local) {
        Constants.checkLocal(local);
        List<Role> roles = roleRepository.findAll();
        List<Integer> roleIds = new ArrayList<>();
        roles.stream().map(p -> p.getRoleId()).forEach(roleIds::add);
        roleIds = roleIds.stream().distinct().collect(Collectors.toList());
        List<RoleDTO> roleDTOS = new ArrayList<>();
        for (Integer roleId : roleIds) {
            RoleDTO roleDTO = getRoleById(roleId, local);
            roleDTOS.add(roleDTO);
        }
        return this.converEntity(roles, local);
    }

    private List<RoleDTO> converEntity(List<Role> roles, String local) {
        List<Integer> roleIds = new ArrayList<>();
        roles.stream().map(p -> p.getRoleId()).forEach(roleIds::add);
        roleIds = roleIds.stream().distinct().collect(Collectors.toList());
        List<RoleDTO> roleDTOS = new ArrayList<>();
        for (Integer roleId : roleIds) {
            RoleDTO roleDTO = getRoleById(roleId, local);
            roleDTOS.add(roleDTO);
        }
        return roleDTOS;
    }

    @Override
    public PaginationRole findAllPage(String local, Integer page, Integer number) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<Role> pageAll = roleRepository.getAllBy(pageable);
        List<RoleDTO> userResponseDtos = converEntity(pageAll.getContent(), local);
        PaginationRole paginationRole = new PaginationRole();
        paginationRole.setRoles(userResponseDtos);
        paginationRole.setSize(pageAll.getTotalPages());
        return paginationRole;
    }

    @Override
    public PaginationRole searchRole(String local, Integer page, Integer number, String search) {
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<Role> pageAll = roleRepository.getBlocksSearch(pageable, local, search);
        List<RoleDTO> userResponseDtos = converEntity(pageAll.getContent(), local);
        PaginationRole paginationRole = new PaginationRole();
        paginationRole.setRoles(userResponseDtos);
        paginationRole.setSize(pageAll.getTotalPages());
        return paginationRole;
    }

    @Override
    public RoleDTO addRole(RoleDTO roleDTO, String local) {
        Constants.checkLocal(local);
        Optional<Role> role = roleRepository.findByName(roleDTO.getName());
        if (role.isPresent()) {
            throw new BadRequestException("Role is existed");
        }
        Role roleNew = new Role();
        roleNew.setName(roleDTO.getName());
        roleNew.setLocal(local);
        roleNew.setCreatedAt(new Date());
        roleRepository.save(roleNew);
        roleNew.setRoleId(roleNew.getId());
        roleRepository.save(roleNew);
        List<Privilege> privileges = new ArrayList<>();
        roleNew.setPrivileges(privileges);
        for (Integer privilegeId : roleDTO.getPrivileges()) {
            Privilege privilege = privilegeRepository.findByPrivilegeIdAndLocal(privilegeId, local).get();
            privileges.add(privilege);
        }
        if (privileges.size() != 0) {
            addListPrivilege(roleNew, privileges);
        }

        List<Team> teams = new ArrayList<>();
        for (Integer teamId : roleDTO.getTeams()) {
            if (teamRepository.findByTeamIdAndLocalAndDeletedAt(teamId, local, null).isPresent()) {
                teams.add(teamRepository.findByTeamIdAndLocalAndDeletedAt(teamId, local, null).get());
            }
        }
        roleNew.getTeams().addAll(teams);
        roleRepository.save(roleNew);
        roleDTO.setId(roleNew.getId());
        return roleDTO;
    }

    @Override
    public RoleDTO editRole(int id, RoleDTO roleDTO, String local) {
        Constants.checkLocal(local);
        List<Role> roles = roleRepository.findByRoleId(roleDTO.getIdRole());
        if (roles == null) {
            throw new NotFoundException("Role not found");
        }

        List<Privilege> privileges = new ArrayList<>();
        for (Integer privilegeId : roleDTO.getPrivileges()) {
            Optional<Privilege> privilege = privilegeRepository.findByPrivilegeIdAndLocal(privilegeId, local);
            if (privilege.isPresent()) {
                privileges.add(privilege.get());
            }
        }
        List<Team> teams = new ArrayList<>();
        for (Integer teamId : roleDTO.getTeams()) {
            Optional<Team> team = teamRepository.findByTeamIdAndLocalAndDeletedAt(teamId, local, null);
            if (team.isPresent()) {
                teams.add(team.get());
            }
        }

        Optional<Role> role = roleRepository.findByRoleIdAndLocal(roleDTO.getIdRole(), local);
        if (role.isPresent()) {
            role.get().getPrivileges().removeAll(role.get().getPrivileges());
            role.get().getTeams().removeAll(role.get().getTeams());
            role.get().setPrivileges(privileges);
            role.get().setTeams(teams);
            role.get().setName(roleDTO.getName());
            roleRepository.save(role.get());
            return new RoleDTO(role.get());
        } else {
            Role roleNew = new Role();
            roleNew.setName(roleDTO.getName());
            roleNew.setTeams(teams);
            roleNew.setPrivileges(privileges);
            roleNew.setLocal(local);
            roleNew.setRoleId(roles.get(0).getRoleId());
            roleRepository.save(roleNew);
            role.get().getPrivileges().removeAll(role.get().getPrivileges());
            role.get().getTeams().removeAll(role.get().getTeams());

            return new RoleDTO(roleNew);
        }
    }

    @Override
    public List<RoleDTO> addPrivilegeToRole(int idRole, List<Integer> Privileges) {
        List<Role> roles = roleRepository.findByRoleId(idRole);
        for (Role role : roles) {
            role.getPrivileges().removeAll(role.getPrivileges());
        }
        for (Integer privilege : Privileges) {
            addPrivilegeToRole(idRole, privilege);
        }
        return convertRole(idRole);
    }

    public List<RoleDTO> addPrivilegeToRole(int idRole, int idPrivilege) {
        List<Role> roles = roleRepository.findByRoleId(idRole);
        for (Role role : roles) {
            if (role.getLocal().equals(Constants.EN) && checkPrivilegeExist(idPrivilege, Constants.EN)) {
                role.getPrivileges().add(privilegeRepository.findByPrivilegeIdAndLocal(idPrivilege, Constants.EN).get());
            }
            if (role.getLocal().equals(Constants.VI) && checkPrivilegeExist(idPrivilege, Constants.VI)) {
                role.getPrivileges().add(privilegeRepository.findByPrivilegeIdAndLocal(idPrivilege, Constants.VI).get());
            }
            roleRepository.save(role);
        }

        return convertRole(idRole);
    }

    @Override
    public List<RoleDTO> removePrivilegeToRole(int idRole, List<Integer> Privileges) {
        List<Role> roles = roleRepository.findByRoleId(idRole);
        for (Role role : roles) {
            RoleDTO roleDTO = new RoleDTO(role);
        }
        for (Integer privilege : Privileges) {
            removePrivilegeToRole(idRole, privilege);
        }
        return convertRole(idRole);
    }

    public List<RoleDTO> removePrivilegeToRole(int idRole, int idPrivilege) {
        List<Role> roles = roleRepository.findByRoleId(idRole);
        for (Role role : roles) {
            if (role.getLocal().equals(Constants.EN) && checkPrivilegeExist(idPrivilege, Constants.EN)) {
                role.getPrivileges().remove(privilegeRepository.findByPrivilegeIdAndLocal(idPrivilege, Constants.EN).get());
            }
            if (role.getLocal().equals(Constants.VI) && checkPrivilegeExist(idPrivilege, Constants.VI)) {
                role.getPrivileges().remove(privilegeRepository.findByPrivilegeIdAndLocal(idPrivilege, Constants.VI).get());
            }
            roleRepository.save(role);
        }

        return convertRole(idRole);
    }

    public boolean checkPrivilegeExist(int id, String local) {
        if (!privilegeRepository.findByPrivilegeIdAndLocal(id, local).isPresent()) {
            throw new NotFoundException("Privilege not found!");
        }
        return true;
    }

    @Override
    public List<RoleDTO> addFullPrivilegeToRole(int idRole) {
        List<Role> roles = roleRepository.findByRoleId(idRole);
        for (Role role : roles) {
            role.getPrivileges().removeAll(role.getPrivileges());
            if (role.getLocal().equals(Constants.VI)) {
                List<Privilege> privileges = privilegeRepository.findByLocal(Constants.VI);
                role.getPrivileges().addAll(privileges);
            } else {
                List<Privilege> privileges = privilegeRepository.findByLocal(Constants.EN);
                role.getPrivileges().addAll(privileges);
            }
            roleRepository.save(role);
        }

        return convertRole(idRole);
    }

    @Override
    public List<RoleDTO> addGroupPrivilegeToRole(int idRole, String group, String local) {
        List<Privilege> privileges = privilegeRepository.findByGroupRole(group);
        Optional<Role> role = roleRepository.findByRoleIdAndLocal(idRole, local);
        if (!role.isPresent()) {
            throw new NotFoundException("Role not found!");
        }
        addListPrivilege(role.get(), privileges);
        roleRepository.save(role.get());
        Privilege privilege = new Privilege();
        Role roleTranslation = new Role();
        if (local.equals(Constants.VI)) {
            privilege = privilegeRepository.findByPrivilegeIdAndLocal(privileges.get(0).getPrivilegeId(), Constants.EN).get();
            roleTranslation = roleRepository.findByRoleIdAndLocal(idRole, Constants.EN).get();
        } else {
            privilege = privilegeRepository.findByPrivilegeIdAndLocal(privileges.get(0).getPrivilegeId(), Constants.VI).get();
            roleTranslation = roleRepository.findByRoleIdAndLocal(idRole, Constants.VI).get();
        }
        String groupTranslation = privilege.getGroupRole();
        List<Privilege> privilegesTranslation = privilegeRepository.findByGroupRole(groupTranslation);
        addListPrivilege(roleTranslation, privilegesTranslation);
        roleRepository.save(roleTranslation);
        return convertRole(idRole);
    }

    public void addListPrivilege(Role role, List<Privilege> privileges) {
        for (Privilege privilege : privileges) {
            if (!role.getPrivileges().contains(privilege)) {
                role.getPrivileges().add(privilege);
            }
        }
        roleRepository.save(role);
    }

    public List<RoleDTO> convertRole(int idRole) {
        List<Role> roles = roleRepository.findByRoleId(idRole);
        List<RoleDTO> roleDTOS = new ArrayList<>();
        for (Role role : roles) {
            RoleDTO roleDTO = new RoleDTO(role);
            roleDTOS.add(roleDTO);
        }
        return roleDTOS;
    }

    @Override
    public List<RoleDTO> addFullTeamToRole(int idRole) {
        List<Role> roles = roleRepository.findByRoleId(idRole);
        for (Role role : roles) {
            role.getTeams().removeAll(role.getTeams());
            if (role.getLocal().equals(Constants.VI)) {
                List<Team> teams = teamRepository.findByLocal(Constants.VI);
                role.getTeams().addAll(teams);
            } else {
                List<Team> teams = teamRepository.findByLocal(Constants.EN);
                role.getTeams().addAll(teams);
            }
            roleRepository.save(role);
        }

        return convertRole(idRole);
    }

    @Override
    public boolean deleteRole(int id) {
        List<Role> roles = roleRepository.findByRoleId(id);
        if (roles == null) {
            throw new NotFoundException("Role not found!");
        }
        List<Integer> users = new ArrayList<>();
        for (Role role : roles) {
            users.addAll(roleRepository.deleteUserOfRole(role.getId()));
        }
        users.stream().distinct();
        for (Integer userId : users) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                user.get().getRoles().removeAll(roles);
                userRepository.save(user.get());
            }
        }
        roleRepository.deleteInBatch(roles);
        return true;
    }

    @Override
    public boolean deleteRole(List<Integer> roles) {
        for (Integer roleId : roles) {
            deleteRole(roleId);
        }
        return true;
    }

    public List<Team> getTeamByRole(int roleId, String local) {
        List<Role> roles = roleRepository.findByRoleId(roleId);
        if (roles == null) {
            throw new NotFoundException("Role not found!");
        }
//        List<Team> teams=roles.get(0).getTeams();
//        List<Team> teamResult=new ArrayList<>();
//        for(Team team:teams){
//            Optional<Team> teamCompare=teamRepository.findByTeamIdAndLocal(team.getTeamId(),local);
//            if(teamCompare.isPresent()){
//                teamResult.add(team);
//            }else {
//                Team teamNew=new Team();
//                teamNew.setLocal(local);
//                teamNew.setTeamId(team.getTeamId());
//                teamNew.setName(team.getName());
//                teamRepository.save(team);
//                teamResult.add(team);
//            }
//        }
        return null;
    }

    public List<Team> createListTeam(List<Team> teamList) {
        List<Team> teams = new ArrayList<>();
        for (Team team : teamList
        ) {
            Team team1 = new Team();
            team1 = team;
            teams.add(team1);
        }
        return teams;
    }
}
