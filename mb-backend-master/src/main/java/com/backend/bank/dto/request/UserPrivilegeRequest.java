package com.backend.bank.dto.request;

import com.backend.bank.model.UserPrivilege;
import lombok.Data;

@Data
public class UserPrivilegeRequest {
    private int privilegeId;
    private int status;

    public UserPrivilegeRequest() {
    }

    public UserPrivilegeRequest(UserPrivilege userPrivilege) {
        this.privilegeId=userPrivilege.getPrivilege().getId();
        this.status=userPrivilege.getStatus();
    }
}
