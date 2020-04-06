package com.backend.bank.dto.request;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class ImagesRequestDto {
    private Integer id;
    private String url;
    private String name;

    private Long size;
    private String path;
    private Timestamp createdAt;
    private Date deletedAt;
    private Date updatedAt;
}
