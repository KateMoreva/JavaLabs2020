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

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
            + COLUMN_PRODUCT_ID + " INT NOT NULL DEFAULT 3, "
            + COLUMN_TITLE + " VARCHAR(20) NOT NULL UNIQUE, "
            + COLUMN_COST + " INT NOT NULL)";
    private static final String INSERT_REQUEST = "INSERT INTO " + TABLE_NAME + "("
            + COLUMN_TITLE + ", "
            + COLUMN_COST + ") VALUES (?, ?)";

    private static final String UPDATE_REQUEST = "UPDATE " + TABLE_NAME + " SET "
            + COLUMN_COST + " = ? WHERE "
            + COLUMN_TITLE + " = ?";

    private static final String SELECT_REQUEST_PRICE_IN_RANGE = "SELECT * FROM " + TABLE_NAME + " WHERE "
            + COLUMN_COST + " >= ? AND "
            + COLUMN_COST + " <= ?";

    private static final String SELECT_REQUEST_PRICE_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE "
            + COLUMN_TITLE + " = ?";

    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;

    private static final String DELETE_PRODUCT_REQUEST = "DELETE FROM " + TABLE_NAME + " WHERE "
            + COLUMN_TITLE + " = ?";


    private static Connection getDerbyConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:derby:products;create=true");
    }

    static void createTable(){
        try (Connection db = getDerbyConnection()) {
            Statement dataQuery = db.createStatement();

            dataQuery.execute(CREATE_TABLE);
        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }

    }

    public static void addTestDataToTable() throws SQLException{
        try (Connection db = getDerbyConnection()) {
            Statement dataQuery = db.createStatement();
            String sql = "TRUNCATE TABLE Products";
            dataQuery.execute(sql);
            String sql2 = "INSERT INTO Products (ProductName, Price) Values ('asd', 123)";
            dataQuery.execute(sql2);

        } catch (SQLException e) {
            if (e.getSQLState().equals("42X05")) {
                Connection db = getDerbyConnection();
                Statement dataQuery = db.createStatement();
                dataQuery.execute(CREATE_TABLE);
            } else {
                System.out.println("Database connection failure: "
                        + e.getMessage());
            }

        }
    }
    public static boolean addDataToTable(String name, int price){
        try (Connection db = getDerbyConnection()) {
            PreparedStatement preparedStatement = db.prepareStatement(INSERT_REQUEST);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static List<Product> selectAll(){
        try (Connection db = getDerbyConnection()) {
            PreparedStatement query = db.prepareStatement(SELECT_ALL);
            ResultSet resultSet = query.executeQuery();

                return buildProductsRequest(resultSet);

        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }

        return null;
    }

    public static boolean deleteDataFromTable(String name){
        try (Connection db = getDerbyConnection()) {
            PreparedStatement preparedStatement = db.prepareStatement(SELECT_REQUEST_PRICE_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                preparedStatement = db.prepareStatement(DELETE_PRODUCT_REQUEST);
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean changeProductPrice(String name, int price){
        try (Connection db = getDerbyConnection()) {
            PreparedStatement preparedStatement = db.prepareStatement(UPDATE_REQUEST);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(1, price);
            preparedStatement.executeUpdate();
           return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static List<Product> priceByNameSearchInTable(String name){
        try (Connection db = getDerbyConnection()) {
            PreparedStatement preparedStatement = db.prepareStatement(SELECT_REQUEST_PRICE_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildProductsRequest(resultSet);

        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }
        return null;
    }

    public static List<Product> priceSearchInTable(int price1, int price2){
        try (Connection db = getDerbyConnection()) {
            PreparedStatement preparedStatement = db.prepareStatement(SELECT_REQUEST_PRICE_IN_RANGE);
            preparedStatement.setInt(1, price1);
            preparedStatement.setInt(2, price2);
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildProductsRequest(resultSet);
        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }
        return null;
    }

    private static List<Product> buildProductsRequest(final ResultSet resultSet) throws SQLException{
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
