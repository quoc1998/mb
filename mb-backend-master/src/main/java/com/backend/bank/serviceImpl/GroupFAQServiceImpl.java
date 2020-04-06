package com.backend.bank.serviceImpl;

import com.backend.bank.converters.bases.converter.GroupFAQConverter;
import com.backend.bank.dto.request.GroupFAQRequest;
import com.backend.bank.dto.response.GroupFAQResponse;
import com.backend.bank.dto.response.GroupListFAQReponse;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.FAQ;
import com.backend.bank.model.GroupFAQ;
import com.backend.bank.model.GroupFAQTranslation;
import com.backend.bank.repository.GroupFAQRepository;
import com.backend.bank.service.GroupFAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GroupFAQServiceImpl implements GroupFAQService {
    @Autowired
    GroupFAQRepository groupFAQRepository;

    @Autowired
    GroupFAQConverter groupFAQConverter;

    @Override
    public List<GroupFAQResponse> findAll(String local) {
        return groupFAQConverter.entityConverterListResponse(groupFAQRepository.findAll(), local);
    }

    @Override
    public GroupFAQResponse findById(Integer id, String local) {
        try {
            Optional<GroupFAQ> faq = groupFAQRepository.findById(id);
            if (!faq.isPresent()) {
                throw new NotFoundException("Not Found FAQ");
            }
            return groupFAQConverter.entityConverterResponse(faq.get(), local);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("Not Found FAQ");
        }
    }

    @Override
    public boolean editGroupFAQ(Integer id, GroupFAQRequest groupFAQRequest, String local) {
        try {
            GroupFAQ groupFAQ = groupFAQRepository.findById(id).get();
            if (groupFAQ == null) {
                throw new NotFoundException("Not found groupFAQ");
            }
            groupFAQ.setIsActive(groupFAQRequest.getIsActive());
            Integer location = checkTranslation(groupFAQ.getGroupFAQTranslations(), local);
            groupFAQ.getGroupFAQTranslations().get(location).setName(groupFAQRequest.getName());
            groupFAQ.setUpdatedAt(new Date());
            groupFAQRepository.save(groupFAQ);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean addGroupFAQ(GroupFAQRequest groupFAQRequest, String local) {
        try {
            GroupFAQ groupFAQ = groupFAQConverter.requestConverterEntity(groupFAQRequest, local);
            groupFAQRepository.save(groupFAQ);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteGroupFAQ(Integer id) {
        try {
            GroupFAQ groupFAQ = groupFAQRepository.findById(id).get();
            if (groupFAQ == null) {
                throw new NotFoundException("Not Found GroupFAQ");
            }
            groupFAQRepository.delete(groupFAQ);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteListGroupFAQ(List<Integer> ids) {
        try {
            for (Integer id : ids
            ) {
                deleteGroupFAQ(id);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public GroupListFAQReponse findAllFAQById(Integer id, String local) {
        try {
            Optional<GroupFAQ> faq = groupFAQRepository.findById(id);
            if (!faq.isPresent()) {
                throw new NotFoundException("Not Found FAQ");
            }
            return groupFAQConverter.groupListFAQConverterResponse(faq.get(), local);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("Not Found FAQ");
        }
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
