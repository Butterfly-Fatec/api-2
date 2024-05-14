package meuapp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChooseLLMService {
    private final ArrayList<String> listJson;
    private final ArrayList<String> listModels;
    private String schemaSelected;
    private static final String COMMAND_JSON = "lms ls --json";
    private static final String COMMAND_LOAD_MODEL = "lms load";
    private static final String COMMAND_UNLOAD_MODEL = "lms unload";

    public ChooseLLMService() throws IOException, InterruptedException {
        this.listJson = new ArrayList<>();
        this.listModels = new ArrayList<>();
        commandOne(COMMAND_JSON);
        nameModel(listJson);
        this.schemaSelected = "";
    }

    public void commandOne(String command) throws IOException, InterruptedException {

        Process process = Runtime.getRuntime().exec(command);

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            this.listJson.add(line);
        }
        reader.close();

        process.waitFor();
    }

    public void commandTwo(String selectedLLM) throws IOException, InterruptedException {
        this.schemaSelected = selectedLLM;
        Process process = Runtime.getRuntime().exec(COMMAND_LOAD_MODEL + " " + this.schemaSelected);

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            this.listJson.add(line);
        }
        reader.close();

        process.waitFor();
    }

    public void commandThree() throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(COMMAND_UNLOAD_MODEL + " " + this.schemaSelected);
        process.waitFor();
    }

    public void nameModel(ArrayList<String> listJson) {
        for (String result : listJson) {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                listModels.add(jsonObject.getString("path"));
            }
        }
    }

    public ArrayList<String> getListModels() {
        return listModels;
    }

}