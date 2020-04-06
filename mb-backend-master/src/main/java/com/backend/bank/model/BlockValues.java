package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "block_values")
@NoArgsConstructor
public class BlockValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    @Column(name = "key_value", nullable = false)
    private String keyValue;

    @Column(name = "position", nullable = false)
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Types types;

    @OneToMany(mappedBy = "blockValue", cascade = CascadeType.ALL)
    private List<BlockValueTranslations> blockValueTranslations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id", nullable = true)
    private Blocks blocks;
}
