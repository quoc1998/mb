package com.backend.bank.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "slider_translations")
@Data
public class SliderTranslations implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;


  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "slider_id")
  private Sliders slider;

  @Column(name = "locate")
  private String locate;

  @Column(name = "name")
  private String name;


}
