package curs.BD.cursBd.repositories;

import curs.BD.cursBd.model.Warehouse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface WarehouseRepository extends CrudRepository<Warehouse, Long> {
}
