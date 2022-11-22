package com.example.service;

import com.example.model.Article;
import com.example.model.User;
import com.example.model.Warehouse;
import com.example.repository.ArticleRepository;
import com.example.repository.UserRepository;
import com.example.service.impl.ArticleServiceImpl;
import com.example.service.impl.UserServiceImpl;
import com.example.service.impl.WarehouseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceUnitTests {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleServiceImpl articleServiceImpl;


    @Test
    void Should_Delete_Article() {
        Article article = setupArticle();

        articleServiceImpl.deleteArticle(article.getId());
        boolean articleFound = articleRepository.findById(article.getId()).isPresent();

        assertThat(articleFound).isFalse();
    }

    @Test
    void Article_Should_Be_Appropriate_For_Storage() {
        Article article = setupArticle();
        boolean isAppropriate = articleServiceImpl.checkIfArticleAppropriateForStorage(article);
        assertThat(isAppropriate).isTrue();
    }
    @Test
    void Article_Should_Not_Be_Appropriate_For_Storage() {
        Article article = setupArticle();
        article.setVolume(5001);
        boolean isAppropriate = articleServiceImpl.checkIfArticleAppropriateForStorage(article);
        assertThat(isAppropriate).isFalse();
    }

    @Test
    void Should_Get_All_Articles() {
        Article samsungHeadphones = setupArticle();
        Article ikeaDesk = setupArticle1();
        List<Article> expectedArticleList = List.of(samsungHeadphones, ikeaDesk);
        articleRepository.saveAll(expectedArticleList);

        when(articleRepository.findAll()).thenReturn(expectedArticleList);
        List<Article> actualArticleList = articleRepository.findAll();

        assertThat(actualArticleList.size()).isEqualTo(expectedArticleList.size());

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

    Article setupArticle1() {
        Warehouse warehouse = new Warehouse();
        warehouse.setMaxWeight(4000);
        warehouse.setStorageRack(2);
        warehouse.setVolume(3000);

        Article article = new Article();
        article.setId(2L);
        article.setArticleName("IKEA desk");
        article.setSupplier("Fedex");
        article.setWeight(50);
        article.setPurchasePrice(150);
        article.setVolume(1);
        article.setWarehouse(warehouse);

        return article;
    }

}
