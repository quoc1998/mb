package com.backend.bank.service;

import com.backend.bank.dto.response.TypesReponse;

import java.util.List;

public interface TypeService {
    List<TypesReponse> findAll();
}
