package com.backend.bank.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

import lombok.Data;

@Data
@Table(name = "news_blocks")
@Entity
public class NewsBlocks implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "news_id")
  private News news;

  @Column(name = "position")
  private Integer position;
  @ManyToOne
  @JoinColumn(name = "block_id")
  private Blocks blocks;

  @OneToMany(mappedBy = "newsBlock", cascade = CascadeType.ALL)
  List<NewsBlockTranslations> newsBlockTranslations;

}
