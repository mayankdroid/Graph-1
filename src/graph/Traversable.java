package graph;

import java.util.Objects;

/**
 * Created by rkalhans on 3/16/2016.
 */
public interface Traversable {

    Node getNext();
    void insert(Object o);
    boolean isEmpty();
}
