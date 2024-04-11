import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CSVWriter {

    private static final String CSV_FILE_PATH = "Auction Data";

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(CSVWriter::writeToCSV, 0, 60, TimeUnit.SECONDS);
    }

    private static void writeToCSV() {
        try (FileWriter writer = new FileWriter(CSV_FILE_PATH, true)) {
            ApiCaller MainCaller = new ApiCaller("f43b2f7b-affd-4d71-b51b-a3ee3111657f");
            ArrayList<AuctionedItem> test = new ArrayList<>();
            test = AuctionedItem.createAuctionedItemsFromApi(MainCaller.CallFinishedAuctions());

            // Write data to CSV file
            // For demonstration, let's just write a timestamp
            for(AuctionedItem item : test){
                writer.append((CharSequence) item.printReasonableJSON());
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
