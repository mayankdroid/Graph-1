package graph;

import java.util.LinkedList;

/**
 * Created by rkalhans on 3/16/2016.
 */
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
