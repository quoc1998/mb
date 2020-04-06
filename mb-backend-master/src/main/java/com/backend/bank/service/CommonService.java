package com.backend.bank.service;

import com.backend.bank.dto.request.*;
import com.backend.bank.dto.response.setting.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public interface CommonService {
    CommonReponseDto findByName(String name, String locale) throws IOException;
    CommonReponseDto addCommon(String locale, CommonRequestDto commonRequestDto);
    CommonReponseDto editCommon(String locale,String name, CommonRequestDto commonRequestDto);
    LogoReponseDto editLogo(String locale, String name, LogoStorefontRequestDto commonRequestDto);
    GeneralReponseDto editGeneral(String locale, String name, GeneralStorefontRequestDto commonRequestDto);
    SocialLinkReponseDto editSocialLink(String locale, String name, SocialLinkRequestDto commonRequestDto);
    LinkAppResponseDto editLinkApp(String locale, String name, LinkAppRequestDto linkAppRequestDto);
}
