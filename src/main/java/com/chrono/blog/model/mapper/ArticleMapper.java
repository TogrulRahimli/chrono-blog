package com.chrono.blog.model.mapper;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chrono.blog.model.dto.ArticleDTO;
import com.chrono.blog.model.dto.CreateArticleDTO;
import com.chrono.blog.model.entity.Article;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mapping(source = "publishedAt", target = "publishedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    ArticleDTO toDTO(Article article);

    @Mapping(source = "publishedAt", target = "publishedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Article toEntity(ArticleDTO articleDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    Article toEntityWithoutId(CreateArticleDTO articleDTO);

    List<ArticleDTO> toDTOList(List<Article> articles);
}

