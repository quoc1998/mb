package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Entity
@Table(name = "detail_type_investors")
@NoArgsConstructor
public class DetailTypeInvestors {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "local", nullable = true)
    private String local;

    @ManyToOne
    @JoinColumn(name = "type_investors_id")
    @JsonBackReference
    private TypeInvestors typeInvestors;
}
