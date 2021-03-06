package examples;

/**
 * Created by rkalhans on 3/20/2016.
 */

import graph.*;

/**
 * Test class to count the number of disconnected components.
 */
public class DisconnectedComponents {

    Graph<Integer> graph;
    public DisconnectedComponents(){graph = new Graph();}

    public static void main(String[] args) {
        DisconnectedComponents test = new DisconnectedComponents();
        test.start();
    }

    private void start() {


        // construct the graph
        graph.addNode(new Node(1));
        graph.addNode(new Node(2));
        graph.addNode(new Node(3));
        graph.addNode(new Node(4));
        graph.addNode(new Node(5));
        graph.addNode(new Node(6));

        // without edges the total number of disconnected components = number of nodes.
        countDisconnectedComponents();
        graph.resetGraph();

        // add some edges. The total number of the disconnected components = 3.
        graph.addEdge(1,2);
        graph.addEdge(1,3);
        graph.addEdge(2,3);
        graph.addEdge(5,6);
        countDisconnectedComponents();
    }

    void countDisconnectedComponents(){
        int disconnectedComponents = 0;
        Node<Integer> startNode = graph.getNextUnVisitedNode();
        while( startNode != null){
            graph.doBFS(startNode);
            disconnectedComponents++;
            startNode = graph.getNextUnVisitedNode();
        }
        System.out.println("# of disconnected components: " + disconnectedComponents);
    }
}
