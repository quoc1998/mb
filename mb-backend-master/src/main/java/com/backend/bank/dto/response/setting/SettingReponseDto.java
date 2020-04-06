package com.backend.bank.dto.response.setting;

import com.backend.bank.model.CustomerFontends;
import com.backend.bank.model.Generals;
import com.backend.bank.model.MailSettings;
import com.backend.bank.model.Setting_id;
import lombok.Data;

import java.io.Serializable;

@Data
public class SettingReponseDto implements Serializable {

    private Generals generals;

    private MailSettings mailSettings;

    private CustomerFontends customerFontends;
}
