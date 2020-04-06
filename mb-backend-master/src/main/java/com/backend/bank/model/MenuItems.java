package com.backend.bank.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
@Table(name = "menu_items")
@Entity
public class MenuItems implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
  @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
  @Column(name = "id", insertable = false, nullable = false)
  private Integer id;

  @Column(name = "category_id", nullable = true)
  private Integer categoryId;

  @Column(name = "category_new_id", nullable = true)
  private Integer categoryNewId;

  @Column(name = "is_active", nullable = false)
  private Boolean active;

  @Column(name = "is_fluid", nullable = false)
  private Boolean fluid;

  @Column(name = "is_root", nullable = true)
  private Boolean root;

  @ManyToOne
  @JoinColumn(name = "menu_id")
  private Menu menu;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  @JoinColumn(name = "pageId")
  private Pages pages;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private MenuItems menuItems;

  @Column(name = "position")
  private Integer position;

  @Column(name = "slug")
  private String slug;

  @Column(name = "slugpages")
  private String slugPages;

  @Column(name = "url")
  private String url;

  @Column(name = "icon")
  private String icon;


  @Column(name = "target_id", nullable = true)
  private Integer targetId;

  @Column(name = "type")
  private String type;

  @Column(name = "create_at", nullable = true)
  private Date createAt;

  @Column(name = "update_at")
  private Date updateAt;

  @Column(name = "deleted_at")
  private Date deletedAt;

  @OneToMany(mappedBy = "menuItems", cascade = CascadeType.ALL)
  private List<MenuItems> menuItemsList;

  @OneToMany(mappedBy = "menuItems", cascade = CascadeType.ALL)
  private List<MenuItemTranslations> menuItemTranslations;

}
