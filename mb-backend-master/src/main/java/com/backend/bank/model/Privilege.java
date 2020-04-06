package com.backend.bank.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "privileges")
public class Privilege {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "privilege_translate_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "local", nullable = false)
    private String local;

    @Column(name = "privilege_id", nullable = false)
    private int privilegeId;

    @Column(nullable = false)
    private String groupRole;

    @OneToMany(mappedBy = "privilege", cascade = CascadeType.ALL)
    List<UserPrivilege> userPrivileges;

}
