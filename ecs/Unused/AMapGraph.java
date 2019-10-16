public abstract class AMapGraph<T> {
    AMapGraph() {
        this.map = new Graph<T>();
    }

    public abstract void createMapGraph();

    public Graph<T> map;
}
