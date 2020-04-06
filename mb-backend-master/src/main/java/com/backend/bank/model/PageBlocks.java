package com.backend.bank.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

import lombok.Data;

@Table(name = "page_blocks")
@Entity
@Data
public class PageBlocks implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "page_id")
  private Pages page;

  @Column(name = "position")
  private Integer position;

  @ManyToOne
  @JoinColumn(name = "block_id")
  private Blocks blocks;

  @OneToMany(mappedBy = "pageBlock", cascade = CascadeType.ALL)
  private List<PageBlockTranslations> pageBlockTranslations;

}
