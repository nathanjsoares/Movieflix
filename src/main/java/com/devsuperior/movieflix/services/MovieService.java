package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private GenreRepository genreRepository;

    public Page<MovieCardDTO> findAllPaged(Pageable pageable) {
        Page<Movie> list = repository.findAll(pageable);
        return list.map(x -> new MovieCardDTO(x));
    }

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id) {
        Movie movie = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new MovieDetailsDTO(movie);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> findByMovieId(Long id) {
        List<Review> result = reviewRepository.findByMovieId(id);
        return result.stream().map(ReviewDTO::new).toList();
    }

    private void copyDtoToEntity(MovieDetailsDTO dto, Movie entity) {
        entity.setTitle(dto.getTitle());
        entity.setSubTitle(dto.getSubTitle());
        entity.setYear(dto.getYear());
        entity.setImgUrl(dto.getImgUrl());
        entity.setSynopsis(dto.getSynopsis());

        Genre genre = genreRepository.getReferenceById(dto.getGenre().getId());
        entity.setGenre(genre);
    }
}
