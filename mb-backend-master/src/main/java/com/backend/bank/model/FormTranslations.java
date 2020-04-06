package com.backend.bank.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;


@Getter
@Data
@Entity
@Table(name = "form_translations")
public class FormTranslations {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="local")
    private String local;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="forms_id")
    private Forms form;



}
