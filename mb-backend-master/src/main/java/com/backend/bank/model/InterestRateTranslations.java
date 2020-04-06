package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "interest_rate_translations")
public class InterestRateTranslations implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "locale", nullable = false)
    private String locale;

    @Column(name = "description", nullable = true)
    @Type(type = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "interest_rate_id", nullable = false)
    @JsonBackReference
    private InterestRate interestRate;
}
