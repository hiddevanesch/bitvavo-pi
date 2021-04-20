package nl.hiddevanesch;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.bitvavo.api.Bitvavo;
import com.google.gson.Gson;

import com.pi4j.component.lcd.impl.I2CLcdDisplay;
import com.pi4j.component.lcd.*;

import org.json.JSONArray;
import org.json.JSONObject;

import nl.hiddevanesch.Model.*;

// Make sure to read the README.md of the project before use

public final class App {

    private App() {
    }

    public static void main(String[] args) throws Exception {
        // Variable initialization
        Gson gson = new Gson();

        final String API_KEY;
        final String API_SECRET;

        Scanner sc = new Scanner(System.in);

        /*
         * Takes API keys and prefered currency as arguments
         */
        if (args.length == 2) {
            API_KEY = args[0];
            API_SECRET = args[1];
        } else {
            System.out.println("Please enter your Bitvavo API Key:");
            API_KEY = sc.nextLine();
            System.out.println("Please enter your Bitvavo API Secret:");
            API_SECRET = sc.nextLine();
        }

        if (API_KEY.length() != 64) {
            throw new Exception("API Key is not of valid length");
        }

        if (API_SECRET.length() != 128) {
            throw new Exception("API Secret is not of valid length");
        }

        // Bitvavo initialization
        Bitvavo bitvavo = new Bitvavo(new JSONObject("{" + 
            "APIKEY: '" + API_KEY + "', " +
            "APISECRET: '" + API_SECRET + "', " +
            "RESTURL: 'https://api.bitvavo.com/v2'," +
            "WSURL: 'wss://ws.bitvavo.com/v2/'," +
            "ACCESSWINDOW: 10000, " +
            "DEBUGGING: false }"
        ));

        // Display initialization
        I2CLcdDisplay lcd = new I2CLcdDisplay(
            2,  //     * @param rows
            16, //     * @param columns
            1,  //     * @param i2cBus
            39, //     * @param i2cAddress
            3,  //     * @param backlightBit
            0,  //     * @param rsBit
            1,  //     * @param rwBit
            2,  //     * @param eBit
            7,  //     * @param d7
            6,  //     * @param d6
            5,  //     * @param d5
            4   //     * @param d4
        );
        lcd.write(0, "Total balance:", LCDTextAlignment.ALIGN_CENTER);
        
        while (bitvavo.getRemainingLimit() > 1) {
            JSONArray balanceResponse = bitvavo.balance(new JSONObject());
            Balance[] balances = gson.fromJson(balanceResponse.toString(),
                Balance[].class);

            if (balances.length + 1 > bitvavo.getRemainingLimit()) {
                break;
            }

            // Compute total balance in euro
            float total = 0;
            for (Balance balance : balances) {
                String symbol = balance.getSymbol();
                if (!symbol.equals("EUR")) {
                    JSONArray marketResponse = bitvavo.tickerPrice(
                        new JSONObject("{market:" + symbol + "-EUR}"));
                    Market[] market = gson.fromJson(marketResponse.toString(),
                        Market[].class);
                    total += market[0].getPrice() * balance.getAmount();
                } else {
                    total += balance.getAmount();
                }
            }

            System.out.println("Total balance: " + total + " EUR");

            // Write to display
            lcd.write(1, String.format("%.02f", total) + " EUR",
                LCDTextAlignment.ALIGN_CENTER);

            // Wait for 1 seconds
            wait(1000);
        }
        
        System.out.println("Request limit exceeded, stopping execution");
        System.exit(0);
    }

    static void wait(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
