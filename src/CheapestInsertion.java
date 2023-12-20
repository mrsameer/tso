import java.util.ArrayList;
import java.util.List;

public class CheapestInsertion {

    private int[][] distances;
    private List<Integer> route;
    private int kNode;

    public CheapestInsertion(int[][] distances, int startingNode) {
        this.distances = distances;
        this.route = new ArrayList<>();
        this.route.add(startingNode);
        this.route.add(getFirstNode(startingNode));
        this.route.add(startingNode);

        startAlgorithm();
    }

    public List<Integer> getRoute() {
        return route;
    }

    public int getTotalCost() {
        int totalCost = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            totalCost += distances[route.get(i)][route.get(i + 1)];
        }
        return totalCost;
    }

    private List<Integer> possibleKNodes() {
        List<Integer> allNodes = new ArrayList<>();
        for (int i = 0; i < distances.length; i++) {
            allNodes.add(i);
        }

        List<Integer> uniqueNodes = new ArrayList<>();
        for (int node : route) {
            if (!uniqueNodes.contains(node)) {
                uniqueNodes.add(node);
            }
        }

        allNodes.removeAll(uniqueNodes);
        return allNodes;
    }

    private void startAlgorithm() {
        List<Integer> possibleKNodes = possibleKNodes();

        while (!possibleKNodes.isEmpty()) {
            int[] path = whereToInsertK();
            insertK(path);
            possibleKNodes = possibleKNodes();
        }
    }

    private int[] whereToInsertK() {
        List<int[]> kNodes = new ArrayList<>();

        for (int kNode : possibleKNodes()) {
            for (int i = 0; i < route.size() - 1; i++) {
                int[] path = { route.get(i), route.get(i + 1) };
                int deltaF = distances[path[0]][kNode] + distances[kNode][path[1]] - distances[path[0]][path[1]];
                kNodes.add(new int[]{ kNode, deltaF, path[0], path[1] });
            }
        }

        int[] minimumDeltaF = kNodes.stream().min((a, b) -> Integer.compare(a[1], b[1])).orElse(new int[0]);
        kNode = minimumDeltaF[0];
        return new int[]{ minimumDeltaF[2], minimumDeltaF[3] };
    }

    private void insertK(int[] path) {
        int pathIndex = route.indexOf(path[0]) + 1;
        route.add(pathIndex, kNode);
    }

    private int getFirstNode(int startingNode) {
        int[] distancesFromStartingNode = distances[startingNode];
        int minDistance = Integer.MAX_VALUE;
        int minDistanceIndex = -1;

        for (int i = 0; i < distancesFromStartingNode.length; i++) {
            if (i != startingNode && distancesFromStartingNode[i] > 0 && distancesFromStartingNode[i] < minDistance) {
                minDistance = distancesFromStartingNode[i];
                minDistanceIndex = i;
            }
        }

        return minDistanceIndex;
    }

    public static void main(String[] args) {
        int[][] distances = {
                {0, 3, 5, 8},
                {3, 0, 6, 7},
                {5, 6, 0, 9},
                {8, 7, 9, 0}
        };

        CheapestInsertion cheapestInsertion = new CheapestInsertion(distances, 0);

        System.out.println("Cheapest Insertion Route: " + cheapestInsertion.getRoute());
        System.out.println("Cheapest Insertion Total Cost: " + cheapestInsertion.getTotalCost());
    }
}
