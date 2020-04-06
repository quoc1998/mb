package com.backend.bank.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Table(name = "icon")
@Data
public class Icon implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "url")
  private String url;

  @ManyToOne
  @JoinColumn(name = "slider_slide_id")
  private SliderSlides sliderSlide;


}
