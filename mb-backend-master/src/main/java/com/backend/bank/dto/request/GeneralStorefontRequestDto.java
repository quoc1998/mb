package com.backend.bank.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class GeneralStorefontRequestDto  implements Serializable {

    private static final long serialVersionUID = 7156526077883281623L;

    private String footer_address;
    private String latitude;
    private String Longitude;
    private String footer_brief;
    private Information information;
}
