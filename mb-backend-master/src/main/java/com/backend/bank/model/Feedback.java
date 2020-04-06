package com.backend.bank.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Table(name = "feedback")
@Entity
@Data
public class Feedback implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @Column(name = "status")
  private Integer status;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "form_id")
  private Forms forms;

  @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL)
  List<FeedbackTranslation> feedbackTranslations = new ArrayList<>();

}
