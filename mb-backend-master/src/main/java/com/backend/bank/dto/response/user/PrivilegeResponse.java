package com.backend.bank.dto.response.user;

import com.backend.bank.model.Privilege;
import com.backend.bank.model.UserPrivilege;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrivilegeResponse {
    private int id;
    private String name;
    private String local;
    private int privilegeId;
    private String groupRole;
    List<UserPrivilegeDTO> userPrivilegesDtos=new ArrayList<>();

    public PrivilegeResponse() {
    }

    public PrivilegeResponse(Privilege privilege) {
        this.id=privilege.getId();
        this.name=privilege.getName();
        this.local=privilege.getName();
        this.privilegeId=privilege.getPrivilegeId();
        this.groupRole=privilege.getGroupRole();
        if(privilege.getUserPrivileges()!=null) {
            for (UserPrivilege userPrivilege : privilege.getUserPrivileges()) {
                UserPrivilegeDTO userPrivilegeDTO = new UserPrivilegeDTO(userPrivilege);
                this.userPrivilegesDtos.add(userPrivilegeDTO);
            }
        }
    }
}
