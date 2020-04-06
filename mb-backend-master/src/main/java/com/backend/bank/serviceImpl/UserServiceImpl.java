package com.backend.bank.serviceImpl;

import com.backend.bank.common.Constants;
import com.backend.bank.configurations.TokenProvider;
import com.backend.bank.dto.response.user.UserPrivilegeDTO;
import com.backend.bank.dto.request.Login;
import com.backend.bank.dto.request.UserPrivilegeRequest;
import com.backend.bank.dto.request.UserRequestDto;
import com.backend.bank.dto.response.AuthToken;
import com.backend.bank.dto.response.user.PaginationUser;
import com.backend.bank.dto.response.user.UserResponseDto;
import com.backend.bank.exception.BadRequestException;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.Privilege;
import com.backend.bank.model.Role;
import com.backend.bank.model.User;
import com.backend.bank.model.UserPrivilege;
import com.backend.bank.repository.PrivilegeRepository;
import com.backend.bank.repository.RoleRepository;
import com.backend.bank.repository.UserPrivilegeRepository;
import com.backend.bank.repository.UserRepository;
import com.backend.bank.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserPrivilegeRepository userPrivilegeRepository;

    @Override
    public List<UserResponseDto> getAllUser() {
        List<User> users = userRepository.findAll();
        return this.converterUser(users);
    }

    @Override
    public PaginationUser findAll(Integer page, Integer number) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<User> pageAll = userRepository.getAllBy(pageable);
        List<UserResponseDto> userResponseDtos = this.converterUser(pageAll.getContent());
        PaginationUser paginationUser = new PaginationUser();
        paginationUser.setUsers(userResponseDtos);
        paginationUser.setSize(pageAll.getTotalPages());
        return paginationUser;
    }

    @Override
    public PaginationUser getUserByNameSearch(Integer page, Integer number, String userName) {
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<User> pageAll = userRepository.getUserByNameSearch(pageable, userName);
        List<UserResponseDto> userResponseDtos = this.converterUser(pageAll.getContent());
        PaginationUser paginationUser = new PaginationUser();
        paginationUser.setUsers(userResponseDtos);
        paginationUser.setSize(pageAll.getTotalPages());
        return paginationUser;
    }

    @Override
    public UserResponseDto getUserInfo(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found!");
        }
        return new UserResponseDto(user.get());
    }

    public UserResponseDto findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found!");
        }
        return new UserResponseDto(user.get());
    }

    @Override
    public ResponseEntity<?> login(Login login) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                )
        );
        User user = userRepository.findByUserName(login.getUsername()).get();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token, user.getId()));
    }

    @Override
    public UserResponseDto register(UserRequestDto userRequestDto) {
        if (userRepository.findByUserName( userRequestDto.getUsername() ).isPresent())//kieu traa {

        {   throw new BadRequestException("User is existed");
        }
        if (!userRequestDto.getPassword().equals(userRequestDto.getPasswordConfirm())) {
            throw new BadRequestException("Password confirm not correct!");
        }
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userRequestDto.getPassword()));
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setDepartment(userRequestDto.getDepartment());
        user.setEmail(userRequestDto.getEmail());


        user.setRoles(new ArrayList<>());
        user.setStatus(0);

        user.setUserPrivileges(new ArrayList<>());

        if (userRequestDto.getRoles() != null) {
            List<Role> roles = new ArrayList<>();
            for (Integer roleId : userRequestDto.getRoles()
            ) {
                roles.add(roleRepository.findById(roleId).get());
            }
            user.setRoles(roles);
        }

        user.setUserPrivileges(addListUserPrivilege(user, userRequestDto.getUserPrivilegeRequests()));
        userRepository.save(user);
        return new UserResponseDto(user);
