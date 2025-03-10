package ro.mpp2024.repository;

import ro.mpp2024.domain.Car;
import ro.mpp2024.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
//import java.util.logging.Logger;

public class CarsDBRepository implements CarRepository{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();

        // Corrected SQL query with a placeholder (?)
        String sql = "SELECT * FROM cars WHERE manufacturer = ?";

        try (PreparedStatement preStnt = con.prepareStatement(sql)) {
            // Set the manufacturer parameter in the query
            preStnt.setString(1, manufacturerN);

            try (ResultSet rs = preStnt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String manufacturer = rs.getString("manufacturer");
                    String model = rs.getString("model");
                    int year = rs.getInt("year");

                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException ex) {
            logger.error("Database error", ex);
            System.err.println("Error db: " + ex.getMessage());
        }

        logger.traceExit();
        return cars;
    }


    @Override
    public List<Car> findBetweenYears(int min, int max) {
        //to do
        return null;
    }

    @Override
    public void add(Car elem) {
        logger.traceEntry("saving task {} ", elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStnt = con.prepareStatement("insert into cars (manufacturer, model, year) values (?,?,?)"))
        {
            preStnt.setString(1, elem.getManufacturer());
            preStnt.setString(2, elem.getModel());
            preStnt.setInt(3, elem.getYear());
            int result = preStnt.executeUpdate();
            logger.trace("saved {} instances", result);

        }
        catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error db: "+ ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Car elem) {
        //to do
    }

    @Override
    public Iterable<Car> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement preStnt = con.prepareStatement("select * from cars"))
        {
            try(ResultSet rs = preStnt.executeQuery())
            {
                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String manufacturer = rs.getString("manufacturer");
                    String model = rs.getString("model");
                    int year = rs.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        }
        catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error db: "+ ex);
        }
        logger.traceExit();
        return cars;
    }
}
