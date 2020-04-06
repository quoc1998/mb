package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "exchange_rate")
public class ExchangeRate implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date_update")
    private Calendar date;

    @Column(name = "created_at")
    private Calendar createdAt;

    @Column(name = "update_at")
    private Date updateAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @OneToMany(mappedBy = "exchangeRate", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ExchangeRateDetail> exchangeRateDetails;
}
