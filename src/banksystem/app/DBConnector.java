package banksystem.app;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class DBConnector {
    private static final SQLiteDataSource dataSource = new SQLiteDataSource();

    public static void setUrl(String url) {
        dataSource.setUrl(url);
    }

    public static void initDB() {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {

                // Создание бд.
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "number TEXT NOT NULL," +
                        "pin TEXT NOT NULL," +
                        "balance INTEGER DEFAULT 0" +
                        ");");


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteDB(String dbname) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DROP TABLE IF EXISTS " + dbname);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // точно работает
    private static final String insert = "INSERT INTO card (number, pin) VALUES (?, ?);";
    public static void addCard(CardAccount account) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insert)) {
                statement.setString(1, account.getNUMBER());
                statement.setString(2, account.getPIN());
                statement.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    private static final String selectByNumberAndPIN = "SELECT * FROM card WHERE number = ? AND pin = ?;";
    public static CardAccount logIN(String num, String pin) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(selectByNumberAndPIN)) {
                statement.setString(1, num);
                statement.setString(2, pin);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return new CardAccount(
                            resultSet.getString("number"),
                            resultSet.getString("pin"),
                            resultSet.getInt("id"),
                            resultSet.getInt("balance")
                    );
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private static final String updateBalanceById = "UPDATE card SET balance = ? WHERE id = ?";
    public static void updateBalanceByID(int id, int balance) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(updateBalanceById)) {

                statement.setInt(1, balance);
                statement.setInt(2, id);
                statement.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static final String selectByNumber = "SELECT * FROM card WHERE number = ? ;";
    public static boolean findByNumber(String number) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(selectByNumber)) {
                statement.setString(1, number);
                ResultSet resultSet = statement.executeQuery();

                // проверка есть ли такой номер
                if (resultSet.next()) {
                    return true;
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private static final String delete = "DELETE FROM card WHERE id = ?;";
    public static void deleteByID(int id) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement deleteAccountByID = connection.prepareStatement(delete)){
                deleteAccountByID.setInt(1, id);
                deleteAccountByID.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // работает только в EDU IDEA
    private static final String updateBalanceByNumber = "UPDATE card SET balance = (balance + ?) WHERE number = ?";
    public static String doTransfer(String from, String to, int value) {
        try (Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(updateBalanceByNumber)) {
                Savepoint savepoint = connection.setSavepoint();

                statement.setInt(1, -value);
                statement.setString(2, from);
                statement.executeUpdate();

                statement.setInt(1, value);
                statement.setString(2, to);
                statement.executeUpdate();

                PreparedStatement selectBalanceByNumber = connection.prepareStatement(selectByNumber);
                selectBalanceByNumber.setString(1, from);
                ResultSet resultSet = selectBalanceByNumber.executeQuery();

                if (resultSet.getInt(4) < 0) {
                    connection.rollback(savepoint);
                    return "Not enough money!";
                }

                connection.commit();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "Success!";
    }
}
