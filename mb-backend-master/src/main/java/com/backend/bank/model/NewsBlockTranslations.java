package com.backend.bank.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "news_block_translations")
public class NewsBlockTranslations implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "news_block_id")
  private NewsBlocks newsBlock;

  @Column(name = "locale")
  private String locale;

  @Column(name = "title")
  private String title;

  @Lob
  @Basic(fetch = FetchType.EAGER)
  @Column(name = "content")
  private String content;


}
