package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "interest_rate")
public class InterestRate implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "term", nullable = false)
    private int term;

    @Column(name = "interest_rate", nullable = false, columnDefinition = "float default 0")
    private Float interest_rate;

    @Column(name = "created_at", nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @OneToMany(mappedBy = "interestRate", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<InterestRateTranslations> interestRateTranslations;
}
