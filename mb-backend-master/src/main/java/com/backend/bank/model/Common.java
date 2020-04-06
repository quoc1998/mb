package com.backend.bank.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.type.TextType;
import org.w3c.dom.Text;

@Entity
@Data
@Table(name = "common")
public class Common implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @Column(name = "locale", nullable = false)
  private String locale;

  @Column(name = "name")
  private String name;

  @Lob
  @Basic(fetch = FetchType.EAGER)
  @Column(name = "json")
  private String json;
}
