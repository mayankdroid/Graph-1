package graph;

import java.util.IllegalFormatCodePointException;
import java.util.Stack;

/**
 * Created by rkalhans on 3/16/2016.
 */
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
