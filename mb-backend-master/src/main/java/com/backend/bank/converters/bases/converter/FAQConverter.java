package com.backend.bank.converters.bases.converter;

import com.backend.bank.dto.request.FAQRequest;
import com.backend.bank.dto.response.FAQResponse;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.FAQ;
import com.backend.bank.model.FAQTranslation;
import com.backend.bank.model.GroupFAQ;
import com.backend.bank.repository.FAQRepository;
import com.backend.bank.repository.FAQTranslationRepository;
import com.backend.bank.repository.GroupFAQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FAQConverter {
    @Autowired
    FAQTranslationRepository faqTranslationRepository;

    @Autowired
    GroupFAQRepository groupFAQRepository;

    public List<FAQResponse> entityConverterListResponse(List<FAQ> faqList, String local) {
        List<FAQResponse> faqResponseList = new ArrayList<>();
        for (FAQ faq : faqList
        ) {
            FAQResponse faqResponse = entityConverterResponse(faq, local);
            faqResponseList.add(faqResponse);
        }
        return faqResponseList;
    }

    public FAQResponse entityConverterResponse(FAQ faq, String local) {
        FAQResponse faqResponse = new FAQResponse();
        Integer location = checkTranslation(faq.getId(), faq.getFAQTranslations(), local);
        if (location == -1) {
            FAQTranslation faqTranslation = new FAQTranslation();
            faqTranslation.setQuestion(faq.getFAQTranslations().get(0).getQuestion());
            faqTranslation.setAnswer(faq.getFAQTranslations().get(0).getAnswer());
            faqTranslation.setLocal(local);
            faqTranslation.setFaq(faq);
            faqTranslationRepository.save(faqTranslation);
            faqResponse.setAnswer(faq.getFAQTranslations().get(0).getAnswer());
            faqResponse.setQuestion(faq.getFAQTranslations().get(0).getQuestion());
        } else {
            faqResponse.setAnswer(faq.getFAQTranslations().get(location).getAnswer());
            faqResponse.setQuestion(faq.getFAQTranslations().get(location).getQuestion());
        }
        faqResponse.setId(faq.getId());
        faqResponse.setGroupFAQId(faq.getId());
        return faqResponse;
    }

    public FAQ responseConverterEntity(FAQRequest faqRequest, String local) {
        try {
            FAQ faq = new FAQ();
            faq.setIsActive(faqRequest.getIsActive());
            faq.setCreatedAt(new Date());
            List<FAQTranslation> faqTranslations = new ArrayList<>();
            FAQTranslation faqTranslation = new FAQTranslation();
            faqTranslation.setAnswer(faqRequest.getAnswer());
            faqTranslation.setQuestion(faqRequest.getQuestion());
            faqTranslation.setFaq(faq);
            faqTranslation.setLocal(local);
            faqTranslations.add(faqTranslation);
            faq.setFAQTranslations(faqTranslations);
            GroupFAQ groupFAQ = groupFAQRepository.findById(faqRequest.getGroupFAQId()).get();
            faq.setGroupFAQ(groupFAQ);
            return faq;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer checkTranslation(Integer id, List<FAQTranslation> faqTranslations, String local) {
        Integer size = faqTranslations.size();
        if (size.equals(0)) {
            throw new NotFoundException("FAQ not found translation: " + id);
        }
        for (Integer i = 0; i < size; i++) {
            if (local.equals(faqTranslations.get(i).getLocal())) {
                return i;
            }
        }
        return -1;
    }

}
