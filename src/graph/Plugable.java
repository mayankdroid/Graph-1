package graph;

/**
 * Created by rkalhans on 3/16/2016.
 */
public interface Plugable {

    void forEachNode(Node n);
    void forEachParentChildPair(Node parent, Node child);
    void resetNode(Node n);
}
