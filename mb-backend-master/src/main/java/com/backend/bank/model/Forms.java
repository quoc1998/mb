package com.backend.bank.model;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@Getter
@Data
@Entity
@Table(name="forms")
public class Forms implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id")
    private Integer id;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name="list")
    private  String list;

    @Column(name="status",columnDefinition = "integer default 0")
    private int status;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name="embedded")
    private  String embedded;

    @Column(name = "created_at", nullable = true)
    private Calendar createdAt;

    @Column(name = "deleted_at")
    private Calendar deletedAt;

    @Column(name = "updated_at")
    private Calendar updatedAt;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
    private List<FormTranslations> formTranslations ;

    @OneToOne(mappedBy = "forms", cascade = CascadeType.ALL)
    private Feedback feedback;

}
