package com.backend.bank.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class LogoStorefontRequestDto implements Serializable {

    private static final long serialVersionUID = 7156526077883281623L;

    private String favicon;
    private String hearderLogo;
    private String footerLogo;
    private String footerBackground;
}
