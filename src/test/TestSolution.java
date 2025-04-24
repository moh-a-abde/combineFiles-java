package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestSolution {
    private static final String CSV_1_PATH = "resources/1.csv";
    private static final String CSV_2_PATH = "resources/2.csv";
    private static final String OUTPUT_CSV_PATH = "resources/my_output.csv";
    public void combineFiles(File csv1, File csv2, File outputFile) throws Exception {
        Map<String, String> remainderById = new HashMap<>();
        try (BufferedReader br2 = new BufferedReader(new FileReader(csv2))) {
            String line;
            while ((line = br2.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",", -1);    
                if (parts.length == 0) continue;
                String id = parts[0].trim();
                String remainder = String.join(",", Arrays.copyOfRange(parts, 1, parts.length));
                remainderById.put(id, remainder);
            }
        }
        try (BufferedReader br1 = new BufferedReader(new FileReader(csv1));
             BufferedWriter bw  = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            while ((line = br1.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length == 0) continue;
                String id = parts[0].trim();

                String match = remainderById.get(id);
                if (match != null) {                          
                    bw.write(line + "," + match);
                    bw.newLine();
                }
            }
        }
    }
    public static void main(String[] args) {
        try {
            File csv1   = new File(CSV_1_PATH);
            File csv2   = new File(CSV_2_PATH);
            File output = new File(OUTPUT_CSV_PATH);
            
            System.out.println("Input file 1 exists: " + csv1.exists());
            System.out.println("Input file 2 exists: " + csv2.exists());
            System.out.println("Working directory: " + new File(".").getAbsolutePath());

            TestSolution solution = new TestSolution();
            solution.combineFiles(csv1, csv2, output);
            
            System.out.println("Processing complete. Output file created: " + output.exists());

        } catch (Exception e) {
            System.out.println("Error occurred:");
            e.printStackTrace();
        }
    }
}
