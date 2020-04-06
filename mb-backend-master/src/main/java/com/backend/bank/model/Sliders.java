package com.backend.bank.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "sliders")
@Data
public class Sliders implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @Column(name = "auto_play")
  private Integer autoPlay;

  @Column(name = "auto_play_speed")
  private Integer autoPlaySpeed;

  @Column(name = "arrows")
  private Integer arrows;

  @Column(name = "dots")
  private Integer dots;

  @Column(name = "created_at", nullable = true)
  private Date createdAt;

  @Column(name = "updated_at", nullable = true)
  private Date updatedAt;

  @Column(name = "deleted_at")
  private Date deletedAt;

  @OneToMany(mappedBy = "slider",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<SliderTranslations> sliderTranslations;

  @OneToMany(mappedBy = "slider",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  List<SliderSlides> sliderSlides = new ArrayList<>();


}
