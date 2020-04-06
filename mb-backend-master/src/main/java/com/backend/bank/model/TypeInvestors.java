package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "type_investors")
@NoArgsConstructor
public class TypeInvestors {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "local", nullable = true)
    private String local;

    @OneToMany(mappedBy = "typeInvestors")
    @JsonManagedReference
    private List<DetailTypeInvestors> detailTypeInvestors;
}
