package com.backend.bank.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class NetworksReponseDto {
    private Integer id;

    private String longitude;

    private String latitude;

    private int status;

    private Date created_at;

    private Date updated_at;

    private String locale;

    private String network_category;

    private String address_name;

    private Integer province_city;

    private Integer district_city;

    private String address;

    private String description;

//    private String language;
}
