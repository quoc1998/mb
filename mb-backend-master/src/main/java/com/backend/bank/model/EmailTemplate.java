package com.backend.bank.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "mail_templates")
public class EmailTemplate implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @Column(name = "code")
  private String code;

  @Column(name = "is_active")
  private Boolean active;

  @Column(name = "email_cc")
  private String emailCc;

  @OneToMany(mappedBy="emailTemplate",cascade = CascadeType.ALL)
  List<EmailTemplateTranslations> emailTemplateTranslations ;

  @Column(name = "created_at", nullable = false)
  private Date createdAt;

  @Column(name = "updated_at")
  private Date updatedAt;

  @Column(name = "deleted_at")
  private Date deletedAt;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getEmailCc() {
    return emailCc;
  }

  public void setEmailCc(String emailCc) {
    this.emailCc = emailCc;
  }
}
