package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@Table(name="mail_templates_translations")
public class EmailTemplateTranslations {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private int id ;

    @Column(name="locale" , nullable=false)
    private String locale;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="subject")
    private String subject;

    @Column(name="context")
    @Type(type="text")
    private String context;

    @ManyToOne
    @JoinColumn(name="email_template_id", nullable=false)
    private EmailTemplate emailTemplate;

}
