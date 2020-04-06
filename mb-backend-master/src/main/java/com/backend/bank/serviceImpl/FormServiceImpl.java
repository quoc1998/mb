package com.backend.bank.serviceImpl;

import com.backend.bank.common.Constants;
import com.backend.bank.converters.bases.converter.FormConverter;
import com.backend.bank.dto.request.FormRequestDto;
import com.backend.bank.dto.request.SendFormRequestDto;
import com.backend.bank.dto.response.PaginationMail;
import com.backend.bank.dto.response.form.FormResponseDto;
import com.backend.bank.dto.response.form.PaginationForm;
import com.backend.bank.exception.MailException;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.*;
import com.backend.bank.repository.FormRepository;
import com.backend.bank.repository.FormTranslationsRepository;
import com.backend.bank.repository.SendMailRepository;
import com.backend.bank.service.FormService;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class FormServiceImpl implements FormService {
    @Autowired
    FormRepository formRepository;
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    SendMailRepository sendMailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    FormTranslationsRepository formTranslationsRepository;

    @Autowired
    FormConverter formConverter;

    @Override
    public List<FormResponseDto> getForm(String local) {
        List<FormResponseDto> formResponseDtos = new ArrayList<>();
        List<Forms> forms = formRepository.findAll();
        for (Forms form : forms
        ) {
            FormResponseDto formResponseDto = formConverter.converterDto(local, form);
            formResponseDtos.add(formResponseDto);
        }
        Constants.checkLocal(local);
        return formResponseDtos;
    }



    @Override
    public PaginationForm findAllPagination(String locale, Integer page, Integer number, String search) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<Forms> pageAll = formRepository.getFormSearch(pageable,locale,  search);
        List<FormResponseDto> formResponseDtos = formConverter.converterListForm(locale, pageAll.getContent());
        PaginationForm paginationPage = new PaginationForm();
        paginationPage.setForms(formResponseDtos);
        paginationPage.setSize(pageAll.getTotalPages());
        return paginationPage;
    }

    @Override
    public FormResponseDto findById(Integer id, String local) {
        Constants.checkLocal(local);
        FormResponseDto formDTO;
        try {
            Forms forms = formRepository.findById(id).get();
            formDTO = formConverter.converterDto(local, forms);
        } catch (Exception e) {
            throw new NotFoundException("Form not found");
        }
        return formDTO;
    }

    @Override
    public FormResponseDto addForm(FormRequestDto formsRequestDto, String local) {
        Constants.checkLocal(local);
        Calendar calendar = Calendar.getInstance();
        Forms form = formConverter.converterEntity(local, formsRequestDto);
        form.setCreatedAt(calendar);
        formRepository.save(form);
        return formConverter.converterDto(local, form);
    }

    @Override
    public FormResponseDto editForm(int form_id, FormRequestDto formRequestDto, String local) {
        Constants.checkLocal(local);
        Forms form = formRepository.findById(form_id).get();
        FormTranslations formTranslations = formTranslationsRepository.findByForm_IdAndLocal(form_id, local);
        if (form == null || formTranslations == null) {
            throw new NotFoundException("Form not found");
        }
        int i = formConverter.checkFormTranslation(local, form.getFormTranslations());
        form.getFormTranslations().get(i).setName(formRequestDto.getName());
        form.setEmbedded(formRequestDto.getEmbedded());
        form.setList(formRequestDto.getList());
        form.setStatus(formRequestDto.getStatus());
        Calendar calendar = Calendar.getInstance();
        form.setUpdatedAt(calendar);
        formRepository.save(form);
        return formConverter.converterDto(local, form);
    }

    @Override
    public void deleteForm(int form_id, String local) {
        Constants.checkLocal(local);
        Optional<Forms> form = formRepository.findById(form_id);
        if (!form.isPresent()) {
            throw new NotFoundException("Form not found");
        }
        formRepository.delete(form.get());
    }

    @Override
    public PaginationMail findAllByFormsId(String local, Integer formId, Integer page, Integer number) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<SendMailEntity> sendMailEntities = sendMailRepository.findAllByFormsId(pageable, formId);
        PaginationMail paginationMail = new PaginationMail();
        paginationMail.setSendMailList(formConverter.converterListSendMail(sendMailEntities.getContent()) );
        paginationMail.setSize(sendMailEntities.getTotalPages());
        return paginationMail;
    }

    @Override
    public Boolean deleteIds(List<Integer> ids) {
        Boolean a;
        try {
            for (Integer id : ids) {
                Optional<Forms> form = formRepository.findById(id);
                formRepository.delete(form.get());
            }
            a = true;
        } catch (Exception e) {
            a = false;
            e.printStackTrace();
        }
        return a;
    }

    public Integer checkTranslation(List<FeedbackTranslation> feedbackTranslations, String local) {
        Integer size = feedbackTranslations.size();
        if (size.equals(0)) {
            throw new NotFoundException("FeedBack not found translation");
        }
        for (Integer i = 0; i < size; i++) {
            if (local.equals(feedbackTranslations.get(i).getLocal())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Boolean sendMail(String local, SendFormRequestDto sendFormRequestDto) {
        try {

            Optional<Forms> forms = formRepository.findById(sendFormRequestDto.getIdForm());
            if (!forms.isPresent()) {
                throw new NotFoundException("Not Found Forms");
            }
            Feedback feedback = forms.get().getFeedback();
            Integer location = checkTranslation(feedback.getFeedbackTranslations(), local);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, "utf-8");
            msg.setTo(sendFormRequestDto.getEmail());
            msg.setSubject(feedback.getFeedbackTranslations().get(location).getSubject());
            String plainText= feedback.getFeedbackTranslations().get(location).getMessageBody();
            msg.setText(plainText, true);
            mailSender.send( mimeMessage);

            // mail notification
            sendMailToEachPerson(mimeMessage, sendFormRequestDto.getEmail(), feedback, location);

            SendMailEntity sendMailEntity = new SendMailEntity();
            sendMailEntity.setContent(sendFormRequestDto.getContent());
            sendMailEntity.setForms(forms.get());
            sendMailEntity.setCreatedAt(new Date());
            sendMailEntity.setLocal(local);
            sendMailRepository.save(sendMailEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("Not Feedback Forms");
        }
    }

    public Boolean sendMailToEachPerson(MimeMessage mimeMessage, String mail, Feedback feedback, Integer location){
        MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, "utf-8");
        String mailNotifications = feedback.getFeedbackTranslations().get(location).getNotification();
        if (mailNotifications.isEmpty()){
            throw new NotFoundException("Not mail notification");
        }
        String[] listMail =  mailNotifications.split(",");
        if (listMail.length != 0){
            for (String mailNotification: listMail) {
                String mailNotificationNotSpace = mailNotification.trim();
                Pattern pattern = Pattern.compile(Constants.EMAIL_PATTERN);
                Boolean matcher = pattern.matcher(mailNotificationNotSpace).matches();
                if (!matcher){
                    StringBuilder notification = new StringBuilder("Incorrect mail format: ").append(mailNotificationNotSpace);
                    throw new MailException(notification.toString());
                }
                try {
                    msg.setTo(mailNotificationNotSpace);
                    msg.setSubject(feedback.getFeedbackTranslations().get(location).getSubject());
                    StringBuilder notificationBody = new StringBuilder(feedback.getFeedbackTranslations().get(location).getNotificationBody());
                    notificationBody.append(mail);
                    msg.setText(notificationBody.toString(), true);
                    mailSender.send( mimeMessage);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }


        return false;
    };


}
