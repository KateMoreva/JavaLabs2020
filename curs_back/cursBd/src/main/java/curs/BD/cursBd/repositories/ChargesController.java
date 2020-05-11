package curs.BD.cursBd.repositories;

import curs.BD.cursBd.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/charges")
public class ChargesController {
    @Autowired
    private ChargesRepository chargesRepository;
    @Autowired
    private ExpenseItemsRepository expenseItemsRepository;

    public ChargesController(ChargesRepository chargesRepository, ExpenseItemsRepository expenseItemsRepository) {
        this.chargesRepository = chargesRepository;
        this.expenseItemsRepository = expenseItemsRepository;
    }

    @GetMapping("/all-items")
    public ResponseEntity<?> getAll() {
        Iterable<Charges> charges = chargesRepository.findAll();

        return !charges.iterator().hasNext() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(charges, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
        Optional<Charges> charge = chargesRepository.findById(id);
        return charge.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(charge, HttpStatus.OK);
//        Iterable<Charges> charges = chargesRepository.findAll();
//        Charges charge = new Charges();
//        for (Charges charg : charges) {
//            if ((charg.getId().equals(id))) {
//                new ResponseEntity<>(charg, HttpStatus.OK);
//            }
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/by-expense-item/{id}")
    public ResponseEntity<ArrayList<Charges>> getByExpenseItem(@PathVariable(value = "id") Long id) {
        Iterable<Charges> charges = chargesRepository.findAll();
        ArrayList<Charges> chargesToDelete = new ArrayList<>();
        for (Charges charge : charges) {
            if ((charge.getExpenseItemsId().getId().equals(id))) {
                chargesToDelete.add(charge);
            }
        }
        if (!chargesToDelete.isEmpty()) {
            return new ResponseEntity<>(chargesToDelete, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/expense-item-name/{name}")
    public ResponseEntity<ArrayList<Charges>> getByExpenseItemName(@PathVariable(value = "name") String name) {
        Iterable<Charges> charges = chargesRepository.findAll();
        ArrayList<Charges> chargesToDelete = new ArrayList<>();
        for (Charges charge : charges) {
            if ((charge.getExpenseItemsId().getName().equals(name))) {
                chargesToDelete.add(charge);
            }
        }
        if (!chargesToDelete.isEmpty()) {
            return new ResponseEntity<>(chargesToDelete, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST )
    public ResponseEntity<?> create(@RequestBody ChargesBody chargeBody) {
        Charges charge = new Charges();
        if (chargeBody.getExpenseItemsId() != null) {
            Optional<ExpenseItems> expenseItem = expenseItemsRepository.findById(chargeBody.getExpenseItemsId());
            if (expenseItem.isPresent()) {
                charge.setExpenseItemsId(expenseItem.get());
                if (chargeBody.getChargeDate() != null) {
                    charge.setChargeDate(chargeBody.getChargeDate());
                }
                if (chargeBody.getAmount() != null) {
                    charge.setAmount(chargeBody.getAmount());
                }
        }
            chargesRepository.save(charge);
            return new ResponseEntity<String>(HttpStatus.CREATED);
        }
        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

    @RequestMapping(value = "/date/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> findByDate(@PathVariable(value = "date") Timestamp date) {
        Iterable<Charges> charges = chargesRepository.findAll();
        ArrayList<Charges> chargesToDelete = new ArrayList<>();
        for (Charges charge : charges) {
            if (charge.getChargeDate() != null) {
            String str1 = charge.getChargeDate().toString();
            if (str1.equals(date.toString())) {
                chargesToDelete.add(charge);
            }}
        }
        if (chargesToDelete.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(chargesToDelete, HttpStatus.OK);
    }
    @RequestMapping(value = "/date-filter/dateFrom/{dateFrom}/dateTo/{dateTo}", method = RequestMethod.GET)
    public ResponseEntity<?> findByDateFilter(@PathVariable(value = "dateFrom") Timestamp dateFrom, @PathVariable(value = "dateTo") Timestamp dateTo) {
        Iterable<Charges> charges = chargesRepository.findAll();
        ArrayList<Charges> chargesToDelete = new ArrayList<>();
        for (Charges charge : charges) {
            if (charge.getChargeDate() != null) {
                if ((charge.getChargeDate().compareTo(dateFrom) >= 0) && (charge.getChargeDate().compareTo(dateTo) <= 0)) {
                    chargesToDelete.add(charge);
                }}
        }
        if (chargesToDelete.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(chargesToDelete, HttpStatus.OK);
    }


    @RequestMapping(value = "/amount/{amount}", method = RequestMethod.GET)
    public ResponseEntity<?> findByAmount(@PathVariable(value = "amount") Integer amount) {
        Iterable<Charges> charges = chargesRepository.findAll();
        ArrayList<Charges> chargesToDelete = new ArrayList<>();
        for (Charges charge : charges) {
            if (charge.getAmount().equals(amount)) {
                chargesToDelete.add(charge);
            }
        }
        if (chargesToDelete.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(chargesToDelete, HttpStatus.OK);
    }

    @GetMapping("/item-name/{name}/timeFrom/{timeFrom}/timeTo/{timeTo}")
    public ResponseEntity<?> getByDateAndNameInfo(@PathVariable(value = "name") String name,
                                                  @PathVariable(value = "timeFrom") Timestamp timeFrom,
                                                  @PathVariable(value = "timeTo") Timestamp timeTo){
            Iterable<Charges> charges = chargesRepository.findAll();
            ArrayList<Charges> chargesToDelete = new ArrayList<>();
            for (Charges charge : charges) {
                if (charge.getExpenseItemsId().getName().equals(name) &&
                        (charge.getChargeDate().compareTo(timeFrom) >= 0) && (charge.getChargeDate().compareTo(timeTo) <= 0)) {
                    chargesToDelete.add(charge);
                }
            }
            if (chargesToDelete.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(chargesToDelete, HttpStatus.OK);
        }
    @RequestMapping(value = "/by-all-info/amount/{amount}/date/{date}/item-name/{name}", method = RequestMethod.GET )
    public ResponseEntity<?> findByAllInfo(@PathVariable(value = "amount") Integer amount,
                                          @PathVariable(value = "date") Timestamp date,
                                          @PathVariable(value = "name") String name) {
        ArrayList<Charges> chargesToDelete = findElementsByAllInfo(amount, date, name);

        if (chargesToDelete.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(chargesToDelete, HttpStatus.OK);
    }

//    @RequestMapping(value = "/new", method = RequestMethod.POST )
//    public ResponseEntity<?> create(@RequestBody ChargesBody chargeBody) {
//        Charges charge = new Charges();
//        if (chargeBody.getExpenseItemsId() != null) {
//            Optional<ExpenseItems> expenseItem = expenseItemsRepository.findById(chargeBody.getExpenseItemsId());
//            if (expenseItem.isPresent()) {
//                charge.setExpenseItemsId(expenseItem.get());
//                if (chargeBody.getChargeDate() != null) {
//                    charge.setChargeDate(chargeBody.getChargeDate());
//                }
//                if (chargeBody.getAmount() != null) {
//                    charge.setAmount(chargeBody.getAmount());
//                }
//            }
//            chargesRepository.save(charge);
//            return new ResponseEntity<String>(HttpStatus.CREATED);
//        }
//        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
//    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT )
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody ChargesBody chargeDetails) {
   //        chargesRepository.findAllById(chargesIdToUpdate).iterator().hasNext()) {
//        if (chargeDetails.getExpenseItemsId() != null) {
//            Optional<ExpenseItems> expenseItem = expenseItemsRepository.findById(chargeDetails.getExpenseItemsId());
//            if (expenseItem.isPresent()) {
////                charge.setExpenseItemsId(expenseItem.get());
        if   (chargesRepository.findById(id).isPresent()){
                    Charges excharge = chargesRepository.findById(id).get();
                        if (chargeDetails.getAmount() != null) {
                            excharge.setAmount(chargeDetails.getAmount());
                        }
                        if (chargeDetails.getChargeDate() != null) {
                            excharge.setChargeDate(chargeDetails.getChargeDate());
                        }

                        Charges newCharge = chargesRepository.save(excharge);
            return new ResponseEntity<>(chargeDetails, HttpStatus.OK);
                    }
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
    @RequestMapping(value = "/no-such/amount/{amount}/date/{date}/item-name/{name}", method = RequestMethod.DELETE )
    public ResponseEntity<?> delete(@PathVariable(value = "amount") Integer amount,
                                    @PathVariable(value = "date") Timestamp date,
                                    @PathVariable(value = "name") String name) {

        ArrayList<Charges> chargesToDelete = findElementsByAllInfo(amount, date, name);
        if (!chargesToDelete.isEmpty()) {
        for (Charges charges1 : chargesToDelete) {
            chargesRepository.delete(charges1);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ArrayList<Charges> findElementsByAllInfo(@PathVariable("amount") Integer amount, @PathVariable("date") Timestamp date, @PathVariable("name") String name) {
        Iterable<Charges> charges = chargesRepository.findAll();
        ArrayList<Charges> chargesToDelete = new ArrayList<>();
        for (Charges charge : charges) {
            if ((charge.getAmount().equals(amount)) && (charge.getChargeDate().toString().equals(date.toString())) &&
                    charge.getExpenseItemsId().getName().equals(name))
            {
                chargesToDelete.add(charge);
            }
        }
        return chargesToDelete;
    }
}
