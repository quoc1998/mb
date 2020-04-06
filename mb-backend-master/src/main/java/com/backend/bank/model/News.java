package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@Data
@Entity
@Table(name = "news")
@NoArgsConstructor
public class News implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "author_name", nullable = true)
	private String author_name;

	@Column(name = "is_sticky", nullable = true)
	private Boolean is_sticky;

	@Column(name = "mini_image")
	private  String miniImage;

	@Column(name = "created_by_user_id")
	private  Integer createdByUserId;

	@Column(name = "approved_by_user_id")
	private  Integer approvedByUserId;

	@Column(name = "edit_by_user_id")
	private  Integer editByUserId;

	@Column(name = "created_at", nullable = true)
	private Calendar createdAt;


	@Column(name = "updated_at", nullable = true)
	private Calendar updatedAt;

	@ManyToMany
	@JsonBackReference
	@JoinTable(
			name = "new_category",
			joinColumns = @JoinColumn(name = "new_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id")
	)
	private List<Category> categories;

	@Column(name= "base_image", nullable = true)
	private  String base_image;


	@OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
	private List<NewsTranslation> newsTranslations ;

	@OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
	List<NewsBlocks> newsBlocks;



}
