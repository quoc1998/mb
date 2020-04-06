package com.backend.bank.controller;

import com.backend.bank.dto.response.user.PaginationUser;
import com.backend.bank.dto.response.user.UserPrivilegeDTO;
import com.backend.bank.dto.request.Login;
import com.backend.bank.dto.request.UserRequestDto;
import com.backend.bank.dto.response.user.UserResponseDto;
import com.backend.bank.model.User;
import com.backend.bank.model.UserPrivilege;
import com.backend.bank.service.RoleService;
import com.backend.bank.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger( UserController.class );

    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        return userService.login( login );
    }


    @GetMapping("/profile")
    public UserResponseDto getProfile(@RequestParam String username) {
        return userService.getUserInfo( username );
    }

    @Secured({"ROLE_GET USER", "ROLE_XEM USER"})
    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable("id") Integer id) {
        return userService.findById( id );
    }

    @Secured({"ROLE_GET USER", "ROLE_XEM USER"})
    @GetMapping
    public List<UserResponseDto> getAllUser() {
        return userService.getAllUser();
    }

    @Secured({"ROLE_GET USER", "ROLE_XEM USER"})
    @GetMapping("/pagination")
    public PaginationUser findAll(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return userService.findAll( page, number );
    }

    @Secured({"ROLE_GET USER", "ROLE_XEM USER"})
    @GetMapping("/search")
    public PaginationUser searchUser(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                     @RequestParam(name = "number", required = false, defaultValue = "10") Integer number,
                                     @RequestParam String userName) {
        return userService.getUserByNameSearch( page, number, userName );
    }

    @Secured({"ROLE_ADD USER", "ROLE_THÊM USER"})
    @PostMapping("/register")
    public UserResponseDto register(@RequestBody UserRequestDto userRequestDto) {
        return userService.register( userRequestDto );
    }
    @Secured({"ROLE_EDIT USER", "ROLE_SỬA USER"})
    @PutMapping("/{id}")
    public UserResponseDto editUser(@PathVariable("id") int id, @RequestBody UserRequestDto userRequestDto) {
        return userService.editUser( id, userRequestDto );
    }

    @Secured({"ROLE_DELETE USER", "ROLE_DELETE USER"})
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userService.deleteUser( id );
    }

    @GetMapping("privileges/{id}")
    public List<UserPrivilegeDTO> getAllPrivilege(@PathVariable("id") int id) {
        return userService.getAllPrivilegeOdUser( id );
    }

    @Secured({"ROLE_EDIT USER", "ROLE_SỬA USER"})
    @PutMapping("/add_role/{id}")
    public UserResponseDto addRoleToUser(@PathVariable("id") int id, @RequestBody List<Integer> roles) {
        return userService.addRoleToUser( id, roles );
    }

    @Secured({"ROLE_EDIT USER", "ROLE_SỬA USER"})
    @PutMapping("/add_privilege/{id}")
    public UserResponseDto addPrivilegeToUser(@PathVariable("id") int id, @RequestBody List<Integer> privilegeIds) {
        return userService.addPrivilegeToUser( id, privilegeIds );
    }

    @Secured({"ROLE_EDIT USER", "ROLE_SỬA USER"})
    @PutMapping("/remove_privilege/{id}")
    public UserResponseDto removePrivilegeToUser(@PathVariable("id") int id, @RequestBody List<Integer> privilegeIds) {
        return userService.removePrivilegeToUser( id, privilegeIds );
    }

    @Secured({"ROLE_EDIT USER", "ROLE_SỬA USER"})
    @PutMapping("/inherit_privilege/{id}")
    public UserResponseDto inheritPrivilegeToUser(@PathVariable("id") int id, @RequestBody List<Integer> privilegeIds) {
        return userService.inheritPrivilegeToUser( id, privilegeIds );
    }

    @Secured({"ROLE_EDIT USER", "ROLE_SỬA USER"})
    @PutMapping("/add_group_privilege/{id}")
    public UserResponseDto addGroupPrivilegeToUser(@PathVariable("id") int id, @RequestParam(value = "group", required = true) String group) {
        return userService.addGroupPrivilegeToUser( id, group );
    }

    @Secured({"ROLE_EDIT USER", "ROLE_SỬA USER"})
    @PutMapping("/remove_group_privilege/{id}")
    public UserResponseDto removeGroupPrivilegeToUser(@PathVariable("id") int id, @RequestParam(value = "group", required = true) String group) {
        return userService.removeGroupPrivilegeToUser( id, group );
    }

    @Secured({"ROLE_EDIT USER", "ROLE_SỬA USER"})
    @PutMapping("/password/{id}")
    public Boolean editPassword(@PathVariable("id") int id, @RequestParam(value = "password", required = true) String passWord) {
        return userService.editPassWord( id, passWord );
    }

    @Secured({"ROLE_DELETE USER", "ROLE_XÓA USER"})
    @DeleteMapping("/deleteIds")
    public Boolean deleteListUser(@RequestBody List<Integer> ids) {
        return userService.deleteIds( ids );
    }


    @GetMapping("/o")
    List<User> getUser() {
        List<User> users = userService.getUser();
        return users;
    }

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<?> user1(@PathVariable("id") String id) {
        int a = Integer.parseInt( id );
        User usersw = userService.getId( a );
        return ResponseEntity.ok( usersw );
    }

    @PostMapping(value = "/addUser")
    public User add(@RequestBody Map<String, String> body) throws ParseException {
        String id = body.get( "id" );
        int c = Integer.parseInt( id );
        String password = body.get( "password" );
        String status = body.get( "status" );
        int b = Integer.parseInt( status );
        String firstName = body.get( "firstName" );
        String lastName = body.get( "lastName" );
        String department = body.get( "department" );
        String email = body.get( "email" );

        String createdAt = body.get( "createdAt" );
        Date date1 = new SimpleDateFormat( "dd/MM/yyyy" ).parse( createdAt );

        String deletedAt = body.get( "deletedAt" );
        Date date2 = new SimpleDateFormat( "dd/MM/yyyy" ).parse( createdAt );

        String username = body.get( "username" );

        User user = new User();
        user.setId( c );
        user.setPassword( password );
        user.setStatus( b );
        user.setFirstName( firstName );
        user.setLastName( lastName );
        user.setDepartment( department );
        user.setEmail( email );
        user.setCreatedAt( date1 );
        user.setDeletedAt( date2 );
        user.setUsername( username );
        return userService.addUsers(user);

    }

}
