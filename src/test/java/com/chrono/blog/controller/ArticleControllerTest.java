package com.chrono.blog.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.chrono.blog.model.entity.Article;
import com.chrono.blog.repository.ArticleRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.config.location=classpath:application-test.yml")
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        articleRepository.deleteAll();
    }

    @Test
    void shouldCreateArticleSuccessfully() throws Exception {
        String articleJson = "{\"title\":\"Title\",\"content\":\"Test Content\",\"author\":\"Toghrul Rahimli\",\"published\":true}";

        mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(articleJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.content").value("Test Content"))
                .andExpect(jsonPath("$.author").value("Toghrul Rahimli"))
                .andExpect(jsonPath("$.published").value(true));
    }

    @Test
    void shouldGetAllArticles() throws Exception {
        Article article = new Article();
        article.setTitle("Title");
        article.setContent("Test Content");
        article.setAuthor("Toghrul Rahimli");
        article.setPublished(true);
        articleRepository.save(article);

        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Title"))
                .andExpect(jsonPath("$[0].content").value("Test Content"))
                .andExpect(jsonPath("$[0].author").value("Toghrul Rahimli"))
                .andExpect(jsonPath("$[0].published").value(true));
    }

    @Test
    void shouldGetArticleById() throws Exception {
        Article article = new Article();
        article.setTitle("Title");
        article.setContent("Test Content");
        article.setAuthor("Toghrul Rahimli");
        article.setPublished(true);
        Article savedArticle = articleRepository.save(article);

        mockMvc.perform(get("/api/articles/" + savedArticle.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.content").value("Test Content"))
                .andExpect(jsonPath("$.author").value("Toghrul Rahimli"))
                .andExpect(jsonPath("$.published").value(true));
    }

    @Test
    void shouldUpdateArticleSuccessfully() throws Exception {
        Article article = new Article();
        article.setTitle("Old Title");
        article.setContent("Old Content");
        article.setAuthor("Old Author");
        article.setPublished(false);
        Article savedArticle = articleRepository.save(article);

        String updatedArticleJson = "{\"title\":\"Title\",\"content\":\"Test Content\",\"author\":\"Toghrul Rahimli\",\"published\":true}";

        mockMvc.perform(put("/api/articles/" + savedArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedArticleJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.content").value("Test Content"))
                .andExpect(jsonPath("$.author").value("Toghrul Rahimli"))
                .andExpect(jsonPath("$.published").value(true));
    }

    @Test
    void shouldDeleteArticleSuccessfully() throws Exception {
        Article article = new Article();
        article.setTitle("Title");
        article.setContent("Test Content");
        article.setAuthor("Toghrul Rahimli");
        article.setPublished(true);
        Article savedArticle = articleRepository.save(article);

        mockMvc.perform(delete("/api/articles/" + savedArticle.getId()))
                .andExpect(status().isNoContent());

        Optional<Article> deletedArticle = articleRepository.findById(savedArticle.getId());
        assertFalse(deletedArticle.isPresent());
    }
}