//        if(userRequestDto.getUserPrivilegeRequests()!=null){
//            List<UserPrivilege>userPrivileges =new ArrayList<>(  );
//            for(Integer privilegesId:userRequestDto.getUserPrivilegeRequests()
//            ){
//                userPrivileges.add( userPrivilegeRepository.findById( privilegesId ).get() );
//            }
//            user.setUserPrivileges( userPrivileges );
//        }
    }

    @Override
    public UserResponseDto editUser(int id, UserRequestDto userRequestDto) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        if (!userRequestDto.getPassword().equals(userRequestDto.getPasswordConfirm())) {
            throw new BadRequestException("Password confirm not correct!");
        }
        user.get().setUsername(userRequestDto.getUsername());
        user.get().setFirstName(userRequestDto.getFirstName());
        user.get().setLastName(userRequestDto.getLastName());
        user.get().setDepartment(userRequestDto.getDepartment());
        user.get().setEmail(userRequestDto.getEmail());
        user.get().setStatus(userRequestDto.getStatus());
        user.get().getRoles().removeAll(user.get().getRoles());
        if (!userRequestDto.getPassword().isEmpty()) {
            user.get().setPassword(new BCryptPasswordEncoder().encode(userRequestDto.getPassword()));
        }
        addRoleToUser(id, userRequestDto.getRoles());
        userPrivilegeRepository.deleteAllByUser(user.get().getId());
