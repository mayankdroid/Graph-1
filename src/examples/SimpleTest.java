package examples;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by rkalhans on 3/16/2016.
 */
public class SimpleTest {
    //<editor-fold desc="Generated Code For Graph Infra using github.com/rkalhans/Graph Do not edit inside this fold" defaultstate="collapsed">
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
                }
                @Override
                public void forEachParentChildPair(Node parent, Node child) {
                }
                @Override
                public void resetNode(Node n) {
                }
            };
        }
        public void addNode(Node<T> value) {
            nodes.put(value.getId(), value);
            nodesList.add(value);
        }
        public void addDirectedEdge(Node source, Node destination, double weight) {
            nodes.get(source.getId()).addOutEdge(destination, weight);
            nodes.get(destination.getId()).addInEdge(source, weight);
        }
        public void addEdge(Node source, Node destination, double weight) {
            addDirectedEdge(source, destination, weight);
            addDirectedEdge(destination, source, weight);
        }
        public void addDirectedEdge(int source, int destination) {
            nodes.get(source).addOutEdge(nodes.get(destination));
            nodes.get(destination).addInEdge(nodes.get(source));
        }
        public void addEdge(int source, int destination) {
            addDirectedEdge(source, destination);
            addDirectedEdge(destination, source);
        }
        public void addDirectedEdge(Node source, Node destination) {
            nodes.get(source.getId()).addOutEdge(destination);
            nodes.get(destination.getId()).addInEdge(source);
        }
        public void addEdge(Node source, Node destination) {
            addDirectedEdge(source, destination);
            addDirectedEdge(destination, source);
        }
        public void addDirectedEdge(int source, int destination, double weight) {
            nodes.get(source).addOutEdge(nodes.get(destination), weight);
            nodes.get(destination).addInEdge(nodes.get(source), weight);
        }
        public void addEdge(int source, int destination, double weight) {
            addDirectedEdge(source, destination);
            addDirectedEdge(destination, source);
        }
        public void resetGraph() {
            it = nodesList.iterator();
            abortTraversal = false;
            for (Node<T> node : nodesList) {
                node.reset();
                if (action != null)
                    action.resetNode(node);
            }
        }
        public void registerPluggableAction(Plugable action) {
            this.action = action;
        }
        public void doBFS(Node<T> source) {
            TraversableQueue<T> queue = new TraversableQueue<>();
            doTraversal(source, queue);
        }
        public void doDFS(Node<T> source) {
            TraversableStack stack = new TraversableStack();
            doTraversal(source, stack);
        }
        private void doTraversal(Node<T> source, Traversable childrenQueue) {
            childrenQueue.insert(source);
            while (!childrenQueue.isEmpty() && !abortTraversal) {
                Node<T> node = childrenQueue.getNext();
                action.forEachNode(node);
                node.setVisited();
                for (Edge edge : node.getAllChildren()) {
                    Node<T> child = edge.getOtherNode();
                    if (child.isVisited()) continue;
                    action.forEachParentChildPair(node, child);
                    childrenQueue.insert(child);
                    child.setVisited();
                }
            }
            if(abortTraversal){
                resetGraph();
            }
        }
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
        public void abortTraversal() {
            this.abortTraversal = true;
        }
        public Node<T> getNodeWithId(int id) {
            return nodes.get(id);
        }
    }
    public class Node <T> {
        private T value;
        private List<Edge> fromNodes;
        private List<Edge> toNodes;
        private boolean visited;
        public Node(T value){
            this.value = value;
            this.fromNodes = new ArrayList<>();
            this.toNodes = new ArrayList<>();
        }
        public void addInEdge(Node n, double weight){
            fromNodes.add( new Edge(n, weight));
        }
        public void addOutEdge(Node n, double weight){
            toNodes.add(new Edge(n, weight));
        }
        public void addInEdge(Node n){
            fromNodes.add( new Edge(n, 1));
        }
        public void addOutEdge(Node n){
            toNodes.add(new Edge(n,1));
        }
        public void addEdge(Node n){
            addInEdge(n);
            addOutEdge(n);
        }
        public int getInDegree(){return  fromNodes.size();}
        public int getOutDegree(){return  toNodes.size();}
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
        public boolean isVisited(){return visited;}
        public void setVisited(){visited = true;}
        public void reset(){visited=false;}
        public int getId(){return value.hashCode();}
        public T getValue() {
            return value;
        }
        public List<Edge> getAllChildren(){
            return toNodes;
        }
        @Override
        public String toString(){
            return value.toString();
        }
    }
    public interface Plugable {
        void forEachNode(Node n);
        void forEachParentChildPair(Node parent, Node child);
        void resetNode(Node n);
    }
    public interface Traversable {
        Node getNext();
        void insert(Object o);
        boolean isEmpty();
    }
    public class TraversableQueue<T> extends LinkedList<Node<T>> implements Traversable {
        @Override
        public Node<T> getNext() {
            Node<T> node = this.peek();
            this.pop();
            return node;
        }
        @Override
        public void insert(Object o) {
            if(o instanceof Node){
                this.add((Node<T>)o);
            }else
                throw new IllegalArgumentException("The parameter provided to insert is not correct");
        }
    }
    public class TraversableStack <T> extends Stack<Node<T>> implements Traversable {
        @Override
        public Node<T> getNext() {
            Node<T> node = this.peek();
            this.pop();
            return node;
        }
        @Override
        public void insert(Object node) {
            if(node instanceof Node){
                Node<T> addNode = (Node<T>)node;
                this.add(addNode);
            }
            else
                throw new IllegalArgumentException("The parameter provided to insert is not correct");
        }
    }
    //</editor-fold>
    public static void main(String[] args) {
        SimpleTest test = new SimpleTest();
        test.start();
    }

    private void start() {
        Graph<Student> graph = new Graph();
        graph.registerPluggableAction(new PrintAction());

        // construct the graph
        graph.addNode(new Node(new Student(1, "Rohit")));
        graph.addNode(new Node(new Student(2, "Rashi")));
        graph.addNode(new Node(new Student(3, "Neeraj")));
        graph.addNode(new Node(new Student(4, "Rocky")));
        graph.addNode(new Node(new Student(5, "Saurabh")));
        graph.addNode(new Node(new Student(6, "Jasmine")));

        graph.addEdge(1,2);
        graph.addEdge(1,3);
        graph.addEdge(2,3);
        graph.addEdge(2,4);
        graph.addEdge(5,6);

        int disconnectedComponents = 0;
        Node<Student> startNode = graph.getNextUnVisitedNode();

        System.out.println("==================================================");
        System.out.println("========== Starting BFS on the graph =============");
        System.out.println("==================================================");
        while( startNode != null){
            graph.doBFS(startNode);
            disconnectedComponents++;
            System.out.println("No. of disconnected components completed: " + disconnectedComponents);
            startNode = graph.getNextUnVisitedNode();
        }

        // reset the graph for next traversal
        graph.resetGraph();
        disconnectedComponents = 0;

        System.out.println("====================================================");
        System.out.println("===== Starting DFS after resetting the graph =======");
        System.out.println("====================================================");
        startNode = graph.getNextUnVisitedNode();
        while( startNode != null){
            graph.doDFS(startNode);
            disconnectedComponents++;
            System.out.println("No. of disconnected components completed: " + disconnectedComponents);
            startNode = graph.getNextUnVisitedNode();
        }

    }

    static class Student{
        int id;
        String name;
        public Student(int id, String name){this.name= name; this.id = id;}
        @Override
        public String toString(){
            return id+" => "+name;
        }

        @Override
        public int hashCode(){return id;}
        @Override
        public boolean  equals( Object other){
            Student s = (Student) other;
            return id == s.id ;
        }
    }

    static class PrintAction implements Plugable{

        @Override
        public void forEachNode(Node n) {
            Node<Student> student = (Node<Student>)n;
            System.out.println("Current Node :"+student.getValue().name);
        }

        @Override
        public void forEachParentChildPair(Node parent, Node children) {
            Node<Student> studentParent = (Node<Student>)parent;
            Node<Student> studentChild = (Node<Student>)children;
//            System.out.println("Parent: "+studentParent.getValue().name +" Child: "+studentChild.getValue().name);
        }

        @Override
        public void resetNode( Node n) {
            // noop
        }
    }
}
