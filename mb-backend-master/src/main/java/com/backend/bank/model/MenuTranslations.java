package com.backend.bank.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Data
@Entity
@Table(name = "menu_translations")
public class MenuTranslations  implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id")
    private Menu menu;


    @Column(name="locale",nullable = false)
    private String locale;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="title")
    private String title;
}
