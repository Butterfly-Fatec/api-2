package meuapp.service;

import java.util.ArrayList;
import java.io.*;

public class ChooseLLMService {
    private ArrayList<String> nameModel;

    public ChooseLLMService() {
        this.nameModel = new ArrayList<>();
    }

    public void lmList() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("/Users/victorgodoy/.cache/lm-studio/bin/lms");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            reader.close();

            ProcessBuilder processBuilder2 = new ProcessBuilder("/Users/victorgodoy/.cache/lm-studio/bin/lms", "ls");
            processBuilder2.redirectErrorStream(true);
            Process process2 = processBuilder2.start();

            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
            String line2;
            StringBuilder output2 = new StringBuilder();

            while ((line2 = reader2.readLine()) != null) {
                output2.append(line2).append("\n");
            }
            reader2.close();

            String[] lines = output2.toString().split("\n");
            for (int i = 4; i < lines.length; i++) {
                String[] dataModel = lines[i].split("\\s+");
                if (dataModel.length > 0) {
                    nameModel.add(dataModel[0]);

                }
            }

        } catch (IOException e) {
            System.out.println("Error filter list LM models: " + e.getMessage());
        }
    }


    public ArrayList<String> getNameModel() {
        return nameModel;
    }
}
