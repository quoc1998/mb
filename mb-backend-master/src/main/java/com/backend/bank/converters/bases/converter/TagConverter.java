package com.backend.bank.converters.bases.converter;

import com.backend.bank.converters.bases.converter.block.BlockConverter;
import com.backend.bank.dto.response.TagReponseDto;
import com.backend.bank.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagConverter {
    @Autowired
    BlockConverter blockConverter;

    public TagReponseDto converterEntity(String local, Tag tag) {
        TagReponseDto tagReponseDto = new TagReponseDto();
        tagReponseDto.setName(tag.getName());
        tagReponseDto.setId(tag.getId());
        tagReponseDto.setBlocks(blockConverter.converterListBlockEditorAdd(local, tag.getBlocks()));
        return tagReponseDto;
    }

    public List<TagReponseDto> converterListEntity(String local, List<Tag> tags) {
        List<TagReponseDto> tagReponseDtos = new ArrayList<>();
        try {
            for (Tag tag : tags
            ) {
                TagReponseDto tagReponseDto = this.converterEntity(local, tag);
                tagReponseDtos.add(tagReponseDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tagReponseDtos;
    }


}
