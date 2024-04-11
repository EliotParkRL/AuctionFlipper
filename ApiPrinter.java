import java.util.ArrayList;

public class ApiPrinter {
    public static void main(String[] args) {
        ArrayList<AuctionedItem> test = new ArrayList<>();

        ApiCaller MainCaller = new ApiCaller("2ae819ac-896b-4a25-8134-44fc676f7f9b");
        test = AuctionedItem.createAuctionedItemsFromApi(MainCaller.CallFinishedAuctions());
        for(AuctionedItem item : test){
            System.out.println(item.tempName());
        }
    }
}
