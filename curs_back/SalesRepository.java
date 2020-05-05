package curs.BD.cursBd.testMessage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@Component
@RepositoryRestResource
public interface SalesRepository extends CrudRepository<Sales, Long> {
}
