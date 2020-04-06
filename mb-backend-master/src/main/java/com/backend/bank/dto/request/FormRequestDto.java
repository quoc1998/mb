package com.backend.bank.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Calendar;

@Data
public class FormRequestDto implements Serializable {
    private  String list;
    private  String name;
    private Integer status;
    private String embedded;
}
