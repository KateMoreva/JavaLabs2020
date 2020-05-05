package curs.BD.cursBd.testMessage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ChargesRepository extends CrudRepository<Charges, Long> {
}
