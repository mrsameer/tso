import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Example distances matrix
//        int[][] distances = {
//                {0, 2, 9, 10},
//                {1, 0, 6, 4},
//                {15, 7, 0, 8},
//                {6, 3, 12, 0}
//        };
//        int[][] distances = {
//                {0, 2, 9, 10},
//                {1, 0, 6, 4},
//                {15, 7, 0, 8},
//                {6, 3, 12, 0}};
        int[][] distances =
                {
                        {0,3,5,8},
                        {3,0,6,7},
                        {5,6,0,9},
                        {8,7,9,0}
                };


        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter starting node for Farthest Insertion:");
        int startingNode = scanner.nextInt();

        FarthestInsertion farthestInsertion = new FarthestInsertion(distances, startingNode);

        System.out.println("Farthest Insertion Route: " + farthestInsertion.printRoute());
        System.out.println("Farthest Insertion Total Cost: " + farthestInsertion.totalCost());
    }
}
