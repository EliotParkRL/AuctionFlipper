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
These can be used to improve the project. The original goal of this project was to make an auction flipper.  
You can use these methods to help create one if you find a way to improve the price calculator (it is missing certain features: dungeon levels, gemstones, and attributes).
Good luck if you'd like to try that.
