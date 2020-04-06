package com.backend.bank.dto.request;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class FeedbackRequestDto {
    private Integer id;
    private Integer status;
    private String subject;
    private String messageBody;
    private String notification;
    private String notificationBody;
}
