package com.backend.bank.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonRequestDto implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;
    private String locale;

    private GeneralStorefontRequestDto general;

    private LogoStorefontRequestDto logo;

    private SocialLinkRequestDto socialLink;

    private LinkAppRequestDto linkApp;


}
