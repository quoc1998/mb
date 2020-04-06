package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "pages")
@NoArgsConstructor
public class Pages  implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "parentId", nullable = false)
    private Integer parentId;

    @Column(name= "position",nullable = true)
    private int position;

    @Column(name = "is_active", nullable = false)
    private Integer is_active;

    @Column(name = "has_sidebar", nullable = false)
    private Integer has_sidebar;

    @Column(name= "base_image",nullable = true)
    private String baseImage;

    @Column(name= "mini_image",nullable = true)
    private String miniImage;

    @Column(name = "template", nullable = false)
    private Integer template;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "section", length = 4000)
    private String section;

    @Column(name = "created_at", nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @OneToMany(mappedBy = "pages",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PageTranslations> pageTranslations;

    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL)
    private List<PageBlocks> pageBlocks;

    @OneToMany(mappedBy = "pages", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MenuItems> menuItemsList;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne()
    @JoinColumn(name = "menu_middle_id")
    private Menu menuMiddle;

    @OneToOne()
    @JoinColumn(name = "slide_id")
    private Sliders sliders;


}
