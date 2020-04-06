package com.backend.bank.model;

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
@Table(name = "investors")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Investors {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "name_file")
    private String nameFile;

    @Column(name = "url_video")
    private String urlVideo;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "url_file", nullable = false, length = 4000)
    private String urlFile;

    @Column(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "investors", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<InvestorsTranslation> investorsTranslations;
}
