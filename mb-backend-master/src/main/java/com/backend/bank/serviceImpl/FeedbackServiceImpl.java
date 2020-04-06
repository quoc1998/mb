package com.backend.bank.serviceImpl;

import com.backend.bank.converters.bases.converter.FeedBackConverter;
import com.backend.bank.dto.request.FeedbackRequestDto;
import com.backend.bank.dto.response.form.FeedbackReponseDto;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.Feedback;
import com.backend.bank.model.FeedbackTranslation;
import com.backend.bank.repository.FeedbackRepository;
import com.backend.bank.service.Feedbackservice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements Feedbackservice {
    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FeedBackConverter feedBackConverter;

    @Override
    public FeedbackReponseDto findById(String local, Integer id) {
        return feedBackConverter.converterEntity(local, feedbackRepository.findByFormsId(id).get());
    }

    @Override
    public FeedbackReponseDto addFeedback(String local, FeedbackRequestDto feedbackRequestDto) {
        Feedback feedback = feedBackConverter.converterFeedBackDto(local, feedbackRequestDto);
        feedbackRepository.save(feedback);
        return feedBackConverter.converterEntity(local, feedback);
    }

    @Override
    public FeedbackReponseDto editFeedback(String local, Integer id, FeedbackRequestDto feedbackRequestDto) {
        Feedback feedback = feedbackRepository.findById(id).orElse(null);
        if (feedback == null) {
            throw new NotFoundException("Not found feedback");
        }
        feedback.setStatus(feedbackRequestDto.getStatus());
        int i = checkTranslation(local, feedback.getFeedbackTranslations());
        feedback.getFeedbackTranslations().get(i).setSubject(feedbackRequestDto.getSubject());
        feedback.getFeedbackTranslations().get(i).setMessageBody(feedbackRequestDto.getMessageBody());
        feedback.getFeedbackTranslations().get(i).setNotification(feedbackRequestDto.getNotification());
        feedback.getFeedbackTranslations().get(i).setNotificationBody(feedbackRequestDto.getNotificationBody());
        feedbackRepository.save(feedback);
        return feedBackConverter.converterEntity(local, feedback);
    }

    public int checkTranslation(String local, List<FeedbackTranslation> blockTranslations) {
        Integer size = blockTranslations.size();
        if (size == 0 ){
            throw new NotFoundException("Feedback not translation");
        }
        for (int i = 0; i < size; i++) {
            if (blockTranslations.get(i).getLocal().equalsIgnoreCase(local)) {
                return i;
            }
        }
        return -1;

    }
}
