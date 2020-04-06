package com.backend.bank.dto.response;

import com.backend.bank.model.SendMailEntity;
import lombok.Data;

import java.util.List;

@Data
public class PaginationMail {
    private List<SendMailResponse> sendMailList;
    private Integer size;
}
