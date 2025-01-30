package com.chrono.blog.service;

import com.chrono.blog.exception.ArticleNotFoundException;
import com.chrono.blog.model.dto.CreateArticleDTO;
import com.chrono.blog.model.mapper.ArticleMapper;
import com.chrono.blog.model.entity.Article;
import com.chrono.blog.repository.ArticleRepository;
import com.chrono.blog.service.impl.ArticleServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleMapper articleMapper;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private CreateArticleDTO createArticleDTO;
    private Article article;

    @BeforeEach
    void setUp() {
        createArticleDTO = CreateArticleDTO.builder()
                .title("Title")
                .content("Test content.")
                .author("Toghrul Rahimli")
                .published(true)
                .build();

        article = Article.builder()
                .id(1L)
                .title("Title")
                .content("Test content")
                .author("Toghrul Rahimli")
                .published(true)
                .build();
    }

    @Test
    void shouldCreateArticleSuccessfully() {
        when(articleMapper.toEntityWithoutId(createArticleDTO)).thenReturn(article);
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        Article savedArticle = articleService.createArticle(articleMapper.toEntityWithoutId(createArticleDTO));

        assertNotNull(savedArticle);
        assertEquals("Title", savedArticle.getTitle());
        assertEquals("Test content", savedArticle.getContent());
        assertEquals("Toghrul Rahimli", savedArticle.getAuthor());
        assertTrue(savedArticle.getPublished());

        verify(articleRepository, times(1)).save(any(Article.class));
    }


    @Test
    void shouldFindArticleById() {
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        Article foundArticle = articleService.getArticleById(1L);

        assertNotNull(foundArticle);
        assertEquals("Title", foundArticle.getTitle());
        verify(articleRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenArticleNotFound() {
        when(articleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ArticleNotFoundException.class, () -> articleService.getArticleById(99L));

        verify(articleRepository, times(1)).findById(99L);
    }

    @Test
    void shouldFindAllArticles() {
        when(articleRepository.findAll()).thenReturn(List.of(article));

        List<Article> articles = articleService.getAllArticles();

        assertFalse(articles.isEmpty());
        assertEquals(1, articles.size());
        verify(articleRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoArticlesFound() {
        when(articleRepository.findAll()).thenReturn(Collections.emptyList());

        List<Article> articles = articleService.getAllArticles();

        assertTrue(articles.isEmpty());
        verify(articleRepository, times(1)).findAll();
    }

    @Test
    void shouldDeleteArticleSuccessfully() {
        when(articleRepository.existsById(1L)).thenReturn(true);
        doNothing().when(articleRepository).deleteById(1L);

        articleService.deleteArticle(1L);

        verify(articleRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingArticle() {
        when(articleRepository.existsById(99L)).thenReturn(false);

        assertThrows(ArticleNotFoundException.class, () -> articleService.deleteArticle(99L));

        verify(articleRepository, times(1)).existsById(99L);
    }

    @Test
    void shouldUpdateArticleSuccessfully() {
        Article updatedArticle = Article.builder()
                .id(1L)
                .title("Updated Title")
                .content("Updated content.")
                .author("John Doe")
                .published(true)
                .publishedAt(LocalDateTime.now())
                .build();

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        when(articleRepository.save(any(Article.class))).thenReturn(updatedArticle);

        Article result = articleService.updateArticle(1L, updatedArticle);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingArticle() {
        Article updatedArticle = Article.builder()
                .title("Updated Title")
                .content("Updated content.")
                .author("John Doe")
                .published(true)
                .build();

        when(articleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ArticleNotFoundException.class, () -> articleService.updateArticle(99L, updatedArticle));

        verify(articleRepository, times(1)).findById(99L);
    }
}

