import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;



//adlifhaiuhgiphgriowejroij

public class ApiCaller {
    public String CallNewAuctions() {
        try {
            // Construct the API endpoint URL with the UUID directly appended to it
            String apiKey = "2ae819ac-896b-4a25-8134-44fc676f7f9b";
            String page = "10";
            String apiUrl = "https://api.hypixel.net/v2/skyblock/auctions_ended?key=" + apiKey + "&page=" + encodeValue(page);

            // Create a URL object with the constructed API endpoint URL
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Read the response from the API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Close the reader and connection
            reader.close();
            connection.disconnect();

            // Return the response from the API
            System.out.println("Response from API:");
            return(response.toString());
        } catch (Exception e) {
            // Handle any exceptions that occur during the API call
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return("There was an error");
        }
    }

    public static String CallFinishedAuctions() {
        try {
            // Construct the API endpoint URL with the UUID directly appended to it
            String apiKey = "2ae819ac-896b-4a25-8134-44fc676f7f9b";
            String page = "1";
            String apiUrl = "https://api.hypixel.net/v2/skyblock/auctions?key=" + apiKey + "&page=" + encodeValue(page);

            // Create a URL object with the constructed API endpoint URL
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Read the response from the API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Close the reader and connection
            reader.close();
            connection.disconnect();

            // Return the response from the API
            return("Response from API:" + response.toString());
        } catch (Exception e) {
            // Handle any exceptions that occur during the API call
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return("There was an error");
        }
    }

    /**
     * Gets a specific auction
     * @param type uuid or player or profile
     * @param specific the uuid of the auction, player or profile
     */
    public String CallSpecificAuction(String type, String specific) {
        try {
            // Construct the API endpoint URL with the UUID directly appended to it
            String apiKey = "2ae819ac-896b-4a25-8134-44fc676f7f9b";
            type = "&" + type + "=";
            String apiUrl = "https://api.hypixel.net/v2/skyblock/auctions?key=" + apiKey + type + encodeValue(specific);

            // Create a URL object with the constructed API endpoint URL
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Read the response from the API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Close the reader and connection
            reader.close();
            connection.disconnect();

            // Return the response from the API
            System.out.println("Response from API:");
            return(response.toString());
        } catch (Exception e) {
            // Handle any exceptions that occur during the API call
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return("There was an error");

        }
    }



    // Helper method to encode URL parameters
    public static String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8");
    }
}
