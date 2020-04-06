package com.backend.bank.service;

import com.backend.bank.dto.response.user.UserPrivilegeDTO;
import com.backend.bank.dto.request.Login;
import com.backend.bank.dto.request.UserRequestDto;
import com.backend.bank.dto.response.user.PaginationUser;
import com.backend.bank.dto.response.user.UserResponseDto;
import com.backend.bank.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    List<UserResponseDto> getAllUser();
    PaginationUser findAll(Integer page, Integer number);
    PaginationUser getUserByNameSearch(Integer page, Integer number,  String userName);
    UserResponseDto getUserInfo(String username);
    UserResponseDto findById(Integer id);
    UserResponseDto register(UserRequestDto userRequestDto);
    UserResponseDto editUser(int id,UserRequestDto userRequestDto);
    UserResponseDto addRoleToUser(int id, List<Integer> roles);
    ResponseEntity<?> login(Login login);
    void deleteUser(int id);

    List<UserPrivilegeDTO> getAllPrivilegeOdUser(int user);

    UserResponseDto addPrivilegeToUser(int idUser,List<Integer> privilegeIds);
    UserResponseDto removePrivilegeToUser(int idUser,List<Integer> privilegeIds);
    UserResponseDto inheritPrivilegeToUser(int idUser,List<Integer> privilegeIds);

    UserResponseDto addGroupPrivilegeToUser(int idUser,String group);
    UserResponseDto removeGroupPrivilegeToUser(int idUser,String group);

    UserResponseDto addAllPrivilegeToUser(int idUser);
    UserResponseDto removeAllPrivilegeToUser(int idUser);



    Boolean editPassWord(Integer id, String pass);

    Boolean deleteIds(List<Integer> ids);



    List<User> getUser();

    User getId(int id);

    User addUsers(User user);
}
