package curs.BD.cursBd.testMessage;

import curs.BD.cursBd.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepository extends CrudRepository<Product, Long> {
//    Optional<Product> findByName(String Name);
}
