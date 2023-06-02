package controller;

import model.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataBase {

    Scanner graphScanner;
    File graphFile;
    public DataBase(String graphFilePath) {
        graphFile = new File(graphFilePath);
        try {
            graphScanner = new Scanner(graphFile);
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
    }

    public void save() {

    }

    public static void main(String[] args) {
        DataBase dataBase = new DataBase("graph.txt");
        dataBase.load();
        Manager.getManagerInstance().getMap().findShortestPath(500, 1000);
    }
}
