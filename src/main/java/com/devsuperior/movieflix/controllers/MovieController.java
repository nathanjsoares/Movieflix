package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping
    public ResponseEntity<Page<MovieDetailsDTO>> findAll(
            @RequestParam(value = "title",defaultValue = "") String title,
            @RequestParam(value = "categoryId",defaultValue = "0") String categoryId,
            Pageable pageable) {
        Page<MovieDetailsDTO> list = service.findAllPaged(title,categoryId,pageable);
        return ResponseEntity.ok().body(list);
    }
}
