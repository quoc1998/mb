package com.backend.bank.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "mail_settings")
public class MailSettings implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @Column(name = "mail_from_address")
  private String mailFromAddress;

  @Column(name = "mail_from_name")
  private String mailFromName;

  @Column(name = "mail_host")
  private String mailHost;

  @Column(name = "mail_port")
  private String mailPort;

  @Column(name = "mail_username")
  private String mailUsername;

  @Column(name = "mail_password")
  private String mailPassword;

  @ManyToOne
  @JoinColumn(name = "mail_encryption_id")
  private Encryptions encryptions;


}
