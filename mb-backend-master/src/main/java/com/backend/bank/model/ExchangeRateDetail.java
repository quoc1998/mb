package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "exchange_rate_detail")
public class ExchangeRateDetail implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "currency")
    private String currency;

    @Column(name = "buy_cash")
    private Double buy_cash;

    @Column(name = "buy_transfer", columnDefinition = "float default 0")
    private Double buy_transfer;

    @Column(name = "sell", columnDefinition = "float default 0")
    private Double sell;

    @Column(name = "change_USD", columnDefinition = "float default 0")
    private Double change_USD;

    @ManyToOne
    @JoinColumn(name = "exchange_rate_id", nullable = false)
    @JsonBackReference
    private ExchangeRate exchangeRate;
}
