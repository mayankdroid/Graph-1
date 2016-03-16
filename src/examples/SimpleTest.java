package examples;

import graph.Graph;
import graph.Node;
import graph.Plugable;

public class SimpleTest {

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
        public void forEachChildren(Node parent, Node children) {
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
