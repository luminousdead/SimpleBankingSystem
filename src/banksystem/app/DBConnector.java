package banksystem.app;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class DBConnector {
    private final SQLiteDataSource dataSource;

    DBConnector(String url) {
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
    }

    public void initDB() {
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

    public void addCard(CardAccount newAcc) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {

                // Добавление записи в бд
                statement.executeUpdate("INSERT INTO card (number, pin) " +
                        "VALUES (" + newAcc.getNUMBER() + ", " + newAcc.getPIN() + ");");


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean findByNumber(String number) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {

                try (ResultSet resultSet = statement.executeQuery("SELECT * " +
                        "FROM card " +
                        "WHERE number = " + number + ";")) {
                    if (resultSet.next()) {
                        return true;
                    }

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public CardAccount logIN(String num, String pin) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {

                try (ResultSet resultSet = statement.executeQuery("SELECT * " +
                        "FROM card " +
                        "WHERE number = " + num + " " +
                        "AND pin = " + pin + ";")) {

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

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
