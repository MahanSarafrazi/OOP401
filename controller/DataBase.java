package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataBase {

    private Scanner graphScanner;
    private Scanner savingFileScanner;
    private File graphFile;
    private File savingFile;
    public DataBase(String graphFilePath, String savingFilePath) {
        graphFile = new File(graphFilePath);
        savingFile = new File(savingFilePath);
        if(!savingFile.exists()) {
            try {
                savingFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            graphScanner = new Scanner(graphFile);
            savingFileScanner = new Scanner(savingFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        // loading map
        Map map = new Map(Integer.parseInt(graphScanner.next()), Integer.parseInt(graphScanner.next()));
        while (graphScanner.hasNext()) {
            map.addEdge(Integer.parseInt(graphScanner.next()), Integer.parseInt(graphScanner.next()), Integer.parseInt(graphScanner.next()));
        }
        Manager.getManagerInstance().setMap(map);
        graphScanner.close();
        // loading program
        String gsonData = "";
        while (savingFileScanner.hasNextLine()) {
            gsonData = gsonData + savingFileScanner.nextLine();
        }
        savingFileScanner.close();
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        UserList.setUserListInstance(gson.fromJson(gsonData, UserList.class));
    }

    public void save() {
        // saving program
        try {
            GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
            Gson gson = builder.create();
            String userListText = gson.toJson(UserList.getUserListInstance());
            FileWriter fileWriter = new FileWriter(savingFile);
            fileWriter.write(userListText);
            fileWriter.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
