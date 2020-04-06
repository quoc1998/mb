package com.backend.bank.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import lombok.Data;

@Data
@Table(name = "slider_slides")
@Entity
public class SliderSlides implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "slider_id")
  private Sliders slider;

  @Column(name = "options")
  private String options;

  @Column(name = "call_to_action_url")
  private String callToActionUrl;

  @Column(name = "url_video_youtobe")
  private String urlVideoYoutobe;

  @Column(name = "position")
  private Integer position;

  @Column(name = "created_at", nullable = true)
  private Date createdAt;

  @Column(name = "updated_at", nullable = true)
  private Date updatedAt;

  @Column(name = "deleted_at")
  private Date deletedAt;

  @OneToMany(mappedBy = "sliderSlides", cascade = CascadeType.ALL)
  List<SliderSlideTranslation> sliderSlideTranslations = new ArrayList<>();
}
