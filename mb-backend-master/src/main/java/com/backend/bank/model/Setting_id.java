package com.backend.bank.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Setting_id implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "general_id")
    private Integer general_id;

    @Column(name = "mail_setting_id")
    private Integer mail_setting_id;

    @Column(name = "customer_fontend_id")
    private Integer customer_fontend_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Setting_id)) return false;
        Setting_id that = (Setting_id) o;
        return Objects.equals(general_id, that.general_id) &&
                Objects.equals(mail_setting_id, that.mail_setting_id) &&
                Objects.equals(customer_fontend_id, that.customer_fontend_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(general_id, mail_setting_id, customer_fontend_id);
    }
}
