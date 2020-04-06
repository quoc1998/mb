package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "page_translations")
@NoArgsConstructor
public class PageTranslations {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "pages_id", nullable = true)
    @JsonBackReference
    private Pages pages;

    @Column(name = "locale" , nullable = false)
    private String locale;

    @Column(name = "name", nullable = false)
    private  String name;

    @Column(name = "slug")
    private String slug;

    @Column(name = "meta_title")
    private String meta_title;

    @Column(name = "meta_keyword")
    private String meta_keyword;

    @Column(name = "meta_description")
    private String meta_description;

}
