#Graph
=======

## Summary
-----------
This is a boiler-plate for Graph related questions which are asked in most coding competitions. The current implementation enables
the user to plug-in the business logic as a saperate class, by following simple procedure. This is highly useful in scenarios where
the users want to solve a graph related problem without creating the graph related infra.

## How to use
---------------

### Creating a new graph
-------------------------
To create a new graph by creating a new graph of a given type of value. In our example we will use Integer as node values.
``` java
    final Graph<Integer> graph = new Graph<>();
```

Next up we will add some nodes or vertices here.

``` java
            // Create references of nodes to be used later
            Node n1 = new Node(1);
            Node n2 = new Node(2);
            graph.addNode(n1);
            graph.addNode(n2);

            // or simply create anonymous nodes.
            graph.addNode(new Node(3));
            graph.addNode(new Node(4));
            graph.addNode(new Node(5));
```
Next add all the edges by specifying the source and the destination nodes. You can identify the nodes by using the node ID which is basically the hashcode of the value passed to the
constructor of the node. Since we are using integer, the resultant hashcode will be the integer itself.
Alternatively you can simply give reference of the nodes itself. There is API for both.

``` java
            // add nodes by references
            graph.addEdge(n1,n2);

            // or add them by  ids.

            graph.addEdge(3,5);
            graph.addEdge(4,1);
            graph.addEdge(5,4);
            graph.addEdge(2,3);
```

### Pluging the business logic for traversal.
-----------------------------------------------
Now that we have created the nodes we can plug-in the business logic. We use Plugable interface for this. You may create a
concrete class which implements the Plugable interface or simply use anonymous class for registering the plugable action.

Here is an example of a concrete class.
 ``` java
 public class MyCustomAction implements Plugable {
             @Override
             public void forEachNode(Node n) {
                 // Do something that you want to do for each node. This will be called one for every node.
             }

             @Override
             public void forEachParentChildPair(Node parent, Node child) {
                 // Do something that you want to do with every parent child pair. This will be called for every parent child pair.
             }

             @Override
             public void resetNode(Node n) {
               // This will be called when the graph is reset. This is usually don when you abort a traversal using the abortTraversal api
               // or if you explicitly reset the graph using resetGraph() api. Here you are supposed to cleanup any additional information that
               // you may have added while traversing using the previous two callbacks. You need not reset the visited flag since it is done by
               // the framework upon resetGraph() call.
             }
         }

         // register this action with the graph.
         graph.registerPluggableAction( new MyCustomAction());
 ```

 Here is an example using an anonmyous class.

 ```java
       graph.registerPluggableAction( new Plugable(){
                     // Override all the methods here like above
       });
 ```

### Operations on the graph
------------------------------

 Currently you can do Breadth First Search and Depth First Search on the graphs.
 To do a BFS

 ```java
    graph.doBFS();
 ```

 To do a DFS
  ``` java
        graph.doDFS();
  ```

### Finishing a graph traversal prematurely.
---------------------------------------------
  In certain cases you may want to abort the traversal prematurely. e.g. You want to find the shortest path from one node to another.
  for this you do BFS starting from one node. Once you reach the destination node, there is no need to continue the traversal.
  All you have to do is to call abort traversal from one of the callbacks in the plugable interface.


``` java
     graph.abortTraversal();
```
Check the description in the section below.

### Completing the graph traversal.
-----------------------------------

#### RESET API
---------------
 You may want to do multiple traversals on the same graph. This can be done by calling graph.resetGraph(). This resets the graph
 and the graph is ready for a new graph traversal. Also ensure that resetNode callback removes all traversal related information
 from the node which you may have added dureing the traversal.

### Other Useful Apis
---------------------

 Here are a few other utility apis which are useful

#### abortTraversal
--------------------
  Aborts the current traversal when called from a callback method of your custom action. This will internally call the resetGraph() api which will remove all traversal related information from the graph. One invoked you cannot resume the
  graph traversal. Check examples/ShortestDistance.java for further details.

#### getNodeWithId
-------------------
 Returns the node reference given the hashcode of the object. check example/ShortestDistance.java for details.

##Contributors
---------------
 Rohit Kalhans <mailto:rohit.kalhans@gmail.com>

Feel free to contrubute and feedback is welcome.
