package curs.BD.cursBd.repositories;

import curs.BD.cursBd.model.Warehouses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface WarehousesRepository extends CrudRepository<Warehouses, Long> {
}
