package com.backend.bank.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "page_block_translations")
public class PageBlockTranslations implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "page_block_id")
  private PageBlocks pageBlock;

  @Column(name = "locale")
  private String locale;

  @Column(name = "title")
  private String title;

  @Column(name = "name")
  private String name;

  @Lob
  @Basic(fetch = FetchType.EAGER)
  @Column(name = "content")
  private String content;

  @Lob
  @Basic(fetch = FetchType.EAGER)
  @Column(name = "content_html", columnDefinition="CLOB")
  private String contentHtml;



}
