//package data.movies;
//
//import com.mysql.cj.jdbc.Driver;
//import config.Config;
//import data.Car;
//​
//import java.sql.*;
//import java.util.ArrayList;
//
//
//
//public class MoviesJDBCTest {
//
//​
//
//        private static Connection connection = null;
//​
//        public static void main(String[] args) throws SQLException {
//            DriverManager.registerDriver(new Driver());
//            connection = DriverManager.getConnection(
//                    "jdbc:mysql://" + Config.DB_HOST + ":3306/docrob?allowPublicKeyRetrieval=true&useSSL=false",
//                    Config.DB_USER,
//                    Config.DB_PW
//            );
//​
//            ArrayList<Movie> cars = fetchAllMovies();
//            System.out.println(cars);
//​
//            // insert a car
//            Statement st = connection.createStatement();
//​
//            // let's assume the below variables come from an end-user
//            String newVin = "malicious sql code goes here";
//            int newYear = 2000;
//            int newMileage = 10000;
//​
//            // never ever do this: concatenate user input into ANY query string
////        String mySQLString = "insert into cars (vin, year, mileage) values ('" + newVin + "', " + newYear + ", " + newMileage + ")";
////        st.executeUpdate(mySQLString);
//​
//            // general sql injection prevention technique is use parameterized queries
//            // jdbc calls them prepared statements
//            PreparedStatement ps = connection.prepareStatement("insert into cars (vin, year, mileage) values (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
//            ps.setString(1, newVin);
//            ps.setInt(2, newYear);
//            ps.setInt(3, newMileage);
//            ps.executeUpdate();
//​
//            ResultSet newKeys = ps.getGeneratedKeys();
//            newKeys.next();
//            int newId = newKeys.getInt(1);
//            System.out.println("new record id is " + newId);
//            newKeys.close();
//​
//            cars = fetchAllCars();
//            System.out.println(cars);
//​
//            newYear = 2010;
//​
//            ps = connection.prepareStatement("update cars set year = ? where id = ?");
//            ps.setInt(1, newYear);
//            ps.setInt(2, newId);
//            ps.executeUpdate();
//​
//            cars = fetchAllCars();
//            System.out.println(cars);
//​
//            ps = connection.prepareStatement("delete from cars where id = ?");
//            ps.setInt(1, newId);
//            ps.executeUpdate();
//​
//            cars = fetchAllCars();
//            System.out.println(cars);
//​
//            ps.close();
//            st.close();
//            connection.close();
//        }
//​
//        private static ArrayList<Car> fetchAllCars() throws SQLException {
//            ArrayList<Car> cars = new ArrayList<>();
//​
//            // use the connection for sql work
//            Statement st = connection.createStatement();
//            ResultSet carRows = st.executeQuery("select vin, year, mileage from cars");
//            // iterate over the rows and print out fields for each row
//            // can rig up a manual counter if we want
//            int rowCount = 0;
//            while(carRows.next()) { // for each row in the resultSet
//                rowCount++;
//                // make a car object from that row
//                Car car = new Car(carRows.getString("vin"),
//                        carRows.getInt("year"),
//                        carRows.getInt("mileage"));
////            System.out.println(car);
//​
//                cars.add(car);
//            }
////        System.out.println("row count: " + rowCount);
//​
////        carRows = st.executeQuery("select count(id) from cars");
////        carRows.next();
////        System.out.println("row count via count function: " + carRows.getInt("count(id)"));
//​
//            return cars;
//        }
//
//}
