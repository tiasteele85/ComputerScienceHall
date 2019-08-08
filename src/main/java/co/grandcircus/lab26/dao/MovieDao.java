package co.grandcircus.lab26.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.grandcircus.lab26.entity.Movie;


public interface MovieDao extends JpaRepository<Movie, Long>{

	List<Movie> findByTitleContainsIgnoreCase(String title);
	
	List<Movie> findByCategoryContainsIgnoreCase(String category);
	
	List<Movie> findCategoryDistinctBy();
	
	
	
}
