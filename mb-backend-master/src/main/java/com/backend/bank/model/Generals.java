package com.backend.bank.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "generals")
@Data
public class Generals implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "defaul_countries_id")
  private Countries defaulCountries;

  @ManyToMany
  @JoinTable(name = "support_locales",
          joinColumns = @JoinColumn(name = "general_id"),
          inverseJoinColumns = @JoinColumn(name = "countries_id"))
  private List<Countries> supportLocales;

  @ManyToMany
  @JoinTable(name = "support_countries",
          joinColumns = @JoinColumn(name = "general_id"),
          inverseJoinColumns = @JoinColumn(name = "countries_id"))
  private List<Countries> supportCountries;


}
