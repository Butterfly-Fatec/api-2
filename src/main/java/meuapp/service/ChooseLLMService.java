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

    public ChooseLLMService() throws IOException, InterruptedException {
        this.listJson = runCommand("lms ls --json");
        this.listModels = getModelNames(listJson);
        this.schemaSelected = "";
    }

    private ArrayList<String> runCommand(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        ArrayList<String> result = new ArrayList<>();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }
        reader.close();
        process.waitFor();
        return result;
    }

    public void loadLMStudioModel(String selectedLLM) throws IOException, InterruptedException {
        this.schemaSelected = selectedLLM;
        runCommand("lms load " + selectedLLM);
    }

    public void unloadLMStudioModel() throws IOException, InterruptedException {
        runCommand("lms unload " + this.schemaSelected);
        this.schemaSelected = "";
    }

    public final ArrayList<String> getModelNames(ArrayList<String> listJson)  {
        ArrayList<String> result = new ArrayList<String>();
        for (String res : listJson) {
            JSONArray jsonArray = new JSONArray(res);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.add(jsonObject.getString("path"));
            }
        }
        return result;
    }

    public ArrayList<String> getListModels() {
        return listModels;
    }

}