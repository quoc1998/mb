package com.backend.bank.dto.request;

import com.backend.bank.model.NewsTranslation;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class NewsRequestDto implements Serializable {

	private static final long serialVersionUID = 7156526077883281623L;

	private Integer id;

	private String slug;

	private int is_active;

	private byte is_sticky;

	private String author_name;

	private int viewed;

	public Date deleted_at;

	public Date created_at;

	public Date updated_at;

	private int category_news_id;

	private byte is_comment;

	private int count_comment;

	private byte news_type;

	private byte news_classify;

	private int status;

	private Integer builder_id;

	private String base_image;

	private  String miniImage;

	private List<NewsTranslation> newsTranslations;

	private List<NewsBlocksRequestDto> newsBlocks;

}
