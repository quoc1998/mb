package com.backend.bank.dto.response.user;

import com.backend.bank.dto.request.UserPrivilegeRequest;
import com.backend.bank.model.Role;
import com.backend.bank.model.User;
import com.backend.bank.model.UserPrivilege;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserResponseDto implements Serializable {

    
	private static final long serialVersionUID = 7156526077883281623L;
    private Integer id;

    private String username;
    private String firstName;

    private String lastName;

    private String department;

    private String email;

    private int status;

    private List<Integer> roles=new ArrayList<>();

//    private List<Integer> userPrivilegeRequests=new ArrayList<>();

    private List<UserPrivilegeRequest> userPrivilegeRequests=new ArrayList<>();


    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.email=user.getEmail();
        this.status=user.getStatus();
        this.department=user.getDepartment();
        for(Role role:user.getRoles()){
            this.getRoles().add(role.getRoleId());
        }
        this.roles=this.getRoles().stream().distinct().collect(Collectors.toList());

        for(UserPrivilege userPrivilege:user.getUserPrivileges()){
            UserPrivilegeRequest userPrivilegeRequest=new UserPrivilegeRequest(userPrivilege);
            this.userPrivilegeRequests.add(userPrivilegeRequest);
        }

    }

    public UserResponseDto(Integer id, String username, String nickname) {
        this.id = id;
        this.username = username;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
