package curs.BD.cursBd.repositories;

import curs.BD.cursBd.model.ExpenseItems;
import curs.BD.cursBd.model.Product;
import curs.BD.cursBd.testMessage.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageTranscoder;
import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/expense-items")
public class ExpenseItemsController {
    @Autowired
    private ExpenseItemsRepository expenseItemsRepository;
    public ExpenseItemsController(ExpenseItemsRepository expenseItemsRepository) {
        this.expenseItemsRepository = expenseItemsRepository;
    }

    @GetMapping("/all-items")
    public Iterable<ExpenseItems> getAll() {
        return expenseItemsRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ExpenseItems> getById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        ExpenseItems expenseItem = expenseItemsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("no"));
        return expenseItemsRepository.findById(id);
    }

    @RequestMapping(value = "/filter/{idFrom}/{idTo}", method = RequestMethod.GET)
    public Iterable<ExpenseItems> findByFilter(@PathVariable ("idFrom") Integer amountFrom, @PathVariable ("idTo") Integer amountTo) {
        Iterable<ExpenseItems> products = expenseItemsRepository.findAll();
        ArrayList<ExpenseItems> foundProducts = new ArrayList<>();
        for (ExpenseItems pr : products) {
            if ((pr.getId() >= amountFrom) && (pr.getId() <= amountTo)) {
                foundProducts.add(pr);
            }
        }
        return foundProducts;
    }


    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ExpenseItems findByName(@PathVariable("name") String name) throws ResourceNotFoundException {
        Iterable<ExpenseItems> expenseItems = expenseItemsRepository.findAll();
        for (ExpenseItems expenseItem : expenseItems) {
            if (expenseItem.getName().equals(name)) {
                return expenseItem;
            }
        }
        throw new ResourceNotFoundException("no");
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST )
    public ResponseEntity<?> create(@RequestBody ExpenseItems expenseItem) {
        expenseItemsRepository.save(expenseItem);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT )
    public ResponseEntity<ExpenseItems> update(@PathVariable("id") Long id, @RequestBody ExpenseItems expenseItemDetails) throws ResourceNotFoundException {
        if (expenseItemsRepository.findById(id).isPresent()){
            ExpenseItems exitem = expenseItemsRepository.findById(id).get();
            if (exitem.getName() != null) {
               exitem.setName(expenseItemDetails.getName());
            }
            ExpenseItems item = expenseItemsRepository.save(exitem);
        }
        return new ResponseEntity<ExpenseItems>(expenseItemDetails, HttpStatus.OK);
    }
    @RequestMapping(value = "/no/{id}", method = RequestMethod.DELETE )
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
       ExpenseItems expenseItems = expenseItemsRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("wwwwhat"));
       expenseItemsRepository.delete(expenseItems);
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "/no-such/name/{name}", method = RequestMethod.DELETE )
    public ResponseEntity<?> deleteByName(@PathVariable("name") String name) throws ResourceNotFoundException{
        Iterable<ExpenseItems> expenseItems = expenseItemsRepository.findAll();
        for (ExpenseItems expenseItem : expenseItems) {
            if (expenseItem.getName().equals(name)) {
                expenseItemsRepository.delete(expenseItem);
            }
        }
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }

}
