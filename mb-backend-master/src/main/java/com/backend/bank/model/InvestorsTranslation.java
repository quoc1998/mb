package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
@Data
@Entity
@Table(name = "investors_translations")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class InvestorsTranslation {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "local")
    private String local;

    @ManyToOne
    @JoinColumn(name = "investors_id", nullable = false)
    @JsonBackReference
    private Investors investors;


    @ManyToOne
    @JoinColumn(name = "type_investors_id")
    private TypeInvestors typeInvestors;

    @ManyToOne
    @JoinColumn(name = "detail_type_investors_id")
    private DetailTypeInvestors detailTypeInvestors;

}
