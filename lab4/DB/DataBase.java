package DB;

import Contract.Contract;

import java.sql.*;

public class DataBase {

    private static final String CREATE_QUERY =
            "CREATE TABLE PRODUCTS (ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, Prodid INT NOT NULL DEFAULT 3, ProductName VARCHAR(20) UNIQUE, Price INT)";
    private static Connection getDerbyConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:derby:products;create=true");
    }

    public static void printAllTable(){
        try (Connection db = getDerbyConnection()) {
            try (PreparedStatement query =
                         db.prepareStatement("SELECT * FROM PRODUCTS")) {
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    System.out.println(String.format("%s %s",
                            rs.getString(Contract.TableColoumns.COLUMN_TITLE_INDEX),
                            rs.getString(Contract.TableColoumns.COLUMN_COST_INDEX)
                    ));
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }
    }

    public static void addTestDataToTable() {
        try (Connection db = getDerbyConnection()) {
            try (Statement dataQuery = db.createStatement()) {
                String sql = "TRUNCATE TABLE Products";
                dataQuery.execute(sql);
                String sql2 = "INSERT INTO Products (ProductName, Price) Values ('asd', 123)";
                dataQuery.execute(sql2);
            }
        } catch (SQLException e) {
            if (!e.getSQLState().equals("X0Y32"))
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }
    }

    public static void addDataToTable(String name, int price){
        try (Connection db = getDerbyConnection()) {
                  String sql = "INSERT INTO Products (ProductName, Price) Values (?, ?)";
                  PreparedStatement preparedStatement = db.prepareStatement(sql);
                  preparedStatement.setString(1, name);
                  preparedStatement.setInt(2, price);
                  preparedStatement.executeUpdate();
                      preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }
    }

    public static void deleteDataFromTable(String name){
        try (Connection db = getDerbyConnection()) {
                String sql = "DELETE FROM Products WHERE ProductName = ?";
                PreparedStatement preparedStatement = db.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }
    }

    public static void changeProductPrice(String name, int price){
        try (Connection db = getDerbyConnection()) {
            String sql = "UPDATE Products SET Price = ? WHERE ProductName = ? ";
            PreparedStatement preparedStatement = db.prepareStatement(sql);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(1, price);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }
    }

    public static void priceByNameSearchInTable(String name){
        try (Connection db = getDerbyConnection()) {
            String sql = "SELECT * FROM Products WHERE ProductName = ?";
            PreparedStatement preparedStatement = db.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println(String.format("%s %s",
                        resultSet.getString(1),
                        resultSet.getString(2)
                ));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }
    }

    public static void priceSearchInTable(int price1, int price2){
        try (Connection db = getDerbyConnection()) {
            String sql = "SELECT * FROM Products WHERE Price >= ? <= ?";
            PreparedStatement preparedStatement = db.prepareStatement(sql);
            preparedStatement.setInt(1, price1);
            preparedStatement.setInt(2, price2);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println(String.format("%s %s",
                        resultSet.getString(1),
                        resultSet.getString(2)
                ));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }
    }
}
//String sqlQuery = "DROP TABLE if exists product";