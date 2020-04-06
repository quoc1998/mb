package com.backend.bank.service;

import com.backend.bank.dto.request.MenuRequestDto;
import com.backend.bank.dto.response.menu.MenuFullResponseDto;
import com.backend.bank.dto.response.menu.MenuResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MenuService {
    List<MenuResponseDto> getAll(String locale);

    List<MenuResponseDto> findAllByPosition(String locale);

    MenuFullResponseDto findByPosition(String locale, String position);

    MenuFullResponseDto getById(int id, String locale);


    MenuResponseDto addMenu(String locale, MenuRequestDto menuRequestDto);
    MenuResponseDto editMenu(int id , String locale, MenuRequestDto menuRequestDto);
    void deleteMenu(String locale, int id);

    Boolean deleteIds(List<Integer> ids);

}
