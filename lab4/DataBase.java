import java.sql.*;
import java.util.Scanner;

class DataBase {

    private static final String CREATE_QUERY =
            "CREATE TABLE PRODUCTS (ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, Prodid INT NOT NULL DEFAULT 3, ProductName VARCHAR(20) UNIQUE, Price INT)";
    private static Connection getDerbyConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:derby:memory:test;create=true");
    }

    static void printAllTable(){
        try (Connection db = getDerbyConnection()) {
            try (PreparedStatement query =
                         db.prepareStatement("SELECT * FROM PRODUCTS")) {
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    System.out.println(String.format("%s %s",
                            rs.getString(2),
                            rs.getString(3)
                    ));
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }
    }
    static void addData(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter product's name");
        String name = scanner.nextLine();
        System.out.println("Enter product's price");
        int price = scanner.nextInt();
        addDataToTable(name, price);
    }
    static void addTestDataToTable(){
        try (Connection db = getDerbyConnection()) {
            try (Statement dataQuery = db.createStatement()) {
                dataQuery.execute(CREATE_QUERY);
                String sql = "INSERT INTO Products (ProductName, Price) Values ('asd', 123)";
                dataQuery.execute(sql);
            }
        } catch (SQLException e) {
            System.out.println("Database connection failure: "
                    + e.getMessage());
        }
    }

   static void addDataToTable(String name, int price){
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

    static void deleteDataFromTable(String name){
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

    static void changeProductPrice(String name, int price){
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

    static void priceByNameSearchInTable(String name){
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

    static void priceSearchInTable(int price1, int price2){
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