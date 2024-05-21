package com.patulus.becomeSpringDeveloper.controller;

import com.patulus.becomeSpringDeveloper.domain.Article;
import com.patulus.becomeSpringDeveloper.dto.ArticleListViewResponse;
import com.patulus.becomeSpringDeveloper.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();

        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(Model model, @PathVariable(name = "id") long id) {
        Article article = blogService.findById(id);

        model.addAttribute("article", article);

        return "article";
    }

}
