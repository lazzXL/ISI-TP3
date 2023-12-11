/*
 * @Author: Matilde Pato (mpato)
 * @Date: 2023-11-27 09:30:00
 * @Last Modified time: 2023-12-06 18:30:00
 * ISEL - DEETC
 * Introdução a Sistemas de Informação
 * MPato, 2023-2024
 * 
 * NOTE: 
 * The code is split into classes; please don't add more classes or 
 * change the initial configuration.
 * 1) The Bike class is a class that contains the attributes of the 
 *  BICYCLE table. The same for ElectricBike, ClassicBike and GPSDevice.
 *  All are already implemented! Do not change it!
 * 2) The model class is where all the application's methods should be 
 *  implemented.
 * 3) The restriction class should contain the restrictions on the data 
 *  model. It is only executed when there is a new entry in the tables to 
 *  which it is affected.
 * 4) You must add the IP address, Port number, Database and PWD in line 237
 * 5) The values entered must be separated by a comma with no blank spaces
 * 6) The values entered must follow the order defined in the database to 
 * avoid further validation codes
 * 
 */
package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
interface DbWorker
{
    void doWork();
}
class App
{
    private enum Option
    {
        // DO NOT CHANGE ANYTHING!
        Unknown,
        Exit,
        novelBike,
        updateBikeState,
        calculateAverageMetricsForElectricBikes,
        getClientsWithReservations,
        getGPSDevice,
        getStores,
        getManagers,
    }
    private static App __instance = null;
    private String __connectionString;

    private HashMap<Option,DbWorker> __dbMethods;

    private App()
    {
        // DO NOT CHANGE ANYTHING!
        __dbMethods = new HashMap<Option,DbWorker>();
        __dbMethods.put(Option.novelBike, () -> App.this.novelBike());    
        __dbMethods.put(Option.updateBikeState, () -> App.this.updateBikeState()); 
        __dbMethods.put(Option.calculateAverageMetricsForElectricBikes, () -> App.this.calculateAverageMetricsForElectricBikes());  
        __dbMethods.put(Option.getClientsWithReservations, () -> App.this.getClientsWithReservations());    
        __dbMethods.put(Option.getGPSDevice, new DbWorker() {public void doWork() {App.this.getGPSDevice();}});
        __dbMethods.put(Option.getStores, new DbWorker() {public void doWork() {App.this.getStores();}});
        __dbMethods.put(Option.getManagers, new DbWorker() {public void doWork() {App.this.getManagers();}});

    }

    public static App getInstance()
    {
        if(__instance == null)
        {
            __instance = new App();
        }
        return __instance;
    }

    private Option DisplayMenu()
    {
        Option option = Option.Unknown;
        try
        {
            // DO NOT CHANGE ANYTHING!
            System.out.println("Bicycle reservation");
            System.out.println();
            System.out.println("1. Exit");
            System.out.println("2. Novel bikes");
            System.out.println("3. Update bike states");
            System.out.println("4. Average metrics for electric bikes");
            System.out.println("5. List of clients with reservations");
            System.out.println("6. List of devices");
            System.out.println("7. List of stores");
            System.out.println("8. List of managers and made reservations");
            System.out.print(">");
            Scanner s = new Scanner(System.in);
            int result = s.nextInt();
            option = Option.values()[result];
        }
        catch(RuntimeException ex)
        {
            //nothing to do.
        }
        return option;

    }
    private static void clearConsole() throws Exception
    {
        for (int y = 0; y < 25; y++) //console is 80 columns and 25 lines
            System.out.println("\n");

    }
    private void Login() throws java.sql.SQLException
    {
        Connection con = DriverManager.getConnection(getConnectionString());
        if(con != null)
            con.close();
    }

    public void Run() throws Exception
    {
        Login();
        Option userInput;
        do
        {
            clearConsole();
            userInput = DisplayMenu();
            clearConsole();
            try
            {
                __dbMethods.get(userInput).doWork();
                System.in.read();

            }
            catch(NullPointerException ex)
            {
                //Nothing to do. The option was not a valid one. Read another.
            }

        }while(userInput!=Option.Exit);
    }

    public String getConnectionString()
    {
        return __connectionString;
    }
    public void setConnectionString(String s)
    {
        __connectionString = s;
    }

    /**
    To implement from this point forward. Do not need to change the code above.
    -------------------------------------------------------------------------------     
        IMPORTANT:
    --- DO NOT MOVE IN THE CODE ABOVE. JUST HAVE TO IMPLEMENT THE METHODS BELOW ---
    -------------------------------------------------------------------------------
    
    */

    private static final int TAB_SIZE = 24;
    void printResults(ResultSet dr) throws SQLException {
        
        //TODO
        /*Result must be similar like:
        ListDepartment()
        dname           dnumber     mgrssn      mgrstartdate            
        -----------------------------------------------------
        Research        5           333445555   1988-05-22            
        Administration  4           987654321   1995-01-01
        */ 
    }

    private void novelBike() {
        // IMPLEMENTEDs
        System.out.println("novelBike()");
        String bikeType = Model.inputData("Type of bike (1 for Electric, 2 for Classic):\n");

        String val_bikes = Model.inputData("The components of the bike (brand, model, weight, and others).\n");
        String val_device = Model.inputData("The serial number, location (latitude and longitude), and battery level of the GPS device.\n");

        //IMPORTANT: The values entered must be separated by a comma with no blank spaces
        GPSDevice device = new GPSDevice(val_device.split(","));
        int nodevice = device.getSerialNumber();
        Model.addGPSDevice(device);

        if ("1".equals(bikeType)) {
            // For Electric Bike
           Model.addElectricBike(new ElectricBike(val_bikes.split(","), nodevice));
        } else if ("2".equals(bikeType)) {
            // For Classic Bike
            Model.addClassicBike(new ClassicBike(val_bikes.split(","), nodevice));
        } else {
            System.out.println("Invalid bike type. Please enter 1 for Electric or 2 for Classic.");
        }
    }
  
    private void updateBikeState()
    {
        // TODO
        System.out.println("updateBikeState()");

    }

    private void calculateAverageMetricsForElectricBikes()
    {
        // TODO
        System.out.println("calculateAverageMetricsForElectricBikes()");

    }

    private void getClientsWithReservations() {
        // TODO
        System.out.println("getClientsWithReservations()");
    }

    private void getGPSDevice()
    {
        // TODO
        System.out.println("getGPSDevices()");
        
    }

    private void getStores()
    {
        // TODO
        System.out.println("getStores");
        
    }

    private void getManagers()
    {
        // TODO
        System.out.println("getManagers");
        
    }
}

public class Ap{
    public static void main(String[] args) throws Exception{
        
        String url = "jdbc:postgresql://XXXXX:5432/?user=XXXXX&password=XXXXX&ssl=false";
        App.getInstance().setConnectionString(url);
        App.getInstance().Run();
    }
}