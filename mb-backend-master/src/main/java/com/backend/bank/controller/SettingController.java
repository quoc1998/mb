package com.backend.bank.controller;

import com.backend.bank.common.Constants;
import com.backend.bank.dto.request.SettingRequestDto;
import com.backend.bank.dto.response.setting.SettingReponseDto;
import com.backend.bank.model.Countries;
import com.backend.bank.model.Encryptions;
import com.backend.bank.model.Settings;
import com.backend.bank.repository.CountriesRepository;
import com.backend.bank.repository.EncryptionsRepository;
import com.backend.bank.repository.SettingsRepository;
import com.backend.bank.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{locale}/api/setting")
public class SettingController {

    @Autowired
    SettingsRepository settingsRepository;

    @Autowired
    SettingsService settingsService;

    @Autowired
    CountriesRepository countriesRepository;

    @Autowired
    EncryptionsRepository encryptionsRepository;

    @Secured({"ROLE_GET SETTING", "ROLE_XEM CÀI ĐẶT"})
    @GetMapping
    public List<Settings> getSetting(@PathVariable("locale") String locale) {
        Constants.checkLocal(locale);
        List<Settings> settingsList = settingsRepository.findAll();
        return settingsList;
    }

    @Secured({"ROLE_EDIT SETTING", "ROLE_SỬA CÀI ĐẶT"})
    @PutMapping("/edit")
    public SettingReponseDto edit(@PathVariable("locale") String locale, @RequestBody SettingRequestDto settingRequestDto) {
        return settingsService.edit(locale, settingRequestDto);
    }

    @Secured({"ROLE_GET SETTING", "ROLE_XEM CÀI ĐẶT"})
    @GetMapping("/country")
    public List<Countries> getCountries(@PathVariable("locale") String locale) {
        Constants.checkLocal(locale);
        List<Countries> countriesList = countriesRepository.findAll();
        if (countriesList.isEmpty()) {
            return null;
        }
        return countriesList;
    }

    @Secured({"ROLE_GET SETTING", "ROLE_XEM CÀI ĐẶT"})
    @GetMapping("/encryptions")
    public List<Encryptions> getEncryptions(@PathVariable("locale") String locale) {
        Constants.checkLocal(locale);
        List<Encryptions> encryptions = encryptionsRepository.findAll();
        if (encryptions.isEmpty()) {
            return null;
        }
        return encryptions;
    }
}
