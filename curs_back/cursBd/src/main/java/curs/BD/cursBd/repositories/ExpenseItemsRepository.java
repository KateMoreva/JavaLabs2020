package curs.BD.cursBd.repositories;

import curs.BD.cursBd.model.ExpenseItems;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ExpenseItemsRepository extends CrudRepository<ExpenseItems, Long> {
}
