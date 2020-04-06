package com.backend.bank.dto.request;

import com.backend.bank.model.NetworkTranslations;
import lombok.Data;

import java.io.Serializable;
@Data
public class NetworksRequestDto implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;

    private String longitude;

    private String latitude;

    private String network_category;

    private String address_name;

    private Integer province_city;

    private Integer district_city;

    private String address;

    private String description;

//    private String language;
}
