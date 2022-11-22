package com.example.service.impl;

import com.example.exception.ResourceException;
import com.example.model.Article;
import com.example.model.Warehouse;
import com.example.repository.ArticleRepository;
import com.example.service.ArticleService;
import com.example.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final WarehouseServiceImpl warehouseServiceImpl;

    private final WarehouseService warehouseService;

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article saveArticle(Article article) {
        if (checkIfArticleAppropriateForStorage(article)) {
            updateArticleWarehouse(article);
            return articleRepository.save(article);
        } else {
            throw new ResourceException(HttpStatus.INTERNAL_SERVER_ERROR, "Article Is Not Appropriate For this Storage Rack");
        }
    }

    @Override
    public void deleteArticle(long id) {
        articleRepository.delete(articleRepository.getReferenceById(id));
    }

    @Override
    public void updateArticle(Article article, long id) {
        Article existingArticle = articleRepository.findById(id).get();
        existingArticle.setWeight(article.getWeight());
        existingArticle.setVolume(article.getVolume());

        if (checkIfArticleAppropriateForStorage(existingArticle)) {
            existingArticle.setArticleName(article.getArticleName());
            existingArticle.setSupplier(article.getSupplier());
            existingArticle.setPurchasePrice(article.getPurchasePrice());
            existingArticle.setWarehouse(article.getWarehouse());
            saveArticle(existingArticle);
            updateArticleWarehouse(existingArticle);
        } else {
            throw new ResourceException(HttpStatus.INTERNAL_SERVER_ERROR, "Article Is Not Appropriate For this Storage Rack");
        }
    }

    @Override
    public void updateArticleWarehouse(Article article) {
        Warehouse articleWarehouse = article.getWarehouse();
        articleWarehouse.setVolume(articleWarehouse.getVolume() - article.getVolume());
        articleWarehouse.setMaxWeight(articleWarehouse.getMaxWeight() - article.getWeight());
        warehouseService.saveWarehouse(articleWarehouse);
    }

    // WE CHECK HERE IF NEW WEIGHT AND VOLUME IS APPROPRIATE FOR SPECIFIC STORAGE RACK WHICH HAS MAX WEIGHT AND MAX VOLUME
    @Override
    public boolean checkIfArticleAppropriateForStorage(Article article) {
        boolean articleAppropriate = false;
        Warehouse warehouse = article.getWarehouse();
        double newWarehouseVolume = warehouse.getVolume() - article.getVolume();
        double newWarehouseWeight = warehouse.getMaxWeight() - article.getWeight();

        if (newWarehouseVolume >= 0 && newWarehouseWeight >= 0) {
            articleAppropriate = true;
        }
        return articleAppropriate;
    }
}
