package graph;

/**
 * Created by rkalhans on 3/16/2016.
 */
public interface Plugable {

    public void forEachNode(Node n);
    public void forEachChildren(Node parent, Node children);
    void resetNode(Node n);
}
