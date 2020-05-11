//package curs.BD.cursBd.dao;
//
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Predicate;
//
//@Service
//@SuppressWarnings("unused")
//public class ElementDao<T> {
//    private final List<T> elements;
//
//    public ElementDao() {
//       elements = new ArrayList<>();
//    }
//
//    public T get(int i) {
//        return elements.get(i);
//    }
//    public List<T> getAll() {
//        return elements;
//    }
//    public int size() {
//        return elements.size();
//    }
//    public void set(int i, T e) {
//        elements.add(e);
//    }
//    public void add(T element) {
//        elements.add(element);
//    }
//    public void remove(Predicate<? super T> filter) {
//        elements.removeIf(filter);
//    }
//    public void deleteAll() {
//        elements.clear();
//    }
//}
