package curs.BD.cursBd.repositories;

import curs.BD.cursBd.model.*;
import curs.BD.cursBd.testTable.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private WarehousesRepository warehousesRepository;


    public SalesController(SalesRepository salesRepository, WarehousesRepository warehousesRepository) {
        this.salesRepository = salesRepository;
        this.warehousesRepository = warehousesRepository;
    }
//    private ResponseEntity<?> find(Boolean condition) {
//        Iterable<Sales> sales = salesRepository.findAll();
//        ArrayList<Sales> salesArrayList = new ArrayList<>();
//        for (Sales sale : sales) {
//            if (condition) {
//                salesArrayList.add(sale);
//            }
//        }
//        if (salesArrayList.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(salesArrayList, HttpStatus.OK);
//    }

    @GetMapping("/all-items")
    public ResponseEntity<?> getAll() {
        Iterable<Sales> sales = salesRepository.findAll();
        return !sales.iterator().hasNext() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Optional<Sales> sale = salesRepository.findById(id);
        return sale.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
        new ResponseEntity<>(sale, HttpStatus.OK);
    }

    @RequestMapping(value = "/by-warehouse/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findWarehouseById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Iterable<Sales> sales = salesRepository.findAll();
        ArrayList<Sales> salesArrayList = new ArrayList<>();
        for (Sales sale : sales) {
            if ((sale.getWarehousesId().getId().equals(id))) {
                salesArrayList.add(sale);
            }
        }
        if (!salesArrayList.isEmpty()){
            return new ResponseEntity<>(salesArrayList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "/by-warehouse-name/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> findWarehouseByName(@PathVariable(value = "name") String name) {
        Iterable<Sales> sales = salesRepository.findAll();
        ArrayList<Sales> salesArrayList = new ArrayList<>();
        for (Sales sale : sales) {
            if ((sale.getWarehousesId().getName().equals(name))) {
                salesArrayList.add(sale);
            }
        }
        if (!salesArrayList.isEmpty()){
            return new ResponseEntity<>(salesArrayList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/date/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> findByName(@PathVariable(value = "date") Timestamp date) throws ResourceNotFoundException {
        Iterable<Sales> sales = salesRepository.findAll();
        ArrayList<Sales> salesArrayList = new ArrayList<>();
        for (Sales sale : sales) {
            if (sale.getSaleDate() != null) {
                String str1 = sale.getSaleDate().toString();
                if (str1.equals(date.toString())) {
                    salesArrayList.add(sale);
                }}
        }
        if (salesArrayList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(salesArrayList, HttpStatus.OK);
    }
    @RequestMapping(value = "/date-filter/dateFrom/{dateFrom}/dateTo/{dateTo}", method = RequestMethod.GET)
    public ResponseEntity<?> findByDateFilter(@PathVariable(value = "dateFrom") Timestamp dateFrom, @PathVariable(value = "dateTo") Timestamp dateTo) {
        Iterable<Sales> charges = salesRepository.findAll();
        ArrayList<Sales> salesArrayList = new ArrayList<>();
        for (Sales sale : charges) {
            if (sale.getSaleDate() != null) {
                if ((sale.getSaleDate().compareTo(dateFrom) >= 0) && (sale.getSaleDate().compareTo(dateTo) <= 0)) {
                    salesArrayList.add(sale);
                }}
        }
        if (salesArrayList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(salesArrayList, HttpStatus.OK);
    }

    @RequestMapping(value = "/amount/{amount}", method = RequestMethod.GET)
    public ResponseEntity<?> findByAmount(@PathVariable(value = "amount") Integer amount) {
        Iterable<Sales> sales = salesRepository.findAll();
        ArrayList<Sales> salesArrayList = new ArrayList<>();
        for (Sales sale : sales) {
            if (sale.getAmount().equals(amount)) {
                salesArrayList.add(sale);
            }
        }
        if (salesArrayList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(salesArrayList, HttpStatus.OK);
    }

    @GetMapping("/item-name/{name}/timeFrom/{timeFrom}/timeTo/{timeTo}")
    public ResponseEntity<?> getByDateAndNameInfo(@PathVariable(value = "name") String name,
                                                  @PathVariable(value = "timeFrom") Timestamp timeFrom,
                                                  @PathVariable(value = "timeTo") Timestamp timeTo){
        Iterable<Sales> sales = salesRepository.findAll();
        ArrayList<Sales> salesArrayList = new ArrayList<>();
        for (Sales sale : sales) {
            if (sale.getWarehousesId().getName().equals(name) &&
                    (sale.getSaleDate().compareTo(timeFrom) >= 0) && (sale.getSaleDate().compareTo(timeTo) <= 0)) {
                salesArrayList.add(sale);
            }
        }
        if (salesArrayList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(salesArrayList, HttpStatus.OK);
    }

    @RequestMapping(value = "/by-all-info/amount/{amount}/quantity/{quantity}/date/{date}/item-name/{name}", method = RequestMethod.GET )
    public ResponseEntity<?> findByAllInfo(@PathVariable(value = "amount") Integer amount,
                                           @PathVariable(value = "quantity") Integer quantity,
                                           @PathVariable(value = "date") Timestamp date,
                                           @PathVariable(value = "name") String name) {
        Iterable<Sales> sales = salesRepository.findAll();
        ArrayList<Sales> salesArrayList = new ArrayList<>();
        for (Sales sale : sales) {
            if ((sale.getAmount().equals(amount)) && (sale.getQuantity().equals(quantity))&& (sale.getSaleDate().toString().equals(date.toString())) &&
                    sale.getWarehousesId().getName().equals(name))
            {
                salesArrayList.add(sale);
            }
        }
        if (salesArrayList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(salesArrayList, HttpStatus.OK);
    }

    @RequestMapping(value = "/sales", method = RequestMethod.POST )
    public ResponseEntity<?> create(@RequestBody SalesBody salesbody) {
       Sales sale = new Sales();
       if (salesbody.getWarehouseId() != null) {
           Optional<Warehouses> warehouse = warehousesRepository.findById(salesbody.getWarehouseId());
           if (warehouse.isPresent()) {
               sale.setWarehousesId(warehouse.get());
               if (salesbody.getSaleDate() != null) {
                   sale.setSaleDate(salesbody.getSaleDate());
               }
               if (salesbody.getQuantity() != null) {
                   sale.setQuantity(salesbody.getQuantity());
               }
               if (salesbody.getAmount() != null) {
                   sale.setAmount(salesbody.getAmount());
               }
           }
           salesRepository.save(sale);
           return new ResponseEntity<>(HttpStatus.CREATED);
       }
        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }
}
