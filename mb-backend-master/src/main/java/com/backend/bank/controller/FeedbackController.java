package com.backend.bank.controller;

import com.backend.bank.dto.request.FeedbackRequestDto;
import com.backend.bank.dto.response.form.FeedbackReponseDto;
import com.backend.bank.service.Feedbackservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("{local}/api/feedback")
public class FeedbackController {
    @Autowired
    Feedbackservice feedbackservice;

    @Secured({"ROLE_GET THƯ PHẢN HỒI", "ROLE_XEM THƯ PHẢN HỒI"})
    @GetMapping("/{id}")
    public FeedbackReponseDto findById(@PathVariable("local") String local, @PathVariable("id") Integer id) {
        return feedbackservice.findById(local, id);
    }

    @PostMapping
    public FeedbackReponseDto addFeedback(@PathVariable("local") String local, @RequestBody FeedbackRequestDto feedbackRequestDto) {
        return feedbackservice.addFeedback(local, feedbackRequestDto);
    }

    @Secured({"ROLE_EDIT THƯ PHẢN HỒI", "ROLE_SỬA THƯ PHẢN HỒI"})
    @PutMapping("/{id}")
    public FeedbackReponseDto editFeedback(@PathVariable("local") String local, @PathVariable("id") Integer id, @RequestBody FeedbackRequestDto feedbackRequestDto) {
        return feedbackservice.editFeedback(local, id, feedbackRequestDto);
    }
}
