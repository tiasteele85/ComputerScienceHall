package co.grandcircus.lab26;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.grandcircus.lab26.dao.MovieDao;
import co.grandcircus.lab26.entity.Movie;

@RestController
public class MovieController {

	@Autowired
	MovieDao dao;

	@GetMapping("/movies")
	public List<Movie> getMovies(@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "title", required = false) String title) {

		if ((category == null || category.isEmpty()) & (title == null || title.isEmpty())) {
			System.out.println(category + " : " + title);
			return dao.findAll();
		} else if(title == null || title.isEmpty()){
			return dao.findByCategoryContainsIgnoreCase(category);
		}else {
			return dao.findByTitleContainsIgnoreCase(title);
		}
	}

	@GetMapping("/movies/random")
	public Movie getRandomMovie(@RequestParam(value = "category", required = false) String category) {
		Random rand = new Random();

		if (category == null || category.isEmpty()) {
			List<Movie> movies = dao.findAll();
			int choice = rand.nextInt(movies.size() - 1);
			return movies.get(choice);
		}else {
			List<Movie> movies = dao.findByCategoryContainsIgnoreCase(category);
			int choice = rand.nextInt(movies.size() - 1);
			return movies.get(choice);
		}
	}

	
	@GetMapping("/movies/random_list")
	public List<Movie> getRandomMovieList(
			@RequestParam("quantity") int quantity){
		
		Random rand = new Random();
		List<Movie> movies = dao.findAll();
		List<Movie> moviesRando = new ArrayList<>();
		
		for(int i = 0; i < quantity; i++)
		{
			moviesRando.add(movies.get(rand.nextInt(movies.size()-1)));
		}
		
		return moviesRando;
	}
	
	@GetMapping("/categories")
	public Set<String> getAllCategories(){
		List<Movie> movies = dao.findAll();
		Set<String> distinct = new TreeSet<>();
		
		for(Movie movie: movies) {
			distinct.add(movie.getCategory()) ;
		}
		
		return distinct;
	}
	
	@GetMapping("/movie/{id}")
	public Movie getMovieByID(@PathVariable("id") Long id) {
		return dao.findById(id).get();
	}
	
	
}
