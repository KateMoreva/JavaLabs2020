package curs.BD.cursBd.controllers;

import curs.BD.cursBd.model.*;
import curs.BD.cursBd.repositories.SalesRepository;
import curs.BD.cursBd.repositories.WarehousesRepository;
import curs.BD.cursBd.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("warehouses/")
public class WarehousesController {
    @Autowired
    private WarehousesRepository warehousesRepository;
    @Autowired
    private SalesRepository salesRepository;

    public WarehousesController(WarehousesRepository warehousesRepository, SalesRepository salesRepository) {
        this.warehousesRepository = warehousesRepository;
        this.salesRepository = salesRepository;
    }

    @GetMapping("/all-items")
    public ResponseEntity<?> getAll() {

        Iterable<Warehouses> warehouses = warehousesRepository.findAll();

        return !warehouses.iterator().hasNext() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(warehouses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
        Optional<Warehouses> warehouses = warehousesRepository.findById(id);
        return warehouses.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(warehouses, HttpStatus.OK);
    }

    @RequestMapping(value = "/filter-amount/{amountFrom}/{amountTo}", method = RequestMethod.GET)
    public ResponseEntity<?> filterByAmount(@PathVariable("amountFrom") Integer amountFrom, @PathVariable("amountTo") Integer amountTo) {
        Iterable<Warehouses> warehouses = warehousesRepository.findAll();
        ArrayList<Warehouses> items = new ArrayList<>();
        for (Warehouses warehouse : warehouses) {
            Integer amount = warehouse.getAmount();
            if ((amount >= amountFrom) && (amount <= amountTo)) {
                items.add(warehouse);
            }
        }
        return items.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(items, HttpStatus.OK);
    }

    @RequestMapping(value = "/filter-quantity/{quantityFrom}/{quantityTo}", method = RequestMethod.GET)
    public ResponseEntity<?> filterByQuantity(@PathVariable("quantityFrom") Integer quantityFrom, @PathVariable("quantityTo") Integer quantityTo) {
        Iterable<Warehouses> warehouses = warehousesRepository.findAll();
        ArrayList<Warehouses> items = new ArrayList<>();
        for (Warehouses warehouse : warehouses) {
            Integer quantity = warehouse.getQuantity();
            if ((quantity >= quantityFrom) && (quantity <= quantityTo)) {
                items.add(warehouse);
            }
        }
        return items.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(items, HttpStatus.OK);
    }


    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> findByName(@PathVariable("name") String name) {
        Iterable<Warehouses> warehouses = warehousesRepository.findAll();
        for (Warehouses warehouse : warehouses) {
            if (warehouse.getName().equals(name)) {
                return new ResponseEntity<>(warehouse, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/warehouse-name/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> findAllByName(@PathVariable("name") String name) {
        Iterable<Warehouses> warehouses = warehousesRepository.findAll();
        ArrayList<Warehouses> results = new ArrayList<>();
        for (Warehouses warehouse : warehouses) {
            if (warehouse.getName().equals(name)) {
                results.add(warehouse);

            }
        }
        return results.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(results, HttpStatus.OK);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Warehouses warehouse) {
        warehousesRepository.save(warehouse);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Warehouses warehouseDetails) {
        if (warehousesRepository.findById(id).isPresent()) {
            Warehouses exitem = warehousesRepository.findById(id).get();
            if (warehouseDetails.getQuantity() != null) {
                exitem.setQuantity(warehouseDetails.getQuantity());
            }
            if (warehouseDetails.getAmount() != null) {
                exitem.setAmount(warehouseDetails.getAmount());
            }

            Warehouses item = warehousesRepository.save(exitem);
            return new ResponseEntity<>(warehouseDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/no/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Warehouses warehouses = warehousesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("no such element"));
        Iterable<Sales> sales = salesRepository.findAll();
        ArrayList<Sales> salesToDelete = new ArrayList<>();
        for (Sales sale : sales) {
            if ((sale.getWarehousesId().getId().equals(id))) {
                salesRepository.delete(sale);
            }
        }
        warehousesRepository.delete(warehouses);
        return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/no-such/name/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteByName(@PathVariable("name") String name) {
        Iterable<Warehouses> warehouses = warehousesRepository.findAll();
        for (Warehouses warehouse : warehouses) {
            if (warehouse.getName().equals(name)) {
                Long id = warehouse.getId();
                Iterable<Sales> sales = salesRepository.findAll();
                for (Sales sale : sales) {
                    if ((sale.getWarehousesId().getId().equals(id))) {
                        salesRepository.delete(sale);
                    }
                }
                warehousesRepository.delete(warehouse);
            }
        }
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }
}
