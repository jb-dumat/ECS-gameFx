import java.util.ArrayList;
import java.util.List;

public class Graph<T> {
    Graph() {
        entry = new Node<T>();
    }

    public Node<T> getEntry() {
        return entry;
    }

    private Node<T> searchRecursive(Node<T> node, T data) {
        if (node.getData() == data) {
            return node;
        }

        for (Node<T> neighbour : node.getNeighbours()) {
            this.searchRecursive(neighbour, data);
        }

        return null;
    }

    public Node<T> search(T data) {
        return this.searchRecursive(entry, data);
    }

    private Node<T> entry;

    public static class Node<T> {
        Node() {
            neighbours = new ArrayList<Node<T>>();
        }

        Node(T data) {
            neighbours = new ArrayList<Node<T>>();
            this.data = data;
        }


        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public void addNeighbour(Node<T> neighbour) {
            for (Node<T> iter : neighbours) {
                if (iter == neighbour)
                    return;
            }
            neighbours.add(neighbour);
        }

        public void removeNeighbour(Node<T> neighbour) {
            neighbours.remove(neighbour);
        }

        public List<Node<T>> getNeighbours() {
            return this.neighbours;
        }

        private List<Node<T>> neighbours;
        private T data;
    }
}