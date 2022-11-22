package com.example.controller;

import com.example.model.Article;
import com.example.model.Warehouse;
import com.example.repository.ArticleRepository;
import com.example.repository.WarehouseRepository;
import com.example.service.ArticleService;
import com.example.service.WarehouseService;
import com.example.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    private ArticleServiceImpl articleServiceImpl;

    @GetMapping("/logistics_articles")
    public String viewLogisticsArticles() {
        return "logisticsArticles";
    }

    //build create article REST API
    @GetMapping("/logistics_list_articles")
    public String viewAllArticles(Model model) {
        List<Warehouse> warehouseList = warehouseService.getAllWarehouses();
        List<Article> listArticles = articleService.getAllArticles();
        model.addAttribute("listArticles", listArticles);
        model.addAttribute("warehouseList", warehouseList);

        return "logisticsListArticles";
    }

    @GetMapping("/logistics_add_article")
    public String viewAddArticleForm(Model model) {
        List<Warehouse> warehouseList = warehouseService.getAllWarehouses();
        model.addAttribute("article", new Article());
        model.addAttribute("warehouseList", warehouseList);
        return "logisticsAddArticle";
    }

    @PostMapping("/logistics_add_article")
    public String saveArticle(Article article) {
        articleService.saveArticle(article);
        return "redirect:/logistics_add_article";
    }

    @DeleteMapping("/logistics_list_articles/delete_article/{id}")
    public String deleteArticle(@PathVariable long id) {
        articleServiceImpl.deleteArticle(id);
        return "redirect:/logistics_list_articles";
    }

    @GetMapping("/logistics_list_articles/update_article/id={id}")
    public ModelAndView viewUpdateForm(@PathVariable long id) {
        ModelAndView mav = new ModelAndView("logisticsUpdateArticle");

        Article article = articleRepository.findById(id).get();


        List<Warehouse> warehouseList = warehouseService.getAllWarehouses();
        warehouseList.removeIf(warehouse -> warehouse.getStorageRack() == article.getStorageRack());

        Warehouse selectedWarehouse = warehouseRepository.findById(article.getStorageRack()).get();

        mav.addObject("article", article);
        mav.addObject("selectedWarehouse", selectedWarehouse);
        mav.addObject("warehouseList", warehouseList);
        return mav;
    }

    @PostMapping("/logistics_list_articles/update_article/{id}")
    public String updateArticle(@PathVariable long id, Article article) {
        articleService.updateArticle(article, id);
        return "redirect:/logistics_list_articles";
    }
}

