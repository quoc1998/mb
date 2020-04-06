package com.backend.bank.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SocialLinkRequestDto  implements Serializable {

    private static final long serialVersionUID = 7156526077883281623L;

    private String youtube;
    private String facebook;
    private String twitter;
    private String instagram;
    private String linkedin;
    private String googleplus;
    private String pinterest;
}
