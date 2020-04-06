package com.backend.bank.dto.response.form;
import lombok.Data;

import java.util.Calendar;


@Data
public class FormResponseDto{
    private Integer id;
    private  String list;
    private int status;
    private  String name;
    private Integer feedbackId;
    private String embedded;
    private Calendar createdAt;
}
