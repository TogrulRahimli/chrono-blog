package com.chrono.blog.service.impl;

import com.chrono.blog.exception.ArticleNotFoundException;
import com.chrono.blog.model.entity.Article;
import com.chrono.blog.repository.ArticleRepository;
import com.chrono.blog.service.ArticleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArticleServiceImpl implements ArticleService {

    ArticleRepository articleRepository;

    @Override
    @Transactional
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found with id: " + id));
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> getArticlesBeforeDate(LocalDateTime date) {
        return articleRepository.findByPublishedAtBefore(date);
    }

    @Override
    public List<Article> searchArticlesByKeyword(String keyword) {
        return articleRepository.searchByKeyword(keyword);
    }

    @Override
    @Transactional
    public Article updateArticle(Long id, Article updatedArticle) {
        return articleRepository.findById(id)
                .map(existingArticle -> {
                    existingArticle.setTitle(updatedArticle.getTitle());
                    existingArticle.setContent(updatedArticle.getContent());
                    existingArticle.setAuthor(updatedArticle.getAuthor());
                    existingArticle.setPublished(updatedArticle.getPublished());
                    return articleRepository.save(existingArticle);
                })
                .orElseThrow(() -> new ArticleNotFoundException("Article not found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteArticle(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new ArticleNotFoundException("Article not found with id: " + id);
        }
        articleRepository.deleteById(id);
    }
}
