package curs.BD.cursBd.repositories;

import curs.BD.cursBd.model.Sales;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface SalesRepository extends CrudRepository<Sales, Long> {
}
