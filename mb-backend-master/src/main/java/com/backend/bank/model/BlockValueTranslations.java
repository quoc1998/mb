package com.backend.bank.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "block_value_translations")
@Data
public class BlockValueTranslations implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "block_value_id")
  private BlockValues blockValue;

  @Column(name = "locale")
  private String locale;

  @Column(name = "title")
  private String title;


}
