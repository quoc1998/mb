package com.backend.bank.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserRequestDto implements Serializable {

    private static final long serialVersionUID = 7156526077883281623L;

    private String username;

    private String password;

	private String passwordConfirm;

	private String firstName;

	private String lastName;

	private String email;

	private int status;

	private String department;

//
//	private List<Integer> roles;
//
//	private List<Integer> userPrivilegeRequests;

	private List<Integer> roles;

	private List<UserPrivilegeRequest> userPrivilegeRequests;
}
