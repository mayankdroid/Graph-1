package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rkalhans on 3/16/2016.
 */
public class Node <T> {

    // Value to be stored in the node. All additional data required can be stored in this object.
    private T value;
    // list of nodes from where the edges are coming in.
    private List<Node<T>> fromNodes;
    // list of nodes to which the out nodes are going to.
    private List<Node<T>> toNodes;
    // has this node been visited during a traversal.
    private boolean visited;

    /**
     * Constructor which takes in the value and constructs a node.
     * @param value
     */
    public Node(T value){
        this.value = value;
        this.fromNodes = new ArrayList<>();
        this.toNodes = new ArrayList<>();
    }

    /**
     * add an inward edge.
     * @param n
     */

    public void addInEdge(Node n){
        fromNodes.add(n);
    }

    /**
     * add an outward edge
     * @param n
     */

    public void addOutEdge(Node n){
        toNodes.add(n);
    }

    /**
     * This is useful for undirected graph where nodes do not have any direction. We will implement this as a bidirectional
     * graph which has two edges between every connected nodes in each direction.
     * @param n
     */
    public void addEdge(Node n){
        addInEdge(n);
        addOutEdge(n);
    }

    /**
     * get in degree of this node.
     * @return
     */
    public int getInDegree(){return  fromNodes.size();}

    /**
     * get outDegree of this node.
     * @return
     */
    public int getOutDegree(){return  toNodes.size();}

    /**
     *  get degree of a node in case of an undirected graph.
     * @return
     */
    public int getDegree(){
        try {
            assert toNodes.size() == fromNodes.size();
        }
        catch (AssertionError ex){
            throw new RuntimeException("Cannot get the degree of this graph since in-degree is not the same as out-degree." +
                    " If this is a directed graph use getInDegree() and getOutDegree()", ex);
        }
        return toNodes.size();
    }

    /**
     * Has the node been visited before in any traversal. Reset this using reset method.
     * @return true if the node has been visited and false otherwise.
     */
    public boolean isVisited(){return visited;}

    /**
     * Mark the node as visited.
     */

    public void setVisited(){visited = true;}

    /**
     *  Reset this node for next traversal;
     */
    public void reset(){visited=false;}

    /**
     *  To ensure that we use uniform ID to hash the nodes and to support multiple nodes with the same values.
     *  In your value override the hashcode and equals.  If you are using unique integer values, no action is required.
     * @return Id.
     */
    public int getId(){return value.hashCode();}

    /**
     * Get the value from the node.
     * @return the value of the node.
     */
    public T getValue() {
        return value;
    }

    /**
     * @return list of all the children.
     */
    public List<Node<T>> getAllChildren(){
        return toNodes;
    }

    /**
     * Overidden to string method for better logging.
     * @return
     */
    @Override
    public String toString(){
        return value.toString();
    }
}
