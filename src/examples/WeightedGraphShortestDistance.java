package examples;

import graph.*;

/**
 * Created by rkalhans on 3/20/2016.
 */
public class WeightedGraphShortestDistance {

    public static void main(String[] args) {
        WeightedGraphShortestDistance wgds = new WeightedGraphShortestDistance();
        wgds.start();
    }

    void start() {
        final Graph<NodeValue> graph = new Graph<>();
        graph.addNode(new Node<>(new NodeValue(1)));
        graph.addNode(new Node(new NodeValue(2)));
        graph.addNode(new Node(new NodeValue(3)));
        graph.addNode(new Node(new NodeValue(4)));
        graph.addNode(new Node(new NodeValue(5)));
        graph.addNode(new Node(new NodeValue(6)));
        graph.addNode(new Node(new NodeValue(7)));

        graph.addEdge(1,2,3);
        graph.addEdge(7,4,2);
        graph.addEdge(2,7,10);
        graph.addEdge(1,3,7);
        graph.addEdge(7,3,2);
        graph.addEdge(7,5,1);
        graph.addEdge(3,6,10);
        graph.addEdge(5,6,1);
        graph.registerPluggableAction(new Plugable() {
            @Override
            public void forEachNode(Node n) {
                // noop
            }

            @Override
            public boolean forEachParentChildPair(Node parent, Node child, double distance) {
                Node<NodeValue> parentNode = (Node<NodeValue>)parent;
                Node<NodeValue> childNode = (Node<NodeValue>)child;
                if(parentNode.getValue().distance + distance < childNode.getValue().distance ||
                        childNode.getValue().distance  == -1)
                {
                    childNode.getValue().distance = parentNode.getValue().distance + (int)distance;
                    return true;
                }
                else return false;
            }

            @Override
            public void resetNode(Node n) {
                ((Node<NodeValue>)n).getValue().distance=0;
            }
        });

        Node<NodeValue> sourceNode = graph.getNodeWithId(1);
        Node<NodeValue> destNode = graph.getNodeWithId(6);
        sourceNode.getValue().distance = 0;
        graph.doBFS(sourceNode);
        System.out.println("Shortest distance between "+sourceNode.getId()+" and "+destNode.getId()+" is :"+destNode.getValue().distance);
    }

    class NodeValue{
        int nodeId;
        int distance;
        public NodeValue(int _id){
            this.nodeId = _id;
            this.distance = -1;
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
