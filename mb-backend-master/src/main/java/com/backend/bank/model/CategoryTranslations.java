package com.backend.bank.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category_translations")
@NoArgsConstructor
public class CategoryTranslations {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable =false)
    private int id;

    @Column(name = "locale", nullable = true)
    private String locale;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "description", nullable = true)
    private String description;

    @Column(name= "slug",nullable = true)
    private String slug;

    @Column(name= "meta_title",nullable = true)
    private String metaTitle;

    @Column(name= "meta_description", nullable = true)
    private String metaDescription;

    @Column(name = "meta_keyword", nullable = true)
    private String metaKeyword;

    @ManyToOne
    @JoinColumn(name="categorys_id", nullable=false)
    @JsonBackReference
    private Category category;
}
