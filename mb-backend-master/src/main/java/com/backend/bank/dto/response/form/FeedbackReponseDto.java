package com.backend.bank.dto.response.form;

import lombok.Data;

@Data
public class FeedbackReponseDto {
    private Integer id;
    private Integer status;
    private String subject;
    private String feedBackTo;
    private String messageBody;
    private String notification;
    private String notificationBody;
}
