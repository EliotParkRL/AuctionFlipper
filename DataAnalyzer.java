import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataAnalyzer {
    public int analyzeData(){
        try {
            // Create ProcessBuilder for calling Python script
            ProcessBuilder pb = new ProcessBuilder("/usr/bin/python3", "GetDataPrices.py");

            // Start the process
            Process process = pb.start();

            // Read output from Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            return exitCode;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Downloads needed pkgs for python
     */
    public void downloadPKGs(){
        try {
            // Create ProcessBuilder for calling Python script
            ProcessBuilder pb = new ProcessBuilder("/usr/bin/python3", "DownloadPKGs.py");

            // Start the process
            Process process = pb.start();

            // Read output from Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
