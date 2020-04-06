package com.backend.bank.dto.response.setting;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonReponseDto implements Serializable {

    private String locale;
    private GeneralReponseDto general;
    private LogoReponseDto logo;
    private SocialLinkReponseDto socialLink;
    private LinkAppResponseDto linkApp;
}
