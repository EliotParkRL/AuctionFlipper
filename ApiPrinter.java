public class ApiPrinter {
    public static void main(String[] args) {
        ApiCaller MainCaller = new ApiCaller("2ae819ac-896b-4a25-8134-44fc676f7f9b");
        System.out.println(MainCaller.CallFinishedAuctions());

        System.out.println(MainCaller.CallSpecificAuction("profile","ed053e145179482d85db33be70a55e82"));
    }
}
