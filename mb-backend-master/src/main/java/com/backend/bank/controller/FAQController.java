package com.backend.bank.controller;

import com.backend.bank.dto.request.FAQRequest;
import com.backend.bank.dto.response.FAQResponse;
import com.backend.bank.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("{local}/api/faq")
public class FAQController {

    @Autowired
    FAQService FAQService;

    @GetMapping
    public List<FAQResponse> findAll(@PathVariable("local") String local) {
        return FAQService.findAll(local);
    }

    @GetMapping("/{id}")
    public FAQResponse findById(@PathVariable("local") String local, @PathVariable("id") Integer id) {
        return FAQService.findById(id, local);
    }

    @PostMapping
    public Boolean addFAQ(@PathVariable("local") String local, @Valid @RequestBody FAQRequest FAQRequest) {
        return FAQService.addFAQ(FAQRequest, local);
    }

    @PutMapping("/{id}")
    public Boolean editFAQ(@PathVariable("local") String local, @PathVariable("id") Integer id,
                           @Valid @RequestBody FAQRequest FAQRequest) {
        return FAQService.editFAQ(id, FAQRequest, local);
    }

    @GetMapping("/search")
    public List<FAQResponse> searchFAQ(@PathVariable("local") String local, @RequestParam("search") String search) {
        return FAQService.searchFAQ(local, search);
    }

    @DeleteMapping
    public boolean deleteFAQ(@RequestBody List<Integer> ids) {
        return FAQService.deleteListFAQ(ids);
    }

}
