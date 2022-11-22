package com.example.service;

import com.example.model.Article;

import java.util.List;

public interface ArticleService {

    List<Article> getAllArticles();

    Article saveArticle(Article article);

    void deleteArticle(long id);

    void updateArticle(Article article, long id);

    void updateArticleWarehouse(Article article);

    boolean checkIfArticleAppropriateForStorage(Article article);

}

