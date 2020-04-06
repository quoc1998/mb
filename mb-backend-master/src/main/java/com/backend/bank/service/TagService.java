package com.backend.bank.service;

import com.backend.bank.dto.request.TagRequestDto;
import com.backend.bank.dto.response.TagReponseDto;

import java.util.List;

public interface TagService {
    TagReponseDto findById(String local, Integer id);
    List<TagReponseDto> findByAllLocal(String local);
    TagReponseDto addTag(String local, TagRequestDto tagRequestDto);
    TagReponseDto editTag(String local,Integer idTag, TagRequestDto tagRequestDto);
    void deleteTag(String local,Integer idTag);
}
