package com.backend.bank.model;

import lombok.Data;
import lombok.Getter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Data
@Entity
@Table(name = "menu")
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name="status",nullable = false)
    private int status;

    @Column(name="create_at",nullable = true)
    private Calendar createAt;

    @Column(name="update_at",nullable = true)
    private Calendar updateAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    private String position;

    @OneToMany(mappedBy = "menu",cascade = CascadeType.ALL)
    private List<MenuTranslations> menuTranslations;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuItems> menuItems;


}
