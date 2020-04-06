package com.backend.bank.dto.response;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
@Data
public class ImagesReponseDTO {
    private Integer id;
    private String url;
    private String name;
    private Long size;
    private String path;
    private String type;
    private Timestamp createdAt;
    private Date deletedAt;
    private Date updatedAt;
}
