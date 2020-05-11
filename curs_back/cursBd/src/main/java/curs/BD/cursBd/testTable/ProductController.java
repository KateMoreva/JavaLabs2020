package curs.BD.cursBd.testTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

//    private final ProductService productService;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
//        productService = null;
    }
//    @GetMapping("/warehouse")
//    public Iterable<Product> getAll() {
//        return productRepository.findAll();
//    }
//
//    @GetMapping("/warehouse/{id}")
//    public Optional<Product> getById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("What"));
//        return productRepository.findById(id);
//    }
//
//    @RequestMapping(value = "/warehouse/filter/{amountFrom}/{amountTo}", method = RequestMethod.GET)
//    public ArrayList<Product> findByFilter(@PathVariable ("amountFrom") Integer amountFrom, @PathVariable ("amountTo") Integer amountTo) {
//        Iterable<Product> products = productRepository.findAll();
//        ArrayList<Product> foundProducts = new ArrayList<>();
//        for (Product pr : products) {
//            if ((pr.getAmount() >= amountFrom) && (pr.getAmount() <= amountTo)) {
//                foundProducts.add(pr);
//            }
//        }
//        return foundProducts;
//    }
//
//    @RequestMapping(value = "/warehouse/name/{name}", method = RequestMethod.GET)
//    public Optional<Product> findByName(@PathVariable ("name") String name) throws ResourceNotFoundException {
//        Iterable<Product> products = productRepository.findAll();
//        for (Product pr : products) {
//            if (pr.getName().equals(name)) {
//                Long id = pr.getId();
//               return productRepository.findById(id);
//            }
//        }
//        Product product = productRepository.findById(22L)
//                .orElseThrow(() -> new ResourceNotFoundException("What"));
//        return productRepository.findById(22L);
//    }
//
//    @RequestMapping(value = "/warehouse", method = RequestMethod.POST )
//    public ResponseEntity<?>  create(@RequestBody Product product) {
//        productRepository.save(product);
//        return new ResponseEntity<String>(HttpStatus.CREATED);
//    }
//
//    @RequestMapping(value = "/warehouse/{id}", method = RequestMethod.PUT )
//    public ResponseEntity<Product> update(@PathVariable("id") Long id, @RequestBody Product productDetails) throws ResourceNotFoundException {
//        if (productRepository.findById(id).isPresent()){
//           Product exproduct = productRepository.findById(id).get();
////            exproduct.setAmount(productDetails.getAmount());
//            exproduct.setQuantity(productDetails.getQuantity());
//            if (productDetails.getName() != null) {
//                exproduct.setName(productDetails.getName());
//            }
//           Product product = productRepository.save(exproduct);
//        }
//        return new ResponseEntity<Product>(productDetails, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/warehouse/{id}", method = RequestMethod.DELETE )
//    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
//        Product product = productRepository.findById(id)
//                .orElseThrow(()-> new ResourceNotFoundException("wwwwhat"));
//        productRepository.delete(product);
//              return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
//    }
//
//    @RequestMapping(value = "/warehouse/deleted/{name}", method = RequestMethod.DELETE )
//    public ResponseEntity<?> deleteByName(@PathVariable("name") String name) throws ResourceNotFoundException{
//        Iterable<Product> products = productRepository.findAll();
////        Product neededPr;
//        for (Product pr : products) {
//            if (pr.getName().equals(name)) {
////                Long id = pr.getId();
////                Product product = productRepository.findById(id);
//                productRepository.delete(pr);
//            }
//        }
//        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
//    }
}
