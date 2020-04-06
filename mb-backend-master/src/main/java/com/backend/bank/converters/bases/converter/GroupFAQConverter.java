package com.backend.bank.converters.bases.converter;

import com.backend.bank.dto.request.GroupFAQRequest;
import com.backend.bank.dto.response.FAQResponse;
import com.backend.bank.dto.response.GroupFAQResponse;
import com.backend.bank.dto.response.GroupListFAQReponse;
import com.backend.bank.model.GroupFAQ;
import com.backend.bank.model.GroupFAQTranslation;
import com.backend.bank.repository.GroupFAQRepository;
import com.backend.bank.repository.GroupFAQTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class GroupFAQConverter {
    @Autowired
    GroupFAQRepository groupFAQRepository;

    @Autowired
    FAQConverter faqConverter;

    @Autowired
    GroupFAQTranslationRepository groupFAQTranslationRepository;

    public GroupFAQResponse entityConverterResponse(GroupFAQ groupFAQ, String local) {
        GroupFAQResponse groupFAQResponse = new GroupFAQResponse();
        Integer location = checkTranslation(groupFAQ.getGroupFAQTranslations(), local);
        if (location == -1) {
            GroupFAQTranslation groupFAQTranslation = new GroupFAQTranslation();
            groupFAQTranslation.setGroupFAQ(groupFAQ);
            groupFAQTranslation.setLocale(local);
            groupFAQTranslation.setName(groupFAQ.getGroupFAQTranslations().get(0).getName());
            groupFAQTranslationRepository.save(groupFAQTranslation);
            groupFAQResponse.setName(groupFAQ.getGroupFAQTranslations().get(0).getName());
        } else {

            groupFAQResponse.setName(groupFAQ.getGroupFAQTranslations().get(location).getName());
        }
        groupFAQResponse.setId(groupFAQ.getId());
        groupFAQResponse.setIsActive(groupFAQ.getIsActive());
        return groupFAQResponse;
    }

    public GroupListFAQReponse groupListFAQConverterResponse(GroupFAQ groupFAQ, String local) {
        GroupListFAQReponse groupFAQResponse = new GroupListFAQReponse();
        List<FAQResponse> faqResponseList = faqConverter.entityConverterListResponse(groupFAQ.getFaqList(), local);
        Integer location = checkTranslation(groupFAQ.getGroupFAQTranslations(), local);
        if (location == -1) {
            GroupFAQTranslation groupFAQTranslation = new GroupFAQTranslation();
            groupFAQTranslation.setGroupFAQ(groupFAQ);
            groupFAQTranslation.setLocale(local);
            groupFAQTranslation.setName(groupFAQ.getGroupFAQTranslations().get(0).getName());
            groupFAQTranslationRepository.save(groupFAQTranslation);
            groupFAQResponse.setName(groupFAQ.getGroupFAQTranslations().get(0).getName());
        } else {

            groupFAQResponse.setName(groupFAQ.getGroupFAQTranslations().get(location).getName());
        }
        groupFAQResponse.setId(groupFAQ.getId());
        groupFAQResponse.setFaqs(faqResponseList);
        return groupFAQResponse;
    }

    public List<GroupFAQResponse> entityConverterListResponse(List<GroupFAQ> groupFAQs, String local) {
        List<GroupFAQResponse> groupFAQResponses = new ArrayList<>();
        for (GroupFAQ groupFAQ : groupFAQs
        ) {
            GroupFAQResponse groupFAQResponse = entityConverterResponse(groupFAQ, local);
            groupFAQResponses.add(groupFAQResponse);
        }
        return groupFAQResponses;
    }

    public GroupFAQ requestConverterEntity(GroupFAQRequest groupFAQRequest, String local) {
        GroupFAQ groupFAQ = new GroupFAQ();
        groupFAQ.setIsActive(groupFAQRequest.getIsActive());
        groupFAQ.setCreatedAt(new Date());
        List<GroupFAQTranslation> groupFAQTranslations = new ArrayList<>();
        GroupFAQTranslation groupFAQTranslation = new GroupFAQTranslation();
        groupFAQTranslation.setName(groupFAQRequest.getName());
        groupFAQTranslation.setLocale(local);
        groupFAQTranslation.setGroupFAQ(groupFAQ);
        groupFAQTranslations.add(groupFAQTranslation);
        groupFAQ.setGroupFAQTranslations(groupFAQTranslations);
        return groupFAQ;
    }


    public Integer checkTranslation(List<GroupFAQTranslation> groupFAQTranslations, String local) {
        Integer size = groupFAQTranslations.size();
        for (Integer i = 0; i < size; i++) {
            if (local.equals(groupFAQTranslations.get(i).getLocale())) {
                return i;
            }
        }
        return -1;
    }
}
