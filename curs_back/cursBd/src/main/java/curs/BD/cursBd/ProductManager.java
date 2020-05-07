//package curs.BD.cursBd;
//
//import curs.BD.cursBd.dao.ElementDao;
//import curs.BD.cursBd.model.Product;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Service
//@SuppressWarnings("unused")
//public class ProductManager {
//    private static final Logger logger = LoggerFactory.getLogger(ProductManager.class);
//    private final ElementDao<Product> dao;
//
//    @Autowired
//    public ProductManager(ElementDao<Product> dao) {
//        this.dao = dao;
//    }
//
//    public Product get(int id) {
//        for(Product product: dao.getAll()) {
//            if (product.getId() == id) {
//                return product;
//            }
//        }
//        return null;
//    }
//    public List<Product> getAll() {
//        return dao.getAll();
//    }
//    public void create(Product product) {
//        dao.add(product);
//    }
//    public void update(Product product) {
//        for (int i = 0; i <dao.size(); ++i ) {
//            if (dao.get(i).getId() == product.getId()) {
//                dao.set(i, product);
//                return;
//            }
//        }
//    }
//    public void delete(int id) {
//        dao.remove(product -> product.getId() == id);
//    }
//    public void deleteAll() {
//        dao.deleteAll();
//    }
//
//}
