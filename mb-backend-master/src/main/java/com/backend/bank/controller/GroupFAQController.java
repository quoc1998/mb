package com.backend.bank.controller;

import com.backend.bank.dto.request.GroupFAQRequest;
import com.backend.bank.dto.response.GroupFAQResponse;
import com.backend.bank.dto.response.GroupListFAQReponse;
import com.backend.bank.service.GroupFAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("{local}/api/groupfaq")
public class GroupFAQController {
    @Autowired
    GroupFAQService groupFAQService;

    @GetMapping
    public List<GroupFAQResponse> findAll(@PathVariable("local") String local) {
        return groupFAQService.findAll(local);
    }

    @GetMapping("/{id}")
    public GroupFAQResponse findById(@PathVariable("local") String local, @PathVariable("id") Integer id) {
        return groupFAQService.findById(id, local);
    }

    @GetMapping("/list/{id}")
    public GroupListFAQReponse findAllById(@PathVariable("local") String local, @PathVariable("id") Integer id) {
        return groupFAQService.findAllFAQById(id, local);
    }

    @PutMapping("/{id}")
    public Boolean editGroupFAQ(@PathVariable("local") String local, @PathVariable("id") Integer id,
                                @Valid @RequestBody GroupFAQRequest groupFAQRequest) {
        return groupFAQService.editGroupFAQ(id, groupFAQRequest, local);
    }

    @PostMapping
    public Boolean addGroupFAQ(@PathVariable("local") String local, @Valid @RequestBody GroupFAQRequest groupFAQRequest) {
        return groupFAQService.addGroupFAQ(groupFAQRequest, local);
    }

    @DeleteMapping
    public boolean deleteGroupFAQ(@RequestBody List<Integer> ids) {
        return groupFAQService.deleteListGroupFAQ(ids);
    }
}
