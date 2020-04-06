package com.backend.bank.converters.bases.converter;

import com.backend.bank.dto.request.FeedbackRequestDto;
import com.backend.bank.dto.response.form.FeedbackReponseDto;
import com.backend.bank.model.Feedback;
import com.backend.bank.model.FeedbackTranslation;
import com.backend.bank.repository.FeedbackTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeedBackConverter {
    @Autowired
    FeedbackTranslationRepository feedbackTranslationRepository;


    public Feedback converterFeedBackDto(String local, FeedbackRequestDto feedbackRequestDto) {
        Feedback feedback = new Feedback();
        feedback.setStatus(feedbackRequestDto.getStatus());
        List<FeedbackTranslation> feedbackTranslations = new ArrayList<>();
        FeedbackTranslation feedbackTranslation = new FeedbackTranslation();
        feedbackTranslation.setFeedback(feedback);
        feedbackTranslation.setMessageBody(feedbackRequestDto.getMessageBody());
        feedbackTranslation.setSubject(feedbackRequestDto.getSubject());
        feedbackTranslation.setLocal(local);
        feedbackTranslations.add(feedbackTranslation);
        feedback.setFeedbackTranslations(feedbackTranslations);
        return feedback;
    }

    public FeedbackReponseDto converterEntity(String local, Feedback feedback) {
        FeedbackReponseDto feedbackReponseDto = new FeedbackReponseDto();
        feedbackReponseDto.setId(feedback.getId());
        int i = this.checkTranslation(local, feedback.getFeedbackTranslations());
        FeedbackTranslation feedbackTranslation;
        if (i != -1) {
            feedbackTranslation = feedback.getFeedbackTranslations().get(i);
        } else {
            feedbackTranslation = new FeedbackTranslation();
            feedbackTranslation.setFeedback(feedback);
            feedbackTranslation.setLocal(local);
            feedbackTranslation.setMessageBody("");
            feedbackTranslation.setSubject("");
            feedbackTranslation.setNotificationBody(" ");
            feedbackTranslation.setNotification(" ");
            feedbackTranslationRepository.save(feedbackTranslation);

        }
        feedbackReponseDto.setStatus(feedback.getStatus());
        feedbackReponseDto.setSubject(feedbackTranslation.getSubject());
        feedbackReponseDto.setMessageBody(feedbackTranslation.getMessageBody());
        feedbackReponseDto.setNotification(feedbackTranslation.getNotification());
        feedbackReponseDto.setNotificationBody(feedbackTranslation.getNotificationBody());
        return feedbackReponseDto;
    }


    public int checkTranslation(String local, List<FeedbackTranslation> blockTranslations) {
        for (int i = 0; i < blockTranslations.size(); i++) {
            if (blockTranslations.get(i).getLocal().equalsIgnoreCase(local)) {
                return i;
            }
        }
        return -1;

    }
}
