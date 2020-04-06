package com.backend.bank.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "group_faqs")
public class GroupFAQ {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @OneToMany(mappedBy = "groupFAQ", cascade = CascadeType.ALL)
    private List<GroupFAQTranslation> groupFAQTranslations;

    @OneToMany(mappedBy = "groupFAQ", cascade = CascadeType.ALL)
    private List<FAQ> faqList;

}
