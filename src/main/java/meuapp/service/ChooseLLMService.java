package meuapp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChooseLLMService {
    public static List<String> getCommandResult(String command) throws IOException, InterruptedException {
        List<String> resultList = new ArrayList<>();

        @SuppressWarnings("deprecation")
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            resultList.add(line);
        }
        reader.close();

        return resultList;
    }

    public ArrayList<String> getNameModel() {
        List<String> resultList;
        ArrayList<String> paths = new ArrayList<>();

        try {
            resultList = getCommandResult("lms ls --json");
        } catch (IOException | InterruptedException e) {
            // Aqui você pode lidar com a exceção localmente
            System.err.println("Ocorreu um erro ao executar o comando: " + e.getMessage());
            resultList = new ArrayList<>(); // Retorna uma lista vazia em caso de erro
        }

        for (String result : resultList) {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                paths.add(jsonObject.getString("path"));
            }
        }

        return paths;
    }
}