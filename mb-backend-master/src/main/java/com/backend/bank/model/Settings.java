package com.backend.bank.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

@Data
@Table(name = "settings")
@Entity
public class Settings implements Serializable {
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  @AttributeOverrides({
          @AttributeOverride(name = "general_id", column = @Column(name = "general_id", nullable = false, length = 7)),
          @AttributeOverride(name = "mail_setting_id", column = @Column(name = "mail_setting_id", nullable = false)),
          @AttributeOverride(name = "customer_fontend_id", column = @Column(name = "customer_fontend_id", nullable = false)) })
  private Setting_id id;


  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "general_id", nullable = false, insertable = false, updatable = false)
  private Generals generals;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "mail_setting_id", nullable = false, insertable = false, updatable = false)
  private MailSettings mailSettings;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customer_fontend_id", nullable = false, insertable = false, updatable = false)
  private CustomerFontends customerFontends;


}
