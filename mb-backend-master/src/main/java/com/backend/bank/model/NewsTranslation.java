package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "news_translations")
@NoArgsConstructor
public class NewsTranslation {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "locale", nullable = true)
    private String locale;

    @Column(name = "short_description", nullable = true)
    private String shortDescription;

    @Column(name = "url", nullable = true)
    private String url;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @NotNull(message = "description not null")
    @Column(name = "description", nullable = true)
    private String description;

    @Column(name= "title", nullable = true)
    private String title;

    @Column(name= "meta_title",nullable = true)
    private String meta_title;

    @Column(name= "meta_description", nullable = true)
    private String meta_description;

    @Column(name = "meta_keyword", nullable = true)
    private String meta_keyword;

    @Column(name = "is_active", nullable = true)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name="news_id", nullable=false)
    @JsonBackReference
    private News news;
}
