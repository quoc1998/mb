package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Data
@Entity
@Table(name = "menu_item_translation")
public class MenuItemTranslations implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name="locale",nullable = true)
    private String locale;

    @Column(name="name",nullable = true)
    private String name;

    @Column(name="description ")
    private String description ;

    @ManyToOne
    @JoinColumn(name="menu_items_id", nullable=false)
    private MenuItems menuItems;
}
