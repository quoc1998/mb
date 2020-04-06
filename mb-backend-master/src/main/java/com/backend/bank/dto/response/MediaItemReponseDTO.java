package com.backend.bank.dto.response;
import lombok.Data;

@Data
public class MediaItemReponseDTO {
    private Boolean isFile;
    private String name;
    public MediaItemReponseDTO(Boolean isFile, String name) {
        this.isFile = isFile;
        this.name = name;
    }
}
