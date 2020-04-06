package com.backend.bank.serviceImpl;

import com.backend.bank.common.Constants;
import com.backend.bank.dto.request.*;
import com.backend.bank.dto.response.setting.*;
import com.backend.bank.model.Common;
import com.backend.bank.repository.CommonRepository;
import com.backend.bank.service.CommonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    CommonRepository commonRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public CommonReponseDto findByName(String name, String locale) throws IOException {
        Common common;
        Constants.checkLocal(locale);
        CommonReponseDto commonReponseDto = new CommonReponseDto();
        if (name.equalsIgnoreCase("logo")) {
            common = commonRepository.findByNameAndLocale("logo", locale);
            if (common == null) {
                LogoStorefontRequestDto logoRequetDto = new LogoStorefontRequestDto();
                logoRequetDto.setFavicon(".");
                logoRequetDto.setFooterBackground(".");
                logoRequetDto.setFooterLogo(".");
                logoRequetDto.setHearderLogo(".");
                common = new Common();
                try {
                    String json = objectMapper.writeValueAsString(logoRequetDto);
                    common.setJson(json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                common.setLocale(locale);
                common.setName("logo");

                commonRepository.save(common);
            }
            LogoReponseDto logoReposeDto = objectMapper.readValue(common.getJson(), LogoReponseDto.class);
            commonReponseDto.setLocale(common.getLocale());
            commonReponseDto.setLogo(logoReposeDto);
        }

        if (name.equalsIgnoreCase("linkapp")) {
            common = commonRepository.findByNameAndLocale("linkapp", locale);
            if (common == null) {
                LinkAppRequestDto linkAppRequestDto = new LinkAppRequestDto();
                linkAppRequestDto.setAndroid(".");
                linkAppRequestDto.setIOS(".");
                common = new Common();
                try {
                    String json = objectMapper.writeValueAsString(linkAppRequestDto);
                    common.setJson(json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                common.setLocale(locale);
                common.setName("linkapp");

                commonRepository.save(common);
            }
            LinkAppResponseDto linkAppResponseDto = objectMapper.readValue(common.getJson(), LinkAppResponseDto.class);
            commonReponseDto.setLocale(common.getLocale());
            commonReponseDto.setLinkApp(linkAppResponseDto);
        }

        if (name.equalsIgnoreCase("general")) {

            common = commonRepository.findByNameAndLocale("general", locale);

            if (common == null) {
                GeneralStorefontRequestDto generalRequetDto = new GeneralStorefontRequestDto();
                generalRequetDto.setFooter_address(".");
                generalRequetDto.setFooter_brief(".");
                generalRequetDto.setLatitude(".");
                generalRequetDto.setLongitude(".");
                common = new Common();
                try {
                    String json = objectMapper.writeValueAsString(generalRequetDto);
                    common.setJson(json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                common.setLocale(locale);
                common.setName("general");
                commonRepository.save(common);
            }
            GeneralReponseDto generalReponseDto = objectMapper.readValue(common.getJson(), GeneralReponseDto.class);
            commonReponseDto.setLocale(common.getLocale());
            commonReponseDto.setGeneral(generalReponseDto);
        }
        if (name.equalsIgnoreCase("sociallink")) {
            common = commonRepository.findByNameAndLocale("sociallink", locale);
            if (common == null) {
                SocialLinkRequestDto socialLinkRequestDto = new SocialLinkRequestDto();
                socialLinkRequestDto.setFacebook(".");
                socialLinkRequestDto.setGoogleplus(".");
                socialLinkRequestDto.setInstagram(".");
                socialLinkRequestDto.setLinkedin(".");
                socialLinkRequestDto.setYoutube(".");
                socialLinkRequestDto.setPinterest(".");
                socialLinkRequestDto.setTwitter(".");
                common = new Common();
                try {
                    String json = objectMapper.writeValueAsString(socialLinkRequestDto);
                    common.setJson(json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                common.setLocale(locale);
                common.setName("sociallink");
                commonRepository.save(common);
            }
            SocialLinkReponseDto socialLinkReponseDto = objectMapper.readValue(common.getJson(), SocialLinkReponseDto.class);
            commonReponseDto.setLocale(common.getLocale());
            commonReponseDto.setSocialLink(socialLinkReponseDto);
        }
        return commonReponseDto;
    }

    @Override
    public CommonReponseDto addCommon(String locale, CommonRequestDto commonRequestDto) {
        Constants.checkLocal(locale);
        return null;
    }

    @Override
    public CommonReponseDto editCommon(String locale, String name, CommonRequestDto commonRequestDto) {
        Constants.checkLocal(locale);
        CommonReponseDto commonReponseDto = new CommonReponseDto();
        try {
            Common commonLogo, commonSocialLink, commonGeneral, commonLinkApp;
            if (commonRequestDto.getLogo() != null) {
                commonLogo = commonRepository.findByNameAndLocale("logo", locale);
                try {
                    String json = objectMapper.writeValueAsString(commonRequestDto.getLogo());
                    commonLogo.setJson(json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                commonRepository.save(commonLogo);
                LogoReponseDto logoReposeDto = objectMapper.readValue(commonLogo.getJson(), LogoReponseDto.class);
                commonReponseDto.setLogo(logoReposeDto);
            }
            if (commonRequestDto.getSocialLink() != null) {
                commonSocialLink = commonRepository.findByNameAndLocale("sociallink", locale);
                try {
                    String json = objectMapper.writeValueAsString(commonRequestDto.getSocialLink());
                    commonSocialLink.setJson(json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                commonRepository.save(commonSocialLink);
                SocialLinkReponseDto socialLinkReponseDto = objectMapper.readValue(commonSocialLink.getJson(), SocialLinkReponseDto.class);
                commonReponseDto.setSocialLink(socialLinkReponseDto);
            }
            if (commonRequestDto.getGeneral() != null) {
                commonGeneral = commonRepository.findByNameAndLocale("general", locale);
                try {
                    String json = objectMapper.writeValueAsString(commonRequestDto.getGeneral());
                    commonGeneral.setJson(json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                commonRepository.save(commonGeneral);
                GeneralReponseDto generalReponseDto = objectMapper.readValue(commonGeneral.getJson(), GeneralReponseDto.class);
                commonReponseDto.setGeneral(generalReponseDto);
            }
            if (commonRequestDto.getLinkApp() != null) {
                commonLinkApp = commonRepository.findByNameAndLocale("linkapp", locale);
                try {
                    String json = objectMapper.writeValueAsString(commonRequestDto.getLinkApp());
                    commonLinkApp.setJson(json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                commonRepository.save(commonLinkApp);
                LinkAppResponseDto linkAppResponseDto = objectMapper.readValue(commonLinkApp.getJson(), LinkAppResponseDto.class);
                commonReponseDto.setLinkApp(linkAppResponseDto);
            }
            commonReponseDto.setLocale(locale);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commonReponseDto;
    }

    @Override
    public LogoReponseDto editLogo(String locale, String name, LogoStorefontRequestDto commonRequestDto) {
        LogoReponseDto logoReponseDto = new LogoReponseDto();
        Common commonLogo;

        commonLogo = commonRepository.findByNameAndLocale("logo", locale);
        try {
            String json = objectMapper.writeValueAsString(commonRequestDto);
            commonLogo.setJson(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        commonRepository.save(commonLogo);
        LogoReponseDto logoReposeDto = null;
        try {
            logoReposeDto = objectMapper.readValue(commonLogo.getJson(), LogoReponseDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logoReposeDto;
    }

    @Override
    public GeneralReponseDto editGeneral(String locale, String name, GeneralStorefontRequestDto commonRequestDto) {
        Common commonGeneral;
        commonGeneral = commonRepository.findByNameAndLocale("general", locale);
        try {
            String json = objectMapper.writeValueAsString(commonRequestDto);
            commonGeneral.setJson(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        commonRepository.save(commonGeneral);
        GeneralReponseDto generalReponseDto = null;
        try {
            generalReponseDto = objectMapper.readValue(commonGeneral.getJson(), GeneralReponseDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return generalReponseDto;
    }

    @Override
    public SocialLinkReponseDto editSocialLink(String locale, String name, SocialLinkRequestDto commonRequestDto) {
        Common commonSocialLink;
        commonSocialLink = commonRepository.findByNameAndLocale("sociallink", locale);
        try {
            String json = objectMapper.writeValueAsString(commonRequestDto);
            commonSocialLink.setJson(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        commonRepository.save(commonSocialLink);
        SocialLinkReponseDto socialLinkReponseDto = null;
        try {
            socialLinkReponseDto = objectMapper.readValue(commonSocialLink.getJson(), SocialLinkReponseDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socialLinkReponseDto;
    }

    @Override
    public LinkAppResponseDto editLinkApp(String locale, String name, LinkAppRequestDto linkAppRequestDto) {
        Common commonLinkApp;
        commonLinkApp = commonRepository.findByNameAndLocale("linkapp", locale);
        try {
            String json = objectMapper.writeValueAsString(linkAppRequestDto);
            commonLinkApp.setJson(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        commonRepository.save(commonLinkApp);
        LinkAppResponseDto linkAppResponseDto = null;
        try {
            linkAppResponseDto = objectMapper.readValue(commonLinkApp.getJson(), LinkAppResponseDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linkAppResponseDto;
    }
}
