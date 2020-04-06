package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "network_translations")
public class NetworkTranslations implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id ;

    @Column(name="locale" , nullable=false)
    private String locale;

    @Column(name = "network_category", nullable = false)
    private String network_category;

    @Column(name = "address_name", nullable = false)
    private String address_name;

    @Column(name = "province_city", nullable = true)
    private Integer province_city;

    @Column(name = "district_city", nullable = true)
    private Integer district_city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = true)
    @Type(type = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name="network_id", nullable=false)
    @JsonBackReference
    private Networks networks;
}
