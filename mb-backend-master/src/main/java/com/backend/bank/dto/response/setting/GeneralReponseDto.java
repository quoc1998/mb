package com.backend.bank.dto.response.setting;

import com.backend.bank.dto.request.Information;
import lombok.Data;

@Data
public class GeneralReponseDto {
    private String footer_address;
    private String footer_brief;
    private String latitude;
    private String Longitude;
    private Information information;
}
