package com.backend.bank.dto.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class CustomerFESettingRequestDto implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;

    private String customerHeaderAssets;
    private String customerFoodterAssets;
}
