import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EdgeWeightedGraph graph = buildGraph(scanner.nextLine()); // graph'ı tanımla

        String startCity = scanner.nextLine();
        String endCity = scanner.nextLine();

        int numCitiesToVisit = scanner.nextInt();
        scanner.nextLine();

        List<String> citiesToVisit = new ArrayList<>();
        for (int i = 0; i < numCitiesToVisit; i++) {
            citiesToVisit.add(scanner.nextLine());
        }

        DijkstraSP dijkstraSP1 = new DijkstraSP(graph, startCity); // Eskişehir'den Cankiri'ye

        DijkstraSP dijkstraSP2;
        if (citiesToVisit.isEmpty()) {
            dijkstraSP2 = new DijkstraSP(graph, endCity); // Eğer ziyaret edilecek şehir yoksa direkt olarak endCity'e
                                                          // git
        } else {
            dijkstraSP2 = new DijkstraSP(graph, citiesToVisit.get(0)); // Cankiri'den Kastamonu'ya
        }

        List<Edge> route1 = getRoute(dijkstraSP1, citiesToVisit.isEmpty() ? endCity : citiesToVisit.get(0), graph);
        List<Edge> route2 = getRoute(dijkstraSP2, endCity, graph);

        // Combine the routes
        List<Edge> route = new ArrayList<>(route1);
        route.addAll(route2);

        // Print the route
        // Print the route
        if (!route.isEmpty()) {
            System.out.print("Routes are: ");
            System.out.println();
            int routeLength = 0;
            for (Edge edge : route) {
                System.out.print(edge.getCity1() + "-");
                routeLength += edge.weight();
            }
            System.out.println(endCity);
            System.out.println("Length of route is: " + routeLength);
        } else {
            System.out.println(".");
        }

    }

    private static List<Edge> getRoute(DijkstraSP dijkstraSP, String destinationCity, EdgeWeightedGraph graph) {
        List<Edge> route = new ArrayList<>();
        int endIndex = graph.getCityIndex(destinationCity);

        Iterable<Edge> path = dijkstraSP.pathTo(endIndex);
        if (path != null) {
            route.addAll((Collection<? extends Edge>) path);
        } else {
            System.out.println("No route found to destination city: " + destinationCity);
            System.exit(0); // Exit the program if there's no route to the destination city
        }
        return route;
    }

    private static EdgeWeightedGraph buildGraph(String filename) {
        EdgeWeightedGraph graph = new EdgeWeightedGraph(100); // Assuming a maximum of 100 vertices

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String city1 = parts[0].trim();
                String city2 = parts[1].trim();
                double weight = Double.parseDouble(parts[2].trim());

                int index1 = graph.getCityIndex(city1);
                if (index1 == -1) {
                    index1 = graph.addCity(city1);
                }

                int index2 = graph.getCityIndex(city2);
                if (index2 == -1) {
                    index2 = graph.addCity(city2);
                }

                // Use the correct constructor with five parameters
                graph.addEdge(new Edge(index1, index2, weight, city1, city2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return graph;
    }
}
