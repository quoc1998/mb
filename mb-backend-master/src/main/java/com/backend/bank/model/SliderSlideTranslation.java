package com.backend.bank.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "slider_slide_translation")
public class SliderSlideTranslation implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "slider_slide_id")
  private SliderSlides sliderSlides;

  @Column(name = "locale")
  private String locale;

  @Column(name = "image")
  private String image;

  @Column(name = "caption_1")
  private String caption1;

  @Column(name = "caption_2")
  private String caption2;

  @Column(name = "caption_3")
  private String caption3;

  @Column(name = "call_to_action_text")
  private String callToActionText;


}
