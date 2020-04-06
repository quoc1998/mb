package com.backend.bank.service;

import com.backend.bank.dto.request.FormRequestDto;
import com.backend.bank.dto.request.SendFormRequestDto;
import com.backend.bank.dto.response.PaginationMail;
import com.backend.bank.dto.response.form.FormResponseDto;
import com.backend.bank.dto.response.form.PaginationForm;
import com.backend.bank.model.SendMailEntity;

import java.util.List;

public interface FormService {
    List<FormResponseDto> getForm(String local);
    Boolean sendMail(String local, SendFormRequestDto sendFormRequestDto);
    PaginationForm findAllPagination(String locale, Integer page , Integer number, String search);
    FormResponseDto findById(Integer id, String local);
    FormResponseDto addForm(FormRequestDto formsRequestDto,String local);
    FormResponseDto editForm(int form_id, FormRequestDto formsRequestDto,String local);
    void deleteForm(int form_id,String local);
    PaginationMail findAllByFormsId(String local, Integer formId, Integer page , Integer number);
    Boolean deleteIds(List<Integer> ids);
}
