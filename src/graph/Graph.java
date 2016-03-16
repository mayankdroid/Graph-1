package graph;

import examples.ShortestDistance;

import java.util.*;

/**
 * Created by rkalhans on 3/16/2016.
 */

public class Graph<T> {
    Map<Integer, Node<T>> nodes;
    List<Node<T>> nodesList;
    private Iterator<Node<T>> it;
    private Plugable action;
    private boolean abortTraversal;

    public Graph() {
        nodes = new HashMap<>();
        nodesList = new ArrayList<>();
        action = new Plugable() {
            @Override
            public void forEachNode(Node n) {
                // noop
            }

            @Override
            public void forEachChildren(Node parent, Node children) {
                // noop
            }

            @Override
            public void resetNode(Node n) {
              //noop
            }
        };
    }

    /**
     * @param value
     */
    public void addNode(Node<T> value) {
        nodes.put(value.getId(), value);
        nodesList.add(value);
    }

    /**
     * Adds a directed edge between two nodes in this graph.
     * A -> B ( A is the source node and B is the destination node.)
     * @param source source node
     * @param destination destination node.
     */

    public void addDirectedEdge(Node source, Node destination) {
        nodes.get(source.getId()).addOutEdge(destination);
        nodes.get(destination.getId()).addInEdge(source);
    }

    /**
     * Adds an undirected Node between source and destination.
     * We achieve undirected edges by creating two directed edges between A->B and A <- B
     *
     * @param source
     * @param destination
     */
    public void addEdge(Node source, Node destination) {
        addDirectedEdge(source, destination);
        addDirectedEdge(destination, source);
    }

    /**
     * Add a directed edge between two node based on their ID
     *
     * @param source
     * @param destination
     */
    public void addDirectedEdge(int source, int destination) {
        nodes.get(source).addOutEdge(nodes.get(destination));
        nodes.get(destination).addInEdge(nodes.get(source));
    }

    /**
     * Add Undirected edge between two nodes based on the id.
     *
     * @param source
     * @param destination
     */
    public void addEdge(int source, int destination) {
        addDirectedEdge(source, destination);
        addDirectedEdge(destination, source);
    }


    /**
     * Cleans all nodes of their traversal related information.
     * Custom information stored in the value should be cleaned using the reset callback
     */
    public void resetGraph() {
        it = nodesList.iterator();
        abortTraversal = false;
        for (Node<T> node : nodesList) {
            node.reset();
            if (action != null)
                action.resetNode(node);
        }
    }

    /**
     * Register a plugable action for this graph.
     * @param action
     */
    public void registerPluggableAction(Plugable action) {
        this.action = action;
    }

    /**
     * Execute Breadth First Search  for this graph
     * @param source
     */

    public void doBFS(Node<T> source) {
        TraversableQueue<T> queue = new TraversableQueue<>();
        doTraversal(source, queue);
    }

    /**
     * Execute Depth First Search for this graph.
     * @param source
     */
    public void doDFS(Node<T> source) {
        TraversableStack stack = new TraversableStack();
        doTraversal(source, stack);
    }

    /**
     * Traversal across the BFS.
     *
     * @param source
     * @param childrenQueue
     */
    private void doTraversal(Node<T> source, Traversable childrenQueue) {
        childrenQueue.insert(source);
        while (!childrenQueue.isEmpty() && !abortTraversal) {
            Node<T> node = childrenQueue.getNext();
            action.forEachNode(node);
            node.setVisited();
            for (Node<T> child : node.getAllChildren()) {
                if (child.isVisited()) continue;
                action.forEachChildren(node, child);
                childrenQueue.insert(child);
                child.setVisited();
            }
        }

        if(abortTraversal){
            // The traversal was aborted based on the user request. No need to empty the queue. it will be discarded after the use.
            resetGraph();
        }
    }

    /**
     * Get the next unvisited node.
     * @return
     */

    public Node<T> getNextUnVisitedNode() {
        if (it == null) it = nodesList.iterator();
        try {
            it.hasNext();
        } catch (ConcurrentModificationException ex) {
            it = nodesList.iterator();
        } finally {
            while (it.hasNext()) {
                Node<T> n = it.next();
                if (!n.isVisited())
                    return n;
            }
            return null;
        }
    }

    /**
     * Call this to abort traversal. Post this the graph will be ready for the next traversal in a completely fresh manner.
     * ResetGraph() is an idempotent call. So you should be fine to abort the traversal and then reset the graph.
     */
    public void abortTraversal() {
        this.abortTraversal = true;
    }

    /**
     * get the node with the given ID.
     * @param id
     * @return
     */
    public Node<T> getNodeWithId(int id) {
        return nodes.get(id);
    }
}
