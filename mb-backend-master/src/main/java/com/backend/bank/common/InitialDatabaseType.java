package com.backend.bank.common;

import com.backend.bank.model.Types;
import com.backend.bank.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitialDatabaseType implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    @Autowired
    TypesRepository typesRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        createTypeIfNotFound(1, "Input", "Input is a texbox cho nhập dữ liệu");
        createTypeIfNotFound(2, "Multi page", " ");
        createTypeIfNotFound(3, "Single post", "Single post: chọn category -> chọn 1 post  thuộc category");
        createTypeIfNotFound(4, "Editor", "Editor: là editor nhưng có thể click repeat để add  more khối editor");
        createTypeIfNotFound(8, "Multiple post", "Muti post");
        createTypeIfNotFound(9, "Multiple image", "Multiple image: tương tự single image nhưng có thể add more image");
        createTypeIfNotFound(10, "Single Image", "Single Image: Cho nhập 1 image với ô  cho nhập title 1");
        createTypeIfNotFound(11, "Repeat", "Repeat: Hiển thị khung editor  cho phép  nhập  nội dung");
        createTypeIfNotFound(12, "Button", "Button cho phép click");
        createTypeIfNotFound(13, "Textarea", "Textarea là khung cho nhập text  nhưng có thể nhập nhiều text hơn");


        alreadySetup = true;
    }

    public void createTypeIfNotFound(Integer id, String name, String description) {
        if (!typesRepository.findByNames(name).isPresent()) {
            Types types = new Types();
            types.setId(id);
            types.setNames(name);
            types.setCodes(description);
            typesRepository.save(types);
        }

    }
}

