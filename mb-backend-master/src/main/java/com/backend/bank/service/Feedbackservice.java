package com.backend.bank.service;

import com.backend.bank.dto.request.FeedbackRequestDto;
import com.backend.bank.dto.response.form.FeedbackReponseDto;

public interface Feedbackservice {
    FeedbackReponseDto findById(String local, Integer id);
    FeedbackReponseDto addFeedback(String local, FeedbackRequestDto feedbackRequestDto);
    FeedbackReponseDto editFeedback(String local, Integer id, FeedbackRequestDto feedbackRequestDto);
}
