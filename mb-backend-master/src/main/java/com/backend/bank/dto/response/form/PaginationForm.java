package com.backend.bank.dto.response.form;

import lombok.Data;

import java.util.List;

@Data
public class PaginationForm {
    private List<FormResponseDto> forms;
    private Integer size;
}
