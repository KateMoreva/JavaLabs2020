package curs.BD.cursBd.repositories;

import curs.BD.cursBd.model.Product;
import curs.BD.cursBd.model.Sales;
import curs.BD.cursBd.testMessage.ResourceNotFoundException;
import curs.BD.cursBd.model.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class SalesController {
    @Autowired
    private SalesRepository salesRepository;

    public SalesController(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @GetMapping("/sales")
    public Iterable<Sales> getAll() {
        return salesRepository.findAll();
    }

    @GetMapping("/sales/{id}")
    public Optional<Sales> getById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Sales sales = salesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found"));
        return salesRepository.findById(id);
    }

//    @RequestMapping(value = "/sales/{id}/warehouse", method = RequestMethod.GET)
//    public Warehouse findWarehouseById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
//        Sales sales = salesRepository.findById(1L)
//                .orElseThrow(() -> new ResourceNotFoundException("fgf"));
//        Warehouse warehouse = new Warehouse();
//        warehouse.setId(sales.getWarehouseId().);
//        warehouse.setName(sales.getWarehouseId().getName());
//        warehouse.setAmount(sales.getWarehouseId().getAmount());
//        warehouse.setQuantity(sales.getWarehouseId().getQuantity());
//        return warehouse;
//    }

    @RequestMapping(value = "/sales/date/{date}", method = RequestMethod.GET)
    public Optional<Sales> findByName(@PathVariable(value = "date") Timestamp date) throws ResourceNotFoundException {
        Iterable<Sales> sales = salesRepository.findAll();
        for (Sales sale : sales) {
            if (sale.getSaleDate().equals(date)) {
                Long id = sale.getId();
                return salesRepository.findById(id);
            }
        }
        Sales sales1 = salesRepository.findById(22L)
                .orElseThrow(() -> new ResourceNotFoundException("no"));
        return salesRepository.findById(22L);
    }

    @RequestMapping(value = "/sales", method = RequestMethod.POST )
    public ResponseEntity<?> create(@RequestBody Sales sales) {
        salesRepository.save(sales);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }
}
