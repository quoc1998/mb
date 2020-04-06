package com.backend.bank.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "tag")
public class Tag implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "local")
  private String local;

  @OneToMany(mappedBy = "tag")
  private List<Blocks> blocks;


}
