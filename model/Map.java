package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Map {
    public Map(int numberOfNodes, int numberOfEdges) {
        this.numberOfNodes = numberOfNodes;
        this.numberOfEdges = numberOfEdges;
        adjacencyMatrix = new int[numberOfNodes][numberOfNodes];
        for(int i = 0; i < numberOfNodes; ++i) {
            for(int j = 0; j < numberOfNodes; ++j) {
                adjacencyMatrix[i][j] = 0;
            }
        }
    }
    private final int numberOfNodes;
    private final int numberOfEdges;
    private final int[][] adjacencyMatrix;

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }
    public void addEdge(int firstNode, int secondNode, int edgesWeight) {
        adjacencyMatrix[firstNode - 1][secondNode - 1] = edgesWeight;
        adjacencyMatrix[secondNode - 1][firstNode - 1] = edgesWeight;
    }

    public void findShortestPath(int origin, int destination) {
        HashMap<Integer, Integer> nodeValues = new HashMap<>();
        HashMap<Integer, Integer> previousNodes = new HashMap<>();
        HashSet<Integer> checkedNodes = new HashSet<>();

        int INFINITE = Integer.MAX_VALUE;

        for(int i = 1; i <= numberOfNodes; ++i) {
            if(i != origin) {
                nodeValues.put(i, INFINITE);
            } else {
                nodeValues.put(i, 0);
            }
        }

        for(int i = 1; i <= numberOfNodes; ++i) {
            if(i != origin) {
                previousNodes.put(i, -1);
            } else {
                previousNodes.put(i, origin);
            }
        }

        int checkingNode = origin;

        while (checkingNode != destination) {
            checkedNodes.add(checkingNode);
            for(int i = 0; i < numberOfNodes; ++i) {
                if(adjacencyMatrix[checkingNode - 1][i] != 0) {
                    if(nodeValues.get(checkingNode) + adjacencyMatrix[checkingNode - 1][i] < nodeValues.get(i + 1)) {
                        nodeValues.replace(i + 1, nodeValues.get(checkingNode) + adjacencyMatrix[checkingNode - 1][i]);
                        previousNodes.replace(i + 1, checkingNode);
                    }
                }
            }

            int min = INFINITE;

            for(int i = 1; i <= numberOfNodes; ++i) {
                if(nodeValues.get(i) <= min && !checkedNodes.contains(i)) {
                    min = nodeValues.get(i);
                    checkingNode = i;
                }
            }
        }

        System.out.println(nodeValues.get(destination));
        int index = destination;
        ArrayList<Integer> path = new ArrayList<>();
        while(index != origin) {
            path.add(index);
            index = previousNodes.get(index);
        }
        System.out.print(origin + " -> ");
        for(int i = path.size() - 1; i >= 0; --i) {
            System.out.print(path.get(i));
            if(i != 0) {
                System.out.print(" -> ");
            }
        }
    }
}
