package com.backend.bank.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "IMAGES")
public class Images implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "url_image")
    private String url;

    @Column(name = "name_image")
    private String name;

    @Column(name = "path_image")
    private String path;

    @Column(name = "size_image")
    private Integer size;

    @Column(name = "type_image")
    private String type;

    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;


}
