package curs.BD.cursBd.repositories;

import curs.BD.cursBd.model.Charges;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ChargesRepository extends CrudRepository<Charges, Long> {
}
