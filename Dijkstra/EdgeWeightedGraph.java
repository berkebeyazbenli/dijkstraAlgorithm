import java.util.*;

public class EdgeWeightedGraph {
    private final int V; // number of vertices
    private int E; // number of edges
    private List<Edge>[] adj; // adjacency list
    private List<String> cities; // şehir listesi

    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        this.adj = new ArrayList[V];
        this.cities = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        List<Edge> edges = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            for (Edge e : adj[v]) {
                if (e.other(v) > v) {
                    edges.add(e);
                }
            }
        }
        return edges;
    }

    public List<String> getCities() {
        return cities;
    }

    public int getCityIndex(String city) {
        int index = cities.indexOf(city);
        return index != -1 ? index : -1; // Return -1 if the city is not found
    }

    public String getCity(int index) {
        return cities.get(index);
    }

    public int addCity(String city) {
        int index = cities.indexOf(city);
        if (index == -1) {
            cities.add(city);
            return cities.size() - 1; // Return the index of the added city
        }
        return index;
    }

    public double getTotalLength(List<Edge> route) {
        double totalLength = 0;
        for (Edge edge : route) {
            totalLength += edge.weight();
        }
        return totalLength;
    }
}
