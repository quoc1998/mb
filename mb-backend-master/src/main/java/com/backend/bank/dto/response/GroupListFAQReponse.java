package com.backend.bank.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class GroupListFAQReponse {
    private Integer id;
    private String name;
    private List<FAQResponse> faqs;
}
