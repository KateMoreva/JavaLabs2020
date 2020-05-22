package curs.BD.cursBd.controllers;

import curs.BD.cursBd.model.*;
import curs.BD.cursBd.repositories.SalesRepository;
import curs.BD.cursBd.repositories.WarehousesRepository;
import curs.BD.cursBd.exceptions.ResourceNotFoundException;
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
        if (!salesArrayList.isEmpty()) {
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
        if (!salesArrayList.isEmpty()) {
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
                }
            }
        }
        if (salesArrayList.isEmpty()) {
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
                }
            }
        }
        if (salesArrayList.isEmpty()) {
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
        if (salesArrayList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(salesArrayList, HttpStatus.OK);
    }

    @GetMapping("/item-name/{name}/timeFrom/{timeFrom}/timeTo/{timeTo}")
    public ResponseEntity<?> getByDateAndNameInfo(@PathVariable(value = "name") String name,
                                                  @PathVariable(value = "timeFrom") Timestamp timeFrom,
                                                  @PathVariable(value = "timeTo") Timestamp timeTo) {
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

    @RequestMapping(value = "/by-all-info/amount/{amount}/quantity/{quantity}/date/{date}/item-name/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> findByAllInfo(@PathVariable(value = "amount") Integer amount,
                                           @PathVariable(value = "quantity") Integer quantity,
                                           @PathVariable(value = "date") Timestamp date,
                                           @PathVariable(value = "name") String name) {
        Iterable<Sales> sales = salesRepository.findAll();
        ArrayList<Sales> salesArrayList = new ArrayList<>();
        for (Sales sale : sales) {
            if ((sale.getAmount().equals(amount)) && (sale.getQuantity().equals(quantity)) && (sale.getSaleDate().toString().equals(date.toString())) &&
                    sale.getWarehousesId().getName().equals(name)) {
                salesArrayList.add(sale);
            }
        }
        if (salesArrayList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(salesArrayList, HttpStatus.OK);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Sales salesbody) {
        Sales sale = new Sales();
        sale.setWarehousesId(salesbody.getWarehousesId());
        Warehouses warehouses = salesbody.getWarehousesId();
        if ((warehouses.getQuantity() != null) && (salesbody.getQuantity() != null)) {
            Integer prevQuantity = salesbody.getWarehousesId().getQuantity();
            Integer delta = salesbody.getQuantity();
            Integer newQuantity = prevQuantity - delta;
            warehouses.setQuantity(newQuantity);
            warehousesRepository.save(sale.getWarehousesId());
            sale.setQuantity(salesbody.getQuantity());

        }
        if (salesbody.getWarehousesId().getAmount() != null) {
            sale.setAmount(salesbody.getWarehousesId().getAmount());
        }
        if (salesbody.getSaleDate() != null) {
            sale.setSaleDate(salesbody.getSaleDate());
        }
        salesRepository.save(sale);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody Sales chargeDetails) {
        if (salesRepository.findById(id).isPresent()) {
            Warehouses warehouses = chargeDetails.getWarehousesId();
            Sales excharge = salesRepository.findById(id).get();
            if ((warehouses.getQuantity() != null) && (chargeDetails.getQuantity() != null)) {
                Integer predelta = excharge.getQuantity();
                Integer prevQuantity = warehouses.getQuantity();
                Integer delta = chargeDetails.getQuantity();
                Integer newdel = delta - predelta;
                Integer newQuantity = prevQuantity - newdel;
                warehouses.setQuantity(newQuantity);
                warehousesRepository.save(warehouses);
                excharge.setQuantity(chargeDetails.getQuantity());

            }
            if (chargeDetails.getAmount() != null) {
                excharge.setAmount(chargeDetails.getAmount());
            }
            if (chargeDetails.getSaleDate() != null) {
                excharge.setSaleDate(chargeDetails.getSaleDate());
            }

            Sales newCharge = salesRepository.save(excharge);
            return new ResponseEntity<>(chargeDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/no-such/amount/{amount}/quantity/{quantity}/date/{date}/item-name/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "amount") Integer amount,
                                    @PathVariable(value = "quantity") Integer quantity,
                                    @PathVariable(value = "date") Timestamp date,
                                    @PathVariable(value = "name") String name) {

        ArrayList<Sales> chargesToDelete = findElementsByAllInfo(amount, quantity, date, name);
        if (!chargesToDelete.isEmpty()) {
            for (Sales charges1 : chargesToDelete) {
                Warehouses warehouses = charges1.getWarehousesId();
                Integer prevQuantity = charges1.getWarehousesId().getQuantity();
                Integer delta = charges1.getQuantity();
                Integer newQuantity = prevQuantity + delta;
                warehouses.setQuantity(newQuantity);
                warehousesRepository.save(warehouses);
                salesRepository.delete(charges1);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ArrayList<Sales> findElementsByAllInfo(@PathVariable("amount") Integer amount, @PathVariable("quantity") Integer quantity, @PathVariable("date") Timestamp date, @PathVariable("name") String name) {
        Iterable<Sales> charges = salesRepository.findAll();
        ArrayList<Sales> chargesToDelete = new ArrayList<>();
        for (Sales charge : charges) {
            if ((charge.getAmount().equals(amount)) && (charge.getQuantity().equals(quantity)) && (charge.getSaleDate().toString().equals(date.toString())) &&
                    charge.getWarehousesId().getName().equals(name)) {
                chargesToDelete.add(charge);
            }
        }
        return chargesToDelete;
    }

}
