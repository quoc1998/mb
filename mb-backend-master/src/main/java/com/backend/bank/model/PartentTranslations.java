package com.backend.bank.model;


import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Data
@Entity
@Table(name = "partent_translations")
public class PartentTranslations {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name="locale",nullable = true)
    private String locale;

    @Column(name="name",nullable = true)
    private String name;

    @ManyToOne
    @JoinColumn(name="parent_id", nullable=false)
    private Parent parent;
}
