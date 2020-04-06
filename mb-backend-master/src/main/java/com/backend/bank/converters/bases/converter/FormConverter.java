package com.backend.bank.converters.bases.converter;

import com.backend.bank.dto.request.FormRequestDto;
import com.backend.bank.dto.response.SendMailResponse;
import com.backend.bank.dto.response.form.FormResponseDto;
import com.backend.bank.model.*;
import com.backend.bank.repository.FeedbackTranslationRepository;
import com.backend.bank.repository.FormTranslationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class FormConverter {

    @Autowired
    FeedbackTranslationRepository feedbackTranslationRepository;

    @Autowired
    FormTranslationsRepository formTranslationsRepository;

    public Forms converterEntity(String local, FormRequestDto formRequestDto) {
        Forms form = new Forms();
        FormTranslations translations = new FormTranslations();
        translations.setLocal(local);
        translations.setForm(form);
        translations.setName(formRequestDto.getName());
        form.setList(formRequestDto.getList());
        form.setCreatedAt(Calendar.getInstance());
        form.setStatus(formRequestDto.getStatus());
        List<FormTranslations> formTranslations = new ArrayList<>();
        formTranslations.add(translations);
        form.setFormTranslations(formTranslations);
        Feedback feedback = new Feedback();
        feedback.setStatus(0);
        feedback.setForms(form);
        List<FeedbackTranslation> feedbackTranslations = new ArrayList<>();
        FeedbackTranslation feedbackTranslation;
        feedbackTranslation = new FeedbackTranslation();
        feedbackTranslation.setFeedback(feedback);
        feedbackTranslation.setLocal(local);
        feedbackTranslation.setMessageBody("");
        feedbackTranslation.setSubject("");
        feedbackTranslations.add(feedbackTranslation);
        feedback.setFeedbackTranslations(feedbackTranslations);
        form.setFeedback(feedback);
        form.setEmbedded(formRequestDto.getEmbedded());
        return form;
    }

    public FormResponseDto converterDto(String local, Forms forms) {
        FormResponseDto formResponseDto = new FormResponseDto();
        formResponseDto.setId(forms.getId());
        int i = checkFormTranslation(local, forms.getFormTranslations());
        if (i != -1) {
            formResponseDto.setName(forms.getFormTranslations().get(i).getName());
        } else {
            FormTranslations translations = new FormTranslations();
            translations.setName("");
            translations.setLocal(local);
            translations.setForm(forms);
            formTranslationsRepository.save(translations);
            formResponseDto.setName("");
        }

        formResponseDto.setList(forms.getList());
        formResponseDto.setStatus(forms.getStatus());
        formResponseDto.setFeedbackId(forms.getFeedback().getId());
        formResponseDto.setEmbedded(forms.getEmbedded());
        formResponseDto.setCreatedAt(forms.getCreatedAt());
        return formResponseDto;
    }


    public List<FormResponseDto> converterListForm(String local, List<Forms> forms) {
        List<FormResponseDto> formResponseDtos = new ArrayList<>();
        for (Forms forms1 : forms
        ) {
            FormResponseDto formResponseDto = this.converterDto(local, forms1);
            formResponseDtos.add(formResponseDto);

        }
        return formResponseDtos;
    }


    public SendMailResponse converterSendMail(SendMailEntity sendMailEntity){
        SendMailResponse sendMailResponse = new SendMailResponse();
        sendMailResponse.setContent(sendMailEntity.getContent());
        sendMailResponse.setFormsId(sendMailEntity.getForms().getId());
        sendMailResponse.setCreatedAt(sendMailEntity.getCreatedAt());
        sendMailResponse.setId(sendMailEntity.getId());
        return sendMailResponse;
    }

    public List<SendMailResponse> converterListSendMail(List<SendMailEntity> sendMailEntities){
        List<SendMailResponse> sendMailResponses = new ArrayList<>();
        for (SendMailEntity sendMailEntity : sendMailEntities
        ) {
            SendMailResponse sendMailResponse = this.converterSendMail(sendMailEntity);
            sendMailResponses.add(sendMailResponse);
        }
        return sendMailResponses;
    }

    public int checkFormTranslation(String local, List<FormTranslations> formTranslations) {
        for (int i = 0; i < formTranslations.size(); i++
        ) {
            if (formTranslations.get(i).getLocal().equalsIgnoreCase(local)) {
                return i;
            }
        }
        return -1;
    }


}
