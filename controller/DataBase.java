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

    private final Scanner graphScanner;
    private final Scanner userListFileScanner;
    private final File graphFile;
    private final File userListFile;
    public DataBase(String graphFilePath, String userListFilePath) {
        graphFile = new File(graphFilePath);
        userListFile = new File(userListFilePath);
        if(!userListFile.exists()) {
            try {
                userListFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            graphScanner = new Scanner(graphFile);
            userListFileScanner = new Scanner(userListFile);
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
        // loading user list
        String gsonData = "";
        while (userListFileScanner.hasNextLine()) {
            gsonData = gsonData + userListFileScanner.nextLine();
        }
        userListFileScanner.close();
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        UserList.setUserListInstance(gson.fromJson(gsonData, UserList.class));


        for (RestaurantOwner re)
        RandomIDGenerator.add(Restaurant.getAllIDs());
        RandomIDGenerator.add(Food.getAllIDs());
    }

    public void save() {
        // saving user list
        try {
            GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
            Gson gson = builder.create();
            String userListText = gson.toJson(UserList.getUserListInstance());
            FileWriter fileWriter = new FileWriter(userListFile);
            fileWriter.write(userListText);
            fileWriter.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
