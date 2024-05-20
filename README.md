# AuctionFlipper
In order to run this project: you must have /usr/bin/python3.  
Then, you must run DataAnalyzer.downloadPKGs() to download the required pkgs for the analyzeData() method.  

Project Structure:  
1. WeaponArmor and AuctionedItem  
WeaponArmor is a subclass of the AuctionedItem abstract class. An object of this subclass represents an item that is a weapon or armor.  
It collects the necessary data of our item through the hypixel and coflnet APIs and has a method to write them to the model_train_input file.  

2. DataAnalyzer  
The DataAnalyzer has static methods that will run the python files in this project.  

3. DownloadPKGs and GetDataPrices  
DownloadPKGS installs the required packages assuming that the user has /usr/bin/python3.  
GetDataPrices will analyze the data within model_train_input and calculate prices in model_predict_input assuming that you have ran DownloadPKGs.

4. ApiCaller  
Makes calls to the API in order to grab information about past and new auctions.  

6. All methods other than those listed and ApiPrinter  
These were written and can be used to improve the project even though they weren't used. The original goal of this project was to make an auction flipper; 
however, given the limited time, we weren't able to utilize them to make a true auction flipper.
Some steps to improve:
A. Add more features to the price calculator. The price calculator is missing dungeon levels, gemstones, and attributes for armor and weapons that need these.  
B. Create a graphics interface using grid_layout in order to allow instant copying from the utilities file if you find an item to buy.  
C. Allow automatic data gathering while flipping and play a ding when an item to flip comes up.  


Best of luck to anyone trying to improve it if you choose to do so.
