package com.backend.bank.serviceImpl;

import com.backend.bank.converters.bases.converter.TagConverter;
import com.backend.bank.dto.request.TagRequestDto;
import com.backend.bank.dto.response.TagReponseDto;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.Tag;
import com.backend.bank.repository.TagRepository;
import com.backend.bank.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagConverter tagConverter;


    @Autowired
    TagRepository tagRepository;


    @Override
    public TagReponseDto findById(String local, Integer id) {
        return tagConverter.converterEntity(local, tagRepository.findById(id).get());
    }

    @Override
    public List<TagReponseDto> findByAllLocal(String local) {
        return tagConverter.converterListEntity(local, tagRepository.findAllByLocal(local));
    }

    @Override
    public TagReponseDto addTag(String local, TagRequestDto tagRequestDto) {
        Tag tag = new Tag();
        tag.setName(tagRequestDto.getName());
        tag.setLocal(local);
        tagRepository.save(tag);
        return tagConverter.converterEntity(local, tag);
    }

    @Override
    public TagReponseDto editTag(String local, Integer idTag, TagRequestDto tagRequestDto) {
        Tag tag = tagRepository.findById(idTag).get();
        tag.setName(tagRequestDto.getName());
        tagRepository.save(tag);
        return tagConverter.converterEntity(local, tag);
    }

    @Override
    public void deleteTag(String local, Integer idTag) {
        try {
            tagRepository.deleteById(idTag);
        } catch (Exception e) {
            throw new NotFoundException("Not found tag");
        }
    }
}
