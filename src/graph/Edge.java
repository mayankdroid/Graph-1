package graph;

/**
 * Created by rkalhans on 3/19/2016.
 */
public class Edge {
    private Node otherNode;
    private double weight;

    public Edge(Node other, double weight){
        this.otherNode = other;
        this.weight = weight;
    }

    public Node getOtherNode(){return otherNode;}
    public double getWeight(){return weight;}
}
