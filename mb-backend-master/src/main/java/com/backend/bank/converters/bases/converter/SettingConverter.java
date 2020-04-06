package com.backend.bank.converters.bases.converter;

import com.backend.bank.dto.request.CustomerFESettingRequestDto;
import com.backend.bank.dto.request.GeneralSettingRequestDto;
import com.backend.bank.dto.request.MailSettingRequestDto;
import com.backend.bank.model.*;
import com.backend.bank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SettingConverter {
    @Autowired
    GeneralsRepository generalsRepository;

    @Autowired
    CountriesRepository countriesRepository;

    @Autowired
    CustomerFontendsRepository customerFontendsRepository;

    @Autowired
    MailSettingsRepository mailSettingsRepository;

    @Autowired
    EncryptionsRepository encryptionsRepository;

    public Generals convertGeneralDtoToEntity(GeneralSettingRequestDto generalSettingRequestDto) {
        List<Generals> generalsList = generalsRepository.findAll();
        Generals generals = generalsList.get(0);
        Countries countries = countriesRepository.findById(generalSettingRequestDto.getDefaulCountries()).orElse(null);
        generals.setDefaulCountries(countries);
        List<Countries> countriesList = new ArrayList<>();
        for (int id : generalSettingRequestDto.getSupportLocales()) {
            countries = countriesRepository.findById(id).orElse(null);
            countriesList.add(countries);
        }
        generals.setSupportLocales(countriesList);
        countriesList = new ArrayList<>();
        for (int id : generalSettingRequestDto.getSupportCountries()) {
            countries = countriesRepository.findById(id).orElse(null);
            countriesList.add(countries);
        }
        generals.setSupportCountries(countriesList);
        return generals;
    }

    public CustomerFontends convertCustomerDtoToEntity(CustomerFESettingRequestDto customerFESettingRequestDto) {
        List<CustomerFontends> customerFontendsList = customerFontendsRepository.findAll();
        CustomerFontends customerFontends = customerFontendsList.get(0);
        customerFontends.setCustomerHeaderAssets(customerFESettingRequestDto.getCustomerHeaderAssets());
        customerFontends.setCustomerFoodterAssets(customerFESettingRequestDto.getCustomerFoodterAssets());
        return customerFontends;
    }

    public MailSettings convertMailDtoToEntity(MailSettingRequestDto mailSettingRequestDto) {
        List<MailSettings> mailSettingsList = mailSettingsRepository.findAll();
        MailSettings mailSettings = mailSettingsList.get(0);
        mailSettings.setMailFromAddress(mailSettingRequestDto.getMailFromAddress());
        mailSettings.setMailFromName(mailSettingRequestDto.getMailFromName());
        mailSettings.setMailHost(mailSettingRequestDto.getMailHost());
        mailSettings.setMailPort(mailSettingRequestDto.getMailPort());
        mailSettings.setMailUsername(mailSettingRequestDto.getMailUsername());
        mailSettings.setMailPassword(mailSettingRequestDto.getMailPassword());

        Encryptions encryptions = encryptionsRepository.findById(mailSettingRequestDto.getEncryptions()).orElse(null);
        mailSettings.setEncryptions(encryptions);

        return mailSettings;
    }
}
