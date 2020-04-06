package com.backend.bank.service;

import com.backend.bank.dto.request.GroupFAQRequest;
import com.backend.bank.dto.response.GroupFAQResponse;
import com.backend.bank.dto.response.GroupListFAQReponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface GroupFAQService {
    List<GroupFAQResponse> findAll(String local);
    GroupFAQResponse findById(Integer id, String local);
    boolean editGroupFAQ(Integer id, GroupFAQRequest groupFAQRequest, String local);
    boolean addGroupFAQ(GroupFAQRequest groupFAQRequest, String local);
    boolean deleteGroupFAQ(Integer id);
    boolean deleteListGroupFAQ(List<Integer> id);
    GroupListFAQReponse findAllFAQById(Integer id, String local);
}
