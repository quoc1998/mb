package com.backend.bank.controller;

import com.backend.bank.dto.request.TagRequestDto;
import com.backend.bank.dto.response.TagReponseDto;
import com.backend.bank.model.Tag;
import com.backend.bank.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{local}/api/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @Secured({"ROLE_GET TAG", "ROLE_XEM TAG"})
    @GetMapping
    public List<TagReponseDto> getAllTag(@PathVariable("local") String local) {
        return tagService.findByAllLocal(local);
    }

    @Secured({"ROLE_GET TAG", "ROLE_XEM TAG"})
    @GetMapping("{id}")
    public TagReponseDto getTag(@PathVariable("local") String local, @PathVariable("id") Integer idTag) {
        return tagService.findById(local, idTag);
    }

    @Secured({"ROLE_ADD TAG", "ROLE_THÊM TAG"})
    @PostMapping
    public TagReponseDto addTag(@PathVariable("local") String local, @RequestBody TagRequestDto tagRequestDto) {
        return tagService.addTag(local, tagRequestDto);
    }

    @Secured({"ROLE_EDIT TAG", "ROLE_SỬA TAG"})
    @PutMapping("/{id}")
    public TagReponseDto editTag(@PathVariable("local") String local, @PathVariable("id") Integer idTag,
                                 @RequestBody TagRequestDto tagRequestDto) {
        return tagService.editTag(local, idTag, tagRequestDto);
    }

    @Secured({"ROLE_DELETE TAG", "ROLE_XÓA TAG"})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("local") String local, @PathVariable("id") Integer idTag) {
        tagService.deleteTag(local, idTag);
    }
}
