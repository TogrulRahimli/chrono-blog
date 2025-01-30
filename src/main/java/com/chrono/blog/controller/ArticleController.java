package com.chrono.blog.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chrono.blog.model.dto.ArticleDTO;
import com.chrono.blog.model.dto.CreateArticleDTO;
import com.chrono.blog.model.entity.Article;
import com.chrono.blog.model.mapper.ArticleMapper;
import com.chrono.blog.service.ArticleService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Validated
@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArticleController {

    ArticleService articleService;
    ArticleMapper articleMapper;

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@Valid @RequestBody CreateArticleDTO articleDTO) {
        Article article = articleService.createArticle(articleMapper.toEntityWithoutId(articleDTO));
        return ResponseEntity.ok(articleMapper.toDTO(article));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        return ResponseEntity.ok(articleMapper.toDTO(article));
    }

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articleMapper.toDTOList(articles));
    }

    @GetMapping("/before/{date}")
    public ResponseEntity<List<ArticleDTO>> getArticlesBeforeDate(@PathVariable String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        List<Article> articles = articleService.getArticlesBeforeDate(dateTime);
        return ResponseEntity.ok(articleMapper.toDTOList(articles));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArticleDTO>> searchArticles(@RequestParam String keyword) {
        List<Article> articles = articleService.searchArticlesByKeyword(keyword);
        return ResponseEntity.ok(articleMapper.toDTOList(articles));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleDTO updatedArticleDTO) {
        Article updatedArticle = articleService.updateArticle(id, articleMapper.toEntity(updatedArticleDTO));
        return ResponseEntity.ok(articleMapper.toDTO(updatedArticle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}

