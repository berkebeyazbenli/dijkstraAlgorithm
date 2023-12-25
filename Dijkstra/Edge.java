public class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;
    private final String city1;
    private final String city2;

    public Edge(int v, int w, double weight, String city1, String city2) {
        this.v = v;
        this.w = w;
        this.weight = weight;
        this.city1 = city1;
        this.city2 = city2;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v)
            return w;
        else if (vertex == w)
            return v;
        else
            throw new IllegalArgumentException("Invalid vertex");
    }

    public double weight() {
        return weight;
    }

    public String getCity1() {
        return city1;
    }

    public String getCity2() {
        return city2;
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return String.format("%s-%s %.2f", city1, city2, weight);
    }

    public int getCity1Index() {
        return v;
    }

    public int getCity2Index() {
        return w;
    }
}
