package com.backend.bank.controller.fontend;

import com.backend.bank.dto.request.TagRequestDto;
import com.backend.bank.dto.response.TagReponseDto;
import com.backend.bank.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{local}/api/fe/tag")
public class TagFrontendController {
    @Autowired
    TagService tagService;

    @GetMapping
    public List<TagReponseDto> getAllTag(@PathVariable("local") String local) {
        return tagService.findByAllLocal(local);
    }

    @GetMapping("{id}")
    public TagReponseDto getTag(@PathVariable("local") String local, @PathVariable("id") Integer idTag) {
        return tagService.findById(local, idTag);
    }

}
