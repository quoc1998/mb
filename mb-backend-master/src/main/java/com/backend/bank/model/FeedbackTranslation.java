package com.backend.bank.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

@Data
@Table(name = "feedback_translation")
@Entity
public class FeedbackTranslation implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @Column(name = "subject")
  private String subject;

  @Column(name = "local")
  private String local;

  @ManyToOne
  @JoinColumn(name = "feedback_id")
  private Feedback feedback;

  @Lob
  @Basic(fetch = FetchType.EAGER)
  @Column(name = "message_body")
  private String messageBody;


  @Column(name = "notification")
  private String notification;

  @Column(name = "notification_body")
  private String notificationBody;


}
