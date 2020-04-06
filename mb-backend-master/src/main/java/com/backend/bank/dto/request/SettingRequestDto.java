package com.backend.bank.dto.request;

import com.backend.bank.model.CustomerFontends;
import com.backend.bank.model.Generals;
import com.backend.bank.model.MailSettings;
import com.backend.bank.model.Setting_id;
import lombok.Data;

import java.io.Serializable;

@Data
public class SettingRequestDto implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;

    private GeneralSettingRequestDto generals;

    private MailSettingRequestDto mailSettings;

    private CustomerFESettingRequestDto customerFontends;
}
