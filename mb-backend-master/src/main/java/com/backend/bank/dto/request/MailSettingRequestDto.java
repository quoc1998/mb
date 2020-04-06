package com.backend.bank.dto.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class MailSettingRequestDto implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;

    private String mailFromAddress;
    private String mailFromName;
    private String mailHost;
    private String mailPort;
    private String mailUsername;
    private String mailPassword;
    private int encryptions;
}
