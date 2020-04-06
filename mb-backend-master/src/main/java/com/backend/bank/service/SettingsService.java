package com.backend.bank.service;

import com.backend.bank.dto.request.SettingRequestDto;
import com.backend.bank.dto.response.setting.SettingReponseDto;
import org.springframework.stereotype.Component;

@Component
public interface SettingsService {
    SettingReponseDto edit(String locale, SettingRequestDto settingRequestDto);
}
