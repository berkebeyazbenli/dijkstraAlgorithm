import java.util.*;

public class DijkstraSP {
    private EdgeWeightedGraph G;
    private double[] distTo;
    private Edge[] edgeTo;
    private IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedGraph G, String startCity) {
        this.G = G;
        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];
        pq = new IndexMinPQ<>(G.V());

        int startVertex = G.getCityIndex(startCity);
        validateVertex(startVertex);

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[startVertex] = 0.0;

        pq.insert(startVertex, distTo[startVertex]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e : G.adj(v)) {
                relax(e);
            }
        }
    }

    private void relax(Edge e) {
        int v = e.getCity1Index();
        int w = e.getCity2Index();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) {
                pq.decreaseKey(w, distTo[w]);
            } else {
                pq.insert(w, distTo[w]);
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<Edge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Edge> path = new Stack<>();
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.either()]) {
            path.push(e);
        }
        return path;
    }

    public Iterable<Edge> pathTo(String city) {
        int cityIndex = G.getCityIndex(city);
        return pathTo(cityIndex);
    }

    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("");
        }
    }
}
