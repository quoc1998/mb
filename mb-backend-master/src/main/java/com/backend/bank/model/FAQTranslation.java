package com.backend.bank.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "faq_translations")
public class FAQTranslation {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    @Column(name = "local")
    private String local;

    @ManyToOne
    @JoinColumn(name = "faq_id")
    private FAQ faq;
}
