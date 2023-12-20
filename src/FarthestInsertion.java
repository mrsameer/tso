import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FarthestInsertion {
    private int[][] distances;
    private List<Integer> route;
    private int kNode;

    public FarthestInsertion(int[][] distances, int startingNode) {
        this.distances = distances;
        this.route = new ArrayList<>();
        this.route.add(startingNode);
        this.route.add(getFirstNode(startingNode));
        this.route.add(startingNode);

        startAlgorithm();
    }

    public List<Integer> printRoute() {
        List<Integer> printedRoute = new ArrayList<>();
        for (int node : route) {
            printedRoute.add(node + 1);
        }
        return printedRoute;
    }

    public int totalCost() {
        int totalCost = 0;
        for (List<Integer> nodes : paths()) {
            totalCost += distances[nodes.get(0)][nodes.get(1)];
        }
        return totalCost;
    }

    private int getFirstNode(int startingNode) {
        int[] distancesFromStartingNode = distances[startingNode];
        int maxDistance = -1;
        int maxDistanceNode = -1;

        for (int i = 0; i < distancesFromStartingNode.length; i++) {
            if (i != startingNode && distancesFromStartingNode[i] > maxDistance) {
                maxDistance = distancesFromStartingNode[i];
                maxDistanceNode = i;
            }
        }

        return maxDistanceNode;
    }

    private void startAlgorithm() {
        while (route.size() - 1 < distances.length) {
            getK();
            List<Integer> path = whereToInsertK();
            insertK(path);
        }
    }

    private void insertK(List<Integer> path) {
        int pathIndex = paths().indexOf(path) + 1;
        route.add(pathIndex, kNode);
    }

    private List<Integer> whereToInsertK() {
        List<Integer> pathsDeltaF = paths().stream()
                .map(path -> distances[path.get(0)][kNode] + distances[kNode][path.get(1)] - distances[path.get(0)][path.get(1)])
                .collect(Collectors.toList());

        int indexOfMinimumDistance = pathsDeltaF.indexOf(Collections.min(pathsDeltaF));
        return paths().get(indexOfMinimumDistance);
    }

    private void getK() {
        int maxValue = 0;
        List<Integer> maxValuePath = new ArrayList<>();

        for (int i = 0; i < distances.length; i++) {
            if (!route.contains(i)) {
                for (int j = 0; j < distances[i].length; j++) {
                    if (route.contains(j)) {
                        if (distances[i][j] > maxValue) {
                            maxValue = distances[i][j];
                            maxValuePath = List.of(i, j);
                        }
                    }
                }
            }
        }

        this.kNode = maxValuePath.get(0);
    }

    private List<List<Integer>> paths() {
        List<List<Integer>> paths = new ArrayList<>();

        for (int i = 0; i < route.size() - 1; i++) {
            List<Integer> path = new ArrayList<>();
            path.add(route.get(i));
            path.add(route.get(i + 1));
            paths.add(path);
        }

        return paths;
    }
}
