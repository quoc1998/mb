package com.backend.bank.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "countries")
public class Countries implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @Column(name = "code")
  private String code;

  @Column(name = "name")
  private String name;


}
