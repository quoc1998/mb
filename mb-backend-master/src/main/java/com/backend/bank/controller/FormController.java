package com.backend.bank.controller;

import com.backend.bank.dto.request.FormRequestDto;
import com.backend.bank.dto.request.SendFormRequestDto;
import com.backend.bank.dto.response.PaginationMail;
import com.backend.bank.dto.response.form.FormResponseDto;
import com.backend.bank.dto.response.form.PaginationForm;
import com.backend.bank.dto.response.team.PaginationTeam;
import com.backend.bank.service.FormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{local}/api/forms")
public class FormController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    FormService formService;

    @Secured({"ROLE_GET FORM", "ROLE_XEM FORM"})
    @GetMapping
    public List<FormResponseDto> getForm(@PathVariable("local") String local) {
        return formService.getForm(local);
    }

    @Secured({"ROLE_GET FORM", "ROLE_XEM FORM"})
    @GetMapping("/{id}")
    public FormResponseDto findById(@PathVariable("id") Integer id, @PathVariable("local") String local) {
        return formService.findById(id, local);
    }

    @Secured({"ROLE_ADD FORM", "ROLE_THÊM FORM"})
    @PostMapping
    public FormResponseDto addForms(@RequestBody FormRequestDto formRequestDto, @PathVariable("local") String local) {
        return formService.addForm(formRequestDto, local);
    }

    @Secured({"ROLE_ADD FORM", "ROLE_THÊM FORM"})
    @PostMapping("/send")
    public Boolean sendMail(@RequestBody SendFormRequestDto sendFormRequestDto, @PathVariable("local") String local) {
        return formService.sendMail(local, sendFormRequestDto);
    }

    @Secured({"ROLE_EDIT FORM", "ROLE_SỬA FORM"})
    @PutMapping("/{id}")
    public FormResponseDto editForms(@PathVariable("id") int form_id, @PathVariable("local") String local, @RequestBody FormRequestDto formRequestDto) {
        return formService.editForm(form_id, formRequestDto, local);

    }

    @Secured({"ROLE_DELETE FORM", "ROLE_XÓA FORM"})
    @DeleteMapping("/{id}")
    public void deleteForms(@PathVariable("id") int form_id, @PathVariable("local") String local) {
        formService.deleteForm(form_id, local);
    }

    @Secured({"ROLE_DELETE FORM", "ROLE_XÓA FORM"})
    @DeleteMapping("/deleteIds")
    public Boolean deleteListImage(@RequestBody List<Integer> ids) {
        return formService.deleteIds(ids);
    }

    @Secured({"ROLE_GET GROUP", "ROLE_XEM NHÓM"})
    @GetMapping("/search")
    public PaginationForm searchForm(@PathVariable("local") String local,
                                     @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                     @RequestParam(name = "number", required = false, defaultValue = "10") Integer number,
                                     @RequestParam(name = "search", required = false, defaultValue = "") String search) {
        return formService.findAllPagination(local, page, number, search);
    }

    @Secured({"ROLE_GET FORM", "ROLE_XEM FORM"})
    @GetMapping("/mail/{id}")
    public PaginationMail findAllByFormsId(@PathVariable("local") String local,
                                   @PathVariable("id") Integer id,
                                   @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                   @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return formService.findAllByFormsId(local, id, page, number);
    }

}
