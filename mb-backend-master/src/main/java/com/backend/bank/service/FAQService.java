package com.backend.bank.service;

import com.backend.bank.dto.request.FAQRequest;
import com.backend.bank.dto.response.FAQResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface FAQService {
    List<FAQResponse> findAll(String local);
    FAQResponse findById(Integer id, String local);
    boolean editFAQ(Integer id, FAQRequest faqRequest, String local);
    boolean addFAQ(FAQRequest faqRequest, String local);
    boolean deleteFAQ(Integer id);
    boolean deleteListFAQ(List<Integer> id);
    List<FAQResponse> searchFAQ(String local, String search);

}
