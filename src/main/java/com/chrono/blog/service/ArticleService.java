package com.chrono.blog.service;

import java.time.LocalDateTime;
import java.util.List;

import com.chrono.blog.model.entity.Article;

public interface ArticleService {

    Article createArticle(Article article);

    Article getArticleById(Long id);

    List<Article> getAllArticles();

    List<Article> getArticlesBeforeDate(LocalDateTime date);

    List<Article> searchArticlesByKeyword(String keyword);

    Article updateArticle(Long id, Article updatedArticle);

    void deleteArticle(Long id);
}
