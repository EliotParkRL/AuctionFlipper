import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataAnalyzer {
    public int analyzeData(){
        try {
            // Create ProcessBuilder for calling Python script
            ProcessBuilder pb = new ProcessBuilder("/Users/natha/PycharmProjects/test/venv/bin/python", "GetDataPrices.py");

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
}
