import java.util.NoSuchElementException;

public class IndexMinPQ<Key extends Comparable<Key>> {
    private int maxN;
    private int N;
    private int[] pq; // binary heap using 1-based indexing
    private int[] qp; // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
    private Key[] keys; // keys[i] = priority of i

    public IndexMinPQ(int maxN) {
        if (maxN < 0)
            throw new IllegalArgumentException();
        this.maxN = maxN;
        keys = (Key[]) new Comparable[maxN + 1];
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        for (int i = 0; i <= maxN; i++) {
            qp[i] = -1;
        }
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public boolean contains(int i) {
        return qp[i] != -1;
    }

    public void insert(int i, Key key) {
        if (contains(i))
            throw new IllegalArgumentException("Index already exists");
        N++;
        qp[i] = N;
        pq[N] = i;
        keys[i] = key;
        swim(N);
    }

    public int delMin() {
        if (isEmpty())
            throw new NoSuchElementException("Priority queue underflow");
        int minIndex = pq[1];
        swap(1, N--);
        sink(1);
        qp[minIndex] = -1;
        keys[pq[N + 1]] = null; // avoid loitering
        pq[N + 1] = -1; // avoid loitering
        return minIndex;
    }

    public void decreaseKey(int i, Key key) {
        if (!contains(i))
            throw new NoSuchElementException("Index does not exist");
        if (keys[i].compareTo(key) <= 0)
            throw new IllegalArgumentException("New key is not smaller than the current key");
        keys[i] = key;
        swim(qp[i]);
    }

    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            swap(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && greater(j, j + 1))
                j++;
            if (!greater(k, j))
                break;
            swap(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    private void swap(int i, int j) {
        int temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    public void change(int i, Key key) {
        if (!contains(i))
            throw new NoSuchElementException("Index does not exist");
        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }
}
