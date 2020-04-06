package com.backend.bank.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@Entity
@Table(name = "blocks")
@NoArgsConstructor
public class Blocks implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "html", nullable = false, length = 4000)
    private String html;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @OneToMany(mappedBy = "blocks", cascade = CascadeType.ALL)
    private List<BlockTranslations> blockTranslations;

    @OneToMany(mappedBy = "blocks", cascade = CascadeType.ALL)
    private List<BlockValues> blockValues;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deleted_at;
}
