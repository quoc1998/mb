package com.backend.bank.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Data
@Entity
@Table(name = "target")
public class Target {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name="create_at",nullable = true)
    private Calendar createAt;

    @Column(name="update_at",nullable = true)
    private Calendar updateAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @OneToMany(fetch=FetchType.EAGER, orphanRemoval=true, cascade=CascadeType.ALL)
    private List<TargetTranslations> translations;
}
