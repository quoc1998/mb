package com.backend.bank.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Configuration
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private StatusRepository statusRepository;
//
//    @Autowired
//    private CategoriesRepository categoriesRepository;
//
//    @Autowired
//    private SkillsRepository skillsRepository;
//
//    @Autowired
//    private RequestTypeRepository requestTypeRepository;
//
//    private void addRoleIfMissing(String name) {
//        if (!roleRepository.findByName(name).isPresent()) {
//            roleRepository.save(new RoleEntity(name));
//        }
//    }
//
//    private void addRequestTypeIfMissing(String name) {
//        if (!requestTypeRepository.findByName(name).isPresent()) {
//            requestTypeRepository.save(new RequestType(name));
//        }
//    }
//
//    private void addUserIfMissing(String username, String password, String... roles) {
//        if (!userRepository.findByEmail(username).isPresent()) {
//            UserEntity user = new UserEntity(username, new BCryptPasswordEncoder().encode(password), "f", "l");
//            user.setRoleEntities(new HashSet<>());
//
//            for (String role : roles) {
//                user.getRoleEntities().add(roleRepository.findByName(role).get());
//            }
//
//            userRepository.save(user);
//        }
//    }
//
//    private void addStatusIfMissing(String name) {
//        if (!statusRepository.findByName(name).isPresent()) {
//            statusRepository.save(new Status(name));
//        }
//    }
//
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
//
    @Value("${jwt-key}")
    private String signingKey;
}