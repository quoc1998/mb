package com.backend.bank.dto.response;

import com.backend.bank.model.Forms;
import lombok.Data;

import java.util.Date;

@Data
public class SendMailResponse {
    private Integer id;
    private String content;
    private Integer formsId;
    private Date createdAt;
}
