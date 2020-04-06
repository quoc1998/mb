package com.backend.bank.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

@Table(name = "customer_fontends")
@Entity
@Data
public class CustomerFontends implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @Column(name = "customer_header_assets")
  private String customerHeaderAssets;

  @Column(name = "customer_foodter_assets")
  private String customerFoodterAssets;


}
