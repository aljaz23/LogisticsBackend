package com.example.controller;

import com.example.model.Article;
import com.example.model.Warehouse;
import com.example.service.impl.ArticleServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArticleController.class)
public class ArticleControllerIntegrationTests {

    @MockBean
    private ArticleServiceImpl articleServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void Should_Status_For_Article_Save_Be_Ok() throws Exception {
        Warehouse warehouse = new Warehouse();
        warehouse.setVolume(5000);
        warehouse.setStorageRack(1);
        warehouse.setMaxWeight(2500);
        Article article = new Article(1l,"Samsung Headphones G301","Fedex",1,0.2,50,warehouse);

        when(articleServiceImpl.saveArticle(eq(article))).thenReturn(article);
        mockMvc.perform(post("/logistics_add_article")).andExpect(status().isOk());


    }

}