//        user.get().setUserPrivileges(addListUserPrivilege(user.get(), userRequestDto.getUserPrivilegeRequests()));
        userRepository.save(user.get());
        return new UserResponseDto(user.get());
    }



    @Override
    public void deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        userRepository.delete(user.get());
    }

    @Override
    public UserResponseDto addRoleToUser(int id, List<Integer> roleIdList) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        List<Role> roles = new ArrayList<>();
        for (Integer roleId : roleIdList) {
            List<Role> rolesList = roleRepository.findByRoleId(roleId);
            if (!user.get().getRoles().contains(roleIdList)) {
                roles.addAll(rolesList);
            }
        }
        user.get().getRoles().removeAll(user.get().getRoles());
        user.get().getRoles().addAll(roles);
        roles = user.get().getRoles();
        List<UserPrivilege> userPrivileges = addListPrivilegeToUser(user.get(), roles, Constants.INHERIT);
        for (UserPrivilege userPrivilege : userPrivileges) {
            if (checkDuplicatePrivilege(user.get(), userPrivilege)) {
                user.get().getUserPrivileges().add(userPrivilege);
            }
        }
        userRepository.save(user.get());
        return new UserResponseDto(user.get());
    }

    @Override
    public List<UserPrivilegeDTO> getAllPrivilegeOdUser(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        List<UserPrivilegeDTO> userPrivilegeDTOS = new ArrayList<>();
        for (UserPrivilege userPrivilege : user.get().getUserPrivileges()) {
            UserPrivilegeDTO userPrivilegeDTO = new UserPrivilegeDTO(userPrivilege);
            userPrivilegeDTOS.add(userPrivilegeDTO);
        }
        return userPrivilegeDTOS;
    }

    @Override
    public UserResponseDto addPrivilegeToUser(int idUser, List<Integer> privilegeIds) {
        Optional<User> user = userRepository.findById(idUser);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        List<Privilege> privileges = new ArrayList<>();
        for (Integer privilegeId : privilegeIds) {
            List<Privilege> privilegeListOfId = privilegeRepository.findByPrivilegeId(privilegeId);
            privileges.addAll(privilegeListOfId);
        }
        for (Privilege privilege : privileges) {
            Optional<UserPrivilege> userPrivilege = userPrivilegeRepository.findByUserAndPrivilege(user.get(), privilege);
            if (userPrivilege.isPresent()) {
                userPrivilege.get().setStatus(Constants.ACCEPT);
                userPrivilegeRepository.save(userPrivilege.get());
            } else {
                addPrivilegeToUser(user.get(), privilege, Constants.ACCEPT);
            }
        }
        userRepository.save(user.get());
        return new UserResponseDto(user.get());
    }

    @Override
    public UserResponseDto removePrivilegeToUser(int idUser, List<Integer> privilegeIds) {
        Optional<User> user = userRepository.findById(idUser);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        List<Privilege> privileges = new ArrayList<>();
        for (Integer privilegeId : privilegeIds) {
            List<Privilege> privilegeListOfId = privilegeRepository.findByPrivilegeId(privilegeId);
            privileges.addAll(privilegeListOfId);
        }
        for (Privilege privilege : privileges) {
            Optional<UserPrivilege> userPrivilege = userPrivilegeRepository.findByUserAndPrivilege(user.get(), privilege);
            if (userPrivilege.isPresent()) {
                userPrivilege.get().setStatus(Constants.DENY);
                userPrivilegeRepository.save(userPrivilege.get());
            } else {
                addPrivilegeToUser(user.get(), privilege, Constants.DENY);
            }
        }
        userRepository.save(user.get());
        return new UserResponseDto(user.get());
    }

    @Override
    public UserResponseDto inheritPrivilegeToUser(int idUser, List<Integer> privilegeIds) {
        Optional<User> user = userRepository.findById(idUser);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        List<Privilege> privileges = new ArrayList<>();
        for (Integer privilegeId : privilegeIds) {
            List<Privilege> privilegeListOfId = privilegeRepository.findByPrivilegeId(privilegeId);
            privileges.addAll(privilegeListOfId);
        }
        for (Privilege privilege : privileges) {
            Optional<UserPrivilege> userPrivilege = userPrivilegeRepository.findByUserAndPrivilege(user.get(), privilege);
            if (checkPrivilegeExistInRole(user.get().getRoles(), privilege) == true) {
                userPrivilege.get().setStatus(Constants.INHERIT);
            } else {
                userPrivilegeRepository.delete(userPrivilege.get());
            }
        }
        userRepository.save(user.get());
        return new UserResponseDto(user.get());
    }

    @Override
    public UserResponseDto addGroupPrivilegeToUser(int idUser, String group) {
        Optional<User> user = userRepository.findById(idUser);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        List<Privilege> privileges = privilegeRepository.findByGroupRole(group);
        if (privileges.size() == 0) {
            throw new NotFoundException("Group not right");
        }
        List<Privilege> privilegesTranslation = new ArrayList<>();
        for (Privilege privilege : privileges) {
            privilegesTranslation.addAll(privilegeRepository.findByPrivilegeId(privilege.getPrivilegeId()));
        }
        for (Privilege privilege : privilegesTranslation) {
            Optional<UserPrivilege> userPrivilege = userPrivilegeRepository.findByUserAndPrivilege(user.get(), privilege);
            if (userPrivilege.isPresent()) {
                userPrivilege.get().setStatus(Constants.ACCEPT);
                userPrivilegeRepository.save(userPrivilege.get());
            } else {
                addPrivilegeToUser(user.get(), privilege, Constants.ACCEPT);
            }
        }
        userRepository.save(user.get());
        return new UserResponseDto(user.get());
    }

    @Override
    public UserResponseDto removeGroupPrivilegeToUser(int idUser, String group) {
        Optional<User> user = userRepository.findById(idUser);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        List<Privilege> privileges = privilegeRepository.findByGroupRole(group);
        if (privileges.size() == 0) {
            throw new NotFoundException("Group not right");
        }
        List<Privilege> privilegesTranslation = new ArrayList<>();
        for (Privilege privilege : privileges) {
            privilegesTranslation.addAll(privilegeRepository.findByPrivilegeId(privilege.getPrivilegeId()));
        }
        for (Privilege privilege : privilegesTranslation) {
            Optional<UserPrivilege> userPrivilege = userPrivilegeRepository.findByUserAndPrivilege(user.get(), privilege);
            if (userPrivilege.isPresent()) {
                userPrivilege.get().setStatus(Constants.DENY);
                userPrivilegeRepository.save(userPrivilege.get());
            } else {
                addPrivilegeToUser(user.get(), privilege, Constants.DENY);
            }
        }
        userRepository.save(user.get());
        return new UserResponseDto(user.get());
    }

    @Override
    public UserResponseDto addAllPrivilegeToUser(int idUser) {
        Optional<User> user = userRepository.findById(idUser);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        List<Privilege> privileges = privilegeRepository.findAll();
        for (Privilege privilege : privileges) {
            Optional<UserPrivilege> userPrivilege = userPrivilegeRepository.findByUserAndPrivilege(user.get(), privilege);
            if (userPrivilege.isPresent()) {
                userPrivilege.get().setStatus(Constants.ACCEPT);
                userPrivilegeRepository.save(userPrivilege.get());
            } else {
                addPrivilegeToUser(user.get(), privilege, Constants.ACCEPT);
            }
        }
        userRepository.save(user.get());
        return new UserResponseDto(user.get());
    }

    @Override
    public UserResponseDto removeAllPrivilegeToUser(int idUser) {
        Optional<User> user = userRepository.findById(idUser);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        List<Privilege> privileges = privilegeRepository.findAll();
        for (Privilege privilege : privileges) {
            Optional<UserPrivilege> userPrivilege = userPrivilegeRepository.findByUserAndPrivilege(user.get(), privilege);
            if (userPrivilege.isPresent()) {
                userPrivilege.get().setStatus(Constants.DENY);
                userPrivilegeRepository.save(userPrivilege.get());
            } else {
                addPrivilegeToUser(user.get(), privilege, Constants.DENY);
            }
        }
        userRepository.save(user.get());
        return new UserResponseDto(user.get());
    }

    @Override
    public Boolean editPassWord(Integer id, String pass) {
        Boolean result = false;
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                String hash = BCrypt.hashpw(pass, BCrypt.gensalt(12));
                user.setPassword(hash);
                userRepository.save(user);
                result = true;
            } else {
                throw new NotFoundException("User Not Found");
            }
        } catch (Exception e) {
            result = false;
            throw new NotFoundException("User Not Found");
        }
        return result;
    }

    @Override
    public Boolean deleteIds(List<Integer> ids) {
        Boolean a;
        try {
            for (Integer id : ids
            ) {
                Optional<User> user = userRepository.findById(id);
                if (!user.isPresent()) {
                    throw new NotFoundException("User not found");
                }
                userRepository.delete(user.get());
            }
            a = true;
        } catch (Exception e) {
            a = false;
        }
        return a;
    }



    public UserPrivilege addPrivilegeToUser(User user, Privilege privilege, int status) {
        UserPrivilege userPrivilege = new UserPrivilege();
        userPrivilege.setUser(user);
        userPrivilege.setPrivilege(privilege);
        userPrivilege.setStatus(status);
        return userPrivilege;
    }

    public List<UserPrivilege> addListPrivilegeToUser(User user, List<Role> roles, int status) {
        List<Privilege> privileges = new ArrayList<>();
        for (Role role : roles) {
            privileges.addAll(role.getPrivileges());
        }
        List<UserPrivilege> userPrivileges = new ArrayList<>();
        for (Privilege privilege : privileges) {
            UserPrivilege userPrivilege = addPrivilegeToUser(user, privilege, status);
            userPrivileges.add(userPrivilege);
        }
        userPrivileges.stream().distinct();
        return userPrivileges;
    }

    public boolean checkDuplicatePrivilege(User user, UserPrivilege userPrivilege) {
        Privilege privilege = userPrivilege.getPrivilege();
        Optional<UserPrivilege> userPrivilegeOptional = userPrivilegeRepository.findByUserAndPrivilege(user, privilege);
        if (!userPrivilegeOptional.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean checkPrivilegeExistInRole(List<Role> roles, Privilege privilege) {
        List<Privilege> privileges = new ArrayList<>();
        for (Role role : roles) {
            privileges.addAll(role.getPrivileges());
        }
        if (privileges.contains(privilege)) {
            return true;
        }
        return false;
    }

    public List<UserPrivilege> addListUserPrivilege(User user, List<UserPrivilegeRequest> userPrivilegeRequests) {
        List<UserPrivilege> userPrivileges = new ArrayList<>();
        for (UserPrivilegeRequest userPrivilegeRequest : userPrivilegeRequests) {
            List<Privilege> privileges = privilegeRepository.findByPrivilegeId(userPrivilegeRequest.getPrivilegeId());
            for (Privilege privilege : privileges) {
                UserPrivilege userPrivilege = new UserPrivilege();
                userPrivilege.setPrivilege(privilege);
                userPrivilege.setStatus(userPrivilegeRequest.getStatus());
                userPrivilege.setUser(user);
                userPrivileges.add(userPrivilege);
            }

        }
        return userPrivileges;
    }

    public List<UserResponseDto> converterUser(List<User> users) {
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : users) {
            UserResponseDto userResponseDto = new UserResponseDto(user);
            userResponseDtos.add(userResponseDto);
        }
        return userResponseDtos;
    }



    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public User getId(int id) {
        return userRepository.getOne( id );
    }

    @Override
    public User addUsers(User user) {
        return userRepository.save(user);
    }

}
