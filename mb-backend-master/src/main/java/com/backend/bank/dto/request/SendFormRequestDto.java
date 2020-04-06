package com.backend.bank.dto.request;
import lombok.Data;

@Data
public class SendFormRequestDto {
    private Integer idForm;
    private String content;
    private String email;
}
