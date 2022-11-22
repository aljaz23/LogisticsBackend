package com.example.repository;


import com.example.model.Article;
import com.example.model.Warehouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleRepositoryIntegrationsTests {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    void Should_Save_Article() {
        Article article = setupArticle();

        Article savedArticle = articleRepository.save(article);

        Article foundArticle = testEntityManager.find(Article.class, savedArticle.getId());
        assertThat(foundArticle).isNotNull();
    }

    @Test
    void Should_Delete_Article() {
        Article article = setupArticle();
        Article savedArticle = articleRepository.save(article);
        articleRepository.deleteById(savedArticle.getId());

        Article foundArticle = testEntityManager.find(Article.class, savedArticle.getId());
        assertThat(foundArticle).isNull();
    }

    Article setupArticle() {
        Warehouse warehouse = new Warehouse();
        warehouse.setMaxWeight(10000);
        warehouse.setStorageRack(1);
        warehouse.setVolume(5000);

        Article article = new Article();
        article.setId(1L);
        article.setArticleName("Samsung Headphones G2");
        article.setSupplier("Samsung");
        article.setWeight(1);
        article.setPurchasePrice(55);
        article.setVolume(0.5);
        article.setWarehouse(warehouse);

        return article;
    }
}
