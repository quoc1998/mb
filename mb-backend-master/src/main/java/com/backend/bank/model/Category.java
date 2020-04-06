package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable =     false)
    private int id;

    @Column(name = "is_active", nullable = false)
    private int is_active;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonManagedReference
    private Category category;

    @Column(name= "position",nullable = true)
    private int position;

    @Column(name= "base_image", nullable = true)
    private  String base_image;

    @Column(name= "create_at", nullable = true)
    @CreatedDate
    private Date createAt;

    @Column(name= "update_at", nullable = true)
    @LastModifiedDate
    private Date updateAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonBackReference
    List<CategoryTranslations> categoryTranslations;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    List<Category> categorys;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "category_team",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> team;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "categories")
    @JsonManagedReference
    private List<News> news;


}
