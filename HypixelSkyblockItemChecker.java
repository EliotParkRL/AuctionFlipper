import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

public class HypixelSkyblockItemChecker {

    private static final String API_KEY = "your_api_key";
    private static final String PLAYER_UUID = "player_uuid";

    public static void main(String[] args) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.hypixel.net/skyblock/profiles?key=" + API_KEY + "&uuid=" + PLAYER_UUID))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject data = new JSONObject(response.body());

            if (data.getBoolean("success")) {
                JSONArray profiles = data.getJSONArray("profiles");
                for (int i = 0; i < profiles.length(); i++) {
                    JSONObject profile = profiles.getJSONObject(i);
                    JSONObject members = profile.getJSONObject("members");
                    JSONObject memberData = members.getJSONObject(PLAYER_UUID);

                    if (memberData.has("inventory")) {
                        String inventoryData = memberData.getJSONObject("inventory").getString("data");
                        byte[] decodedBytes = Base64.getDecoder().decode(inventoryData);
                        String decodedString = new String(decodedBytes);
                        JSONArray inventoryJson = new JSONArray(decodedString);

                        for (int j = 0; j < inventoryJson.length(); j++) {
                            JSONObject item = inventoryJson.getJSONObject(j);
                            if (item.has("tag") && item.getJSONObject("tag").has("ExtraAttributes")) {
                                JSONObject extraAttributes = item.getJSONObject("tag").getJSONObject("ExtraAttributes");
                                int hotPotatoCount = extraAttributes.optInt("hot_potato_count", 0);
                                int fumingPotatoCount = extraAttributes.optInt("fuming_potato_count", 0);

                                String itemName = item.getJSONObject("tag").getJSONObject("display").getString("Name");
                                System.out.println("Item: " + itemName);
                                System.out.println("Hot Potato Books: " + hotPotatoCount);
                                System.out.println("Fuming Potato Books: " + fumingPotatoCount);
                            }
                        }
                    }
                }
            } else {
                System.out.println("API request was not successful");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}