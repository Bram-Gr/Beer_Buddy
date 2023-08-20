package com.techelevator.controller;

import com.techelevator.dao.NewsDao;
import com.techelevator.model.News;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@CrossOrigin
public class NewsController {
    private NewsDao newsDao;

    public NewsController(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @RequestMapping(path="/news", method = RequestMethod.GET)
    public List<News> getAllNews() {
        return newsDao.getAllNews();
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="/news", method = RequestMethod.POST)
    public boolean createNews(@PathVariable int breweriesId, @RequestParam String newsDescription, @RequestParam Date newsDate) {
        return newsDao.createNews(breweriesId, newsDescription, newsDate);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @RequestMapping(path="/news/{newsId}", method = RequestMethod.DELETE)
    public int deleteNewsById(@Valid @PathVariable int newsId) {
        return newsDao.deleteNewsById(newsId);
    }
}

