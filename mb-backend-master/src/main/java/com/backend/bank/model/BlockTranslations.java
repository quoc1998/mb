package com.backend.bank.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "block_translations")
public class BlockTranslations implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;


  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "block_id")
  private Blocks blocks;

  @Column(name = "locale")
  private String locale;

  @Column(name = "name")
  private String name;

  @Column(name = "image")
  private String image;

}
