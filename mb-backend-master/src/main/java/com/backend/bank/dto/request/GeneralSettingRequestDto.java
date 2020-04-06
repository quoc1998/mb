package com.backend.bank.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GeneralSettingRequestDto implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;

    private int defaulCountries;
    private List<Integer> supportLocales;
    private List<Integer> supportCountries;
}
