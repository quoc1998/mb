package com.backend.bank.serviceImpl;


import com.backend.bank.converters.bases.converter.FAQConverter;
import com.backend.bank.dto.request.FAQRequest;
import com.backend.bank.dto.response.FAQResponse;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.FAQ;
import com.backend.bank.model.FAQTranslation;
import com.backend.bank.model.GroupFAQ;
import com.backend.bank.repository.FAQRepository;
import com.backend.bank.repository.GroupFAQRepository;
import com.backend.bank.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FAQServiceImpl implements FAQService {
    @Autowired
    FAQConverter faqConverter;

    @Autowired
    FAQRepository faqRepository;

    @Autowired
    GroupFAQRepository groupFAQRepository;


    @Override
    public List<FAQResponse> findAll(String local) {
        return faqConverter.entityConverterListResponse(faqRepository.findAll(), local);
    }

    @Override
    public FAQResponse findById(Integer id, String local) {
        try {
            Optional<FAQ> faq = faqRepository.findById(id);
            if (!faq.isPresent()) {
                throw new NotFoundException("Not Found FAQ");
            }
            return faqConverter.entityConverterResponse(faq.get(), local);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("Not Found FAQ");
        }
    }

    @Override
    public boolean editFAQ(Integer id, FAQRequest faqRequest, String local) {
        try {
            FAQ faq = faqRepository.findById(id).orElse(null);
            if (faq == null) {
                throw new NotFoundException(("FAQ not found"));
            }
            Integer location = checkTranslation(faq.getFAQTranslations(), local);
            if (location == -1) {
                throw new NotFoundException(("FAQTranslation not found"));
            }
            GroupFAQ groupFAQ = groupFAQRepository.findById(faqRequest.getGroupFAQId()).get();
            faq.setGroupFAQ(groupFAQ);
            faq.setIsActive(faqRequest.getIsActive());
            faq.getFAQTranslations().get(location).setAnswer(faqRequest.getAnswer());
            faq.getFAQTranslations().get(location).setQuestion(faqRequest.getQuestion());
            faqRepository.save(faq);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addFAQ(FAQRequest faqRequest, String local) {
        try {
            FAQ faq = faqConverter.responseConverterEntity(faqRequest, local);
            if (faq == null) {
                throw new NotFoundException("Not create FAQ");
            }
            faqRepository.save(faq);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteFAQ(Integer id) {
        try {
            FAQ faq = faqRepository.findById(id).get();
            if (faq == null) {
                throw new NotFoundException("Not Found FAQ");
            }
            faqRepository.delete(faq);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteListFAQ(List<Integer> ids) {
        try {
            for (Integer id : ids
            ) {
                deleteFAQ(id);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FAQResponse> searchFAQ(String local, String search) {
        return faqConverter.entityConverterListResponse(faqRepository.searchFAQ(search), local);
    }

    public Integer checkTranslation(List<FAQTranslation> faqTranslations, String local) {
        Integer size = faqTranslations.size();
        for (Integer i = 0; i < size; i++) {
            if (local.equals(faqTranslations.get(i).getLocal())) {
                return i;
            }
        }
        return -1;
    }
}
