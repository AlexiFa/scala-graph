# scala-graph

## ***[Subject link](./INSTRUCTIONS.md)***

## Tasks

- [x] Datastructures
- [x] Base Methods (get vertices, edges, neighbors, addEdge, removeEdge)
- [x] Types of graphs (directed, undirected, weighted)
- [x] JSON encoding/decoding
- [x] GraphViz display
- [ ] Graph algorithms
  - [x] Depth-first search (Undirected, Directed, Weighted)
  - [ ] Breadth-first search
  - [ ] Topological sort
  - [x] Cycle detection (Undirected, Directed, Weighted)
  - [ ] Floyd's algorithm
  - [ ] Dijkstra's algorithm
- [ ] App 
  - [ ] Design
  - [ ] State management
- [ ] Logging (Optional)
- [ ] Readme




## ZIO 2 Application Integration

Our graph library is integrated into a ZIO 2 application as an interactive command-line tool. 
The application allows users to create graphs, perform operations, and visualize results.

### State Management

For this application, we chose a simple state management approach where the graph state 
is passed and returned in each operation. This approach is sufficient for our current needs 
and keeps the code straightforward.

For a more complex application, we would consider using ZIO Ref for managing mutable state 
within ZIO, or ZIO Layer for dependency injection and more sophisticated state management. 
These approaches would offer better scalability and testability as the application grows.
