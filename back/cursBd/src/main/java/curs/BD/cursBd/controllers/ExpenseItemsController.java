package curs.BD.cursBd.controllers;

import curs.BD.cursBd.model.Charges;
import curs.BD.cursBd.model.ExpenseItems;
import curs.BD.cursBd.model.Product;
import curs.BD.cursBd.repositories.ChargesRepository;
import curs.BD.cursBd.repositories.ExpenseItemsRepository;
import curs.BD.cursBd.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/expense-items")
public class ExpenseItemsController {
    @Autowired
    private ExpenseItemsRepository expenseItemsRepository;
    @Autowired
    private ChargesRepository chargesRepository;

    public ExpenseItemsController(ExpenseItemsRepository expenseItemsRepository, ChargesRepository chargesRepository) {
        this.chargesRepository = chargesRepository;
        this.expenseItemsRepository = expenseItemsRepository;
    }

    @GetMapping("/all-items")
    public ResponseEntity<?> getAll() {

        Iterable<ExpenseItems> expenseItems = expenseItemsRepository.findAll();

        return !expenseItems.iterator().hasNext() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(expenseItems, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Optional<ExpenseItems> expenseItem = expenseItemsRepository.findById(id);
        return expenseItem.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(expenseItem, HttpStatus.OK);
    }

    @RequestMapping(value = "/filter/{idFrom}/{idTo}", method = RequestMethod.GET)
    public ResponseEntity<?> findByFilter(@PathVariable("idFrom") Long amountFrom, @PathVariable("idTo") Long amountTo) {
        Iterable<ExpenseItems> expenseItems = expenseItemsRepository.findAll();
        ArrayList<ExpenseItems> items = new ArrayList<>();
        for (ExpenseItems pr : expenseItems) {
            Long id = pr.getId();
            if ((id >= amountFrom) && (id <= amountTo)) {
                items.add(pr);
            }
        }
        return items.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(items, HttpStatus.OK);
    }


    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> findByName(@PathVariable("name") String name) {
        Iterable<ExpenseItems> expenseItems = expenseItemsRepository.findAll();
        for (ExpenseItems expenseItem : expenseItems) {
            if (expenseItem.getName().equals(name)) {
                return new ResponseEntity<>(expenseItem, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody ExpenseItems expenseItem) {
        expenseItemsRepository.save(expenseItem);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ExpenseItems expenseItemDetails) throws ResourceNotFoundException {
        if (expenseItemsRepository.findById(id).isPresent()) {
            ExpenseItems exitem = expenseItemsRepository.findById(id).get();
            if (exitem.getName() != null) {
                exitem.setName(expenseItemDetails.getName());
            }
            ExpenseItems item = expenseItemsRepository.save(exitem);
            return new ResponseEntity<>(expenseItemDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/no/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        ExpenseItems expenseItems = expenseItemsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("no such element"));
        Iterable<Charges> charges = chargesRepository.findAll();
        for (Charges charge : charges) {
            if ((charge.getExpenseItemsId().getId().equals(id))) {
                chargesRepository.delete(charge);
            }
        }
        expenseItemsRepository.delete(expenseItems);
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/no-such/name/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteByName(@PathVariable("name") String name) throws ResourceNotFoundException {
        Iterable<ExpenseItems> expenseItems = expenseItemsRepository.findAll();
        for (ExpenseItems expenseItem : expenseItems) {
            if (expenseItem.getName().equals(name)) {
                Long id = expenseItem.getId();
                Iterable<Charges> charges = chargesRepository.findAll();
                for (Charges charge : charges) {
                    if ((charge.getExpenseItemsId().getId().equals(id))) {
                        chargesRepository.delete(charge);
                    }
                }
                expenseItemsRepository.delete(expenseItem);
            }
        }
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }

}
