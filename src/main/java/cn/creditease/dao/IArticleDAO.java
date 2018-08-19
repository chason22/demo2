package cn.creditease.dao;

import cn.creditease.entity.Article;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IArticleDAO {
    List<Article> getAllArticles();
    Article getArticleById(int articleId);
    void addArticle(Article article);
    void updateArticle(Article article);
    void deleteArticle(int articleId);
    boolean articleExists(String title, String category);
}
