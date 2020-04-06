package com.backend.bank.serviceImpl;

import com.backend.bank.common.Constants;
import com.backend.bank.converters.bases.converter.SettingConverter;
import com.backend.bank.dto.request.CustomerFESettingRequestDto;
import com.backend.bank.dto.request.GeneralSettingRequestDto;
import com.backend.bank.dto.request.MailSettingRequestDto;
import com.backend.bank.dto.request.SettingRequestDto;
import com.backend.bank.dto.response.setting.SettingReponseDto;
import com.backend.bank.model.*;
import com.backend.bank.repository.CustomerFontendsRepository;
import com.backend.bank.repository.GeneralsRepository;
import com.backend.bank.repository.MailSettingsRepository;
import com.backend.bank.repository.SettingsRepository;
import com.backend.bank.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private GeneralsRepository generalsRepository;

    @Autowired
    private CustomerFontendsRepository customerFontendsRepository;

    @Autowired
    private MailSettingsRepository mailSettingsRepository;

    @Autowired
    private SettingConverter settingConverter;

    @Override
    public SettingReponseDto edit(String locale, SettingRequestDto settingRequestDto) {
        Constants.checkLocal(locale);
        SettingReponseDto settingReponseDto = new SettingReponseDto();
        try {
            List<Settings> settings = settingsRepository.findAll();
            Settings setting = settings.get(0);

            GeneralSettingRequestDto generalSettingRequestDto = settingRequestDto.getGenerals();
            Generals generals = settingConverter.convertGeneralDtoToEntity(generalSettingRequestDto);
            generalsRepository.save(generals);

            CustomerFESettingRequestDto customerFESettingRequestDto = settingRequestDto.getCustomerFontends();
            CustomerFontends customerFontends = settingConverter.convertCustomerDtoToEntity(customerFESettingRequestDto);
            customerFontendsRepository.save(customerFontends);

            MailSettingRequestDto mailSettingRequestDto = settingRequestDto.getMailSettings();
            MailSettings mailSettings = settingConverter.convertMailDtoToEntity(mailSettingRequestDto);
            mailSettingsRepository.save(mailSettings);

            setting.setGenerals(generals);
            setting.setCustomerFontends(customerFontends);
            setting.setMailSettings(mailSettings);
            settingsRepository.save(setting);
            settingReponseDto.setGenerals(setting.getGenerals());
            settingReponseDto.setCustomerFontends(setting.getCustomerFontends());
            settingReponseDto.setMailSettings(setting.getMailSettings());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return settingReponseDto;
    }
}
