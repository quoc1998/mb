package com.backend.bank.configurations;

import com.backend.bank.dto.request.UserRequestDto;
import com.backend.bank.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitialDataUserLoader implements
        ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    UserRepository userRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        //write code

        alreadySetup = true;

    }
    public void createUser (UserRequestDto userRequestDto){

    }
}
