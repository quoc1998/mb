package com.backend.bank.serviceImpl;

import com.backend.bank.dto.response.TypesReponse;
import com.backend.bank.model.Types;
import com.backend.bank.repository.TypesRepository;
import com.backend.bank.service.TypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    TypesRepository typesRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<TypesReponse> findAll() {
        List<Types> types = typesRepository.findAll();

        List<TypesReponse> typesReponses = new ArrayList<>();
        for (Types types1 : types
        ) {
            TypesReponse typesReponse = modelMapper.map(types1, TypesReponse.class);
            typesReponses.add(typesReponse);
        }
        modelMapper.map(types, TypesReponse.class);
        return typesReponses;
    }
}
