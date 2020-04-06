package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "role_translate_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "local", nullable = false)
    private String local;

    @Column(name = "team_id", nullable = false)
    private int teamId;

    @OneToMany(mappedBy = "team")
    private List<Pages> pages;

    @ManyToMany(mappedBy = "team")
    @JsonBackReference
    private List<Category> categories;

    @ManyToMany(mappedBy = "teams")
    private List<Role> roles;

    @Column(name = "created_at", nullable = true)
    private Date createdAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

}
