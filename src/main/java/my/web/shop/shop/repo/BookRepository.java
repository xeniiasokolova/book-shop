package my.web.shop.shop.repo;

import my.web.shop.shop.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

}
