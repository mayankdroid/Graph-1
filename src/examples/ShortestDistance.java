package examples;

import graph.Graph;
import graph.Node;
import graph.Plugable;

/**
 * Created by rkalhans on 3/16/2016.
 */
public class ShortestDistance {

    public static void main(String[] args) {
        ShortestDistance sd = new ShortestDistance();
        sd.start();
    }

    private void start() {
        final Graph<NodeValue> graph = new Graph<>();
        graph.addNode(new Node(new NodeValue(1)));
        graph.addNode(new Node(new NodeValue(2)));
        graph.addNode(new Node(new NodeValue(3)));
        graph.addNode(new Node(new NodeValue(4)));
        graph.addNode(new Node(new NodeValue(5)));

        // Add all the endges.

        graph.addEdge(1,2);
        graph.addEdge(3,5);
        graph.addEdge(4,1);
        graph.addEdge(5,4);
        graph.addEdge(2,3);

        // Let's say we want to find the shortest distance from Node 1 to node 3.
        // There are two paths between them namely 1 -> 2 -> 3 and 1 -> 4 -> 5 -> 3;
        // We should output 2 since that is the minimal distance assuming each edge has length 1.
        final int source = 1;
        final int destination = 3;

        graph.registerPluggableAction(new Plugable() {

            @Override
            public void forEachNode(Node n) {
               // noop
            }

            @Override
            public void forEachChildren(Node parent, Node children) {
                Node<NodeValue>childNode = (Node<NodeValue>)children;
                Node<NodeValue>parentNode = (Node<NodeValue>)parent;
                int nodeDistance = parentNode.getValue().distance+1;
                if(childNode.getValue().nodeId == destination) {
                    System.out.println("The shortest distance between "+source+" node and the "+destination+
                            " node is "+ nodeDistance );
                    graph.abortTraversal();
                }
                else
                childNode.getValue().distance = nodeDistance;
            }

            @Override
            public void resetNode(Node n) {
                // reset the distance.
                ((Node<NodeValue>)n).getValue().distance = 0;
            }
        });

        // do a BFS for the shortest distance starting from 1.
        graph.doBFS(graph.getNodeWithId(1));

    }
    class NodeValue{
        int nodeId;
        int distance;
        public NodeValue(int _id){
            this.nodeId = _id;
            this.distance = 0;
        }
        @Override
        public int hashCode(){ return nodeId;}

        @Override
        public boolean equals(Object other){
            NodeValue value = (NodeValue) other;
            return nodeId == value.nodeId;
        }
    }
}
