package com.backend.bank.dto.response.user;

import com.backend.bank.model.UserPrivilege;
import lombok.Data;

@Data
public class UserPrivilegeDTO {
    private int id;
    private int privilegeId;
    private int status;

    public UserPrivilegeDTO() {
    }

    public UserPrivilegeDTO(UserPrivilege userPrivilege) {
        this.id=userPrivilege.getId();
        this.privilegeId=userPrivilege.getPrivilege().getId();
        this.status=userPrivilege.getStatus();
    }

}
