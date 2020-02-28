package DB;

public interface DBHelper {
    void selectAll();
    void add(final String productName, final int price);
    void delete(final String productName);
    void updatePrice(final String productName, final int price);
    void selectByName(final String productName);
    void selectByPriceRange(final int priceFrom, final int priceTo);
}
