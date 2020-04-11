package sample.dataBase;

import sample.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DbHelper {
    private static final String TABLE_NAME = "Products";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_PRODUCT_ID = "Prodid";
    private static final String COLUMN_TITLE = "ProductName";
    private static final String COLUMN_COST = "Price";
    private static final String DERBY_CONNECTION = "jdbc:derby:products;create=true";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
            + COLUMN_PRODUCT_ID + " INT NOT NULL DEFAULT 3, "
            + COLUMN_TITLE + " VARCHAR(20) NOT NULL UNIQUE, "
            + COLUMN_COST + " INT NOT NULL)";
    private static final String INSERT = "INSERT INTO " + TABLE_NAME + "("
            + COLUMN_TITLE + ", "
            + COLUMN_COST + ") VALUES (?, ?)";

    private static final String CHANGE_PRICE = "UPDATE " + TABLE_NAME + " SET "
            + COLUMN_COST + " = ? WHERE "
            + COLUMN_TITLE + " = ?";

    private static final String PRICE_IN_RANGE = "SELECT * FROM " + TABLE_NAME + " WHERE "
            + COLUMN_COST + " >= ? AND "
            + COLUMN_COST + " <= ?";

    private static final String PRICE_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE "
            + COLUMN_TITLE + " = ?";

    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;

    private static final String DELETE_PRODUCT = "DELETE FROM " + TABLE_NAME + " WHERE "
            + COLUMN_TITLE + " = ?";
    private static final String TABLE_NOT_EXIST = "42X05";
    private static final String TRUNCATE = "TRUNCATE TABLE " + TABLE_NAME;
    private static final String TEST_PRODUCT_NAME1 = "asd";
    private static final int TEST_PRODUCT_PRICE1 = 123;


    private static Connection getDBConnection() throws SQLException{
        return DriverManager.getConnection(DERBY_CONNECTION);
    }

    public static void addTestProduct() throws SQLException{
        try (Connection db = getDBConnection()) {
            Statement dataQuery = db.createStatement();
            dataQuery.execute(TRUNCATE);
            PreparedStatement preparedStatement = db.prepareStatement(INSERT);
            preparedStatement.setString(1, TEST_PRODUCT_NAME1);
            preparedStatement.setInt(2, TEST_PRODUCT_PRICE1);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            if (e.getSQLState().equals(TABLE_NOT_EXIST)) {
                Connection db = getDBConnection();
                Statement dataQuery = db.createStatement();
                dataQuery.execute(CREATE_TABLE);
            } else {
                System.out.println("Database connection failure: "
                        + e.getMessage());
            }

        }
    }

    public static boolean addProduct(String name, int price){
        try (Connection db = getDBConnection()) {
            PreparedStatement preparedStatement = db.prepareStatement(INSERT);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static List<Product> selectAllProducts(){
        try (Connection db = getDBConnection()) {
            PreparedStatement query = db.prepareStatement(SELECT_ALL);
            ResultSet resultSet = query.executeQuery();

                return buildProducts(resultSet);

        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }

        return null;
    }

    public static boolean deleteProduct(String name){
        try (Connection db = getDBConnection()) {
            PreparedStatement preparedStatement = db.prepareStatement(PRICE_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
                preparedStatement = db.prepareStatement(DELETE_PRODUCT);
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
                preparedStatement.close();
               return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean changeProductPrice(String name, int price){
        try (Connection db = getDBConnection()) {
            PreparedStatement preparedStatement = db.prepareStatement(PRICE_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> tmp = buildProducts(resultSet);
            if (!tmp.isEmpty()) {
                preparedStatement = db.prepareStatement(CHANGE_PRICE);
                preparedStatement.setString(2, name);
                preparedStatement.setInt(1, price);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }
        return false;
    }

    public static List<Product> priceByNameProductSearch(String name){
        try (Connection db = getDBConnection()) {
            PreparedStatement preparedStatement = db.prepareStatement(PRICE_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildProducts(resultSet);
        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }
        return null;
    }

    public static List<Product> priceRangeProductSearch(int priceFrom, int priceTo){
        try (Connection db = getDBConnection()) {
            PreparedStatement preparedStatement = db.prepareStatement(PRICE_IN_RANGE);
            preparedStatement.setInt(1, priceFrom);
            preparedStatement.setInt(2, priceTo);
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildProducts(resultSet);
        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }
        return null;
    }

    private static List<Product> buildProducts(final ResultSet resultSet) throws SQLException{
        final List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
             products.add(
                    new Product(
                            resultSet.getInt(COLUMN_PRODUCT_ID),
                            resultSet.getString(COLUMN_TITLE),
                            resultSet.getInt(COLUMN_COST)));
        }
        resultSet.close();
        return products;
    }

}
