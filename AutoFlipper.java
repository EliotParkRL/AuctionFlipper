import java.util.ArrayList;

public class AutoFlipper {
    public static void main(String[] args) {
        while(true){
            //get recent auctions
            //create auctioneditems objects
            ArrayList<WeaponArmor> mainArrayList = new ArrayList<>();

            ApiCaller MainCaller = new ApiCaller("f43b2f7b-affd-4d71-b51b-a3ee3111657f");
            mainArrayList = WeaponArmor.createAuctionedItemsFromApi(MainCaller.CallFinishedAuctions(), true);

            for(AuctionedItem item : mainArrayList){
                if (item.getAuctionPrice() < .85*item.getPredictedPrice()){
                    Clipboard.copyToClipboard("/viewauction " + item.getAuctionID());
                    System.out.println(item.getAuctionID());
                }
                if (true){
                    Clipboard.copyToClipboard("/viewauction " + item.getAuctionID());
                    System.out.println(item.getAuctionID());
                }
            }


        }
    }
}
