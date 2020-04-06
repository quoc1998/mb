package com.backend.bank.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "send_mail")
public class SendMailEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Forms forms;

    @Column(name = "local")
    private String local;

    @Column(name = "created_at")
    private Date createdAt;

}
