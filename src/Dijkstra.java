import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: amudi
 * Date: 5/8/12
 * Time: 7:11 PM
 */
public class Dijkstra {

    private Graph graph;

    private HashMap<Integer, Integer> path;
    private HashMap<Integer, Integer> predecessors;
    private HashSet<Integer> determinedVertices;
    private PriorityQueue remainingVertices;

    public Dijkstra(Graph graph) {

        this.graph = graph;
        int verticesNumber = graph.getVerticesNumber();

        path = new HashMap<Integer, Integer>(verticesNumber);
        predecessors = new HashMap<Integer, Integer>(verticesNumber);
        determinedVertices = new HashSet<Integer>(verticesNumber);
        remainingVertices = new PriorityQueue();
    }

    public int getShortestDistance(int start, int destination) {

        int distance = -1;
        try {
            Path shortestPath = getShortestPath(start, destination);
            distance = graph.getEdgeWeight(shortestPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return distance;
    }

    public Path getShortestPath(int start, int destination) {

        checkVertexExist(start);
        checkVertexExist(destination);

        runDijkstraAlgorithm(start, destination);
        if (start == destination) {
            return buildShortestPath(start, destination);
        } else {
            PriorityQueue solutionsPQ = new PriorityQueue();
            Iterator<Integer> iterator = graph.getAdjacentVertices(start);
            while (iterator.hasNext()) {
                int vertex = iterator.next();
                int distFromDestVertex = graph.getEdgeWeight(start, vertex);
                runDijkstraAlgorithm(vertex, destination);
                int distance = getShortestPathFromSource(destination);
                if (distance < Integer.MAX_VALUE) {
                    solutionsPQ.insert(vertex, distFromDestVertex + distance);
                }
            }

            Path path = new Path();
            path.addVertex(start);
            path.addPath(buildShortestPath(solutionsPQ.dequeueElement(true), destination));
            return path;
        }
    }

    private void runDijkstraAlgorithm(int source, int destination) {

        path.clear();
        predecessors.clear();
        determinedVertices.clear();
        remainingVertices.clear();

        path.put(source, 0);
        remainingVertices.insert(source, 0);

        while (!remainingVertices.isEmpty()) {
            int next = remainingVertices.dequeueElement(true);
            if (next == destination) {
                break;
            }
            determinedVertices.add(next);
            relax(next);
        }
    }

    private void relax(int vertex) {

        Iterator<Integer> adjacentVertices = graph.getAdjacentVertices(vertex);
        while (adjacentVertices.hasNext()) {
            int adjacentVertex = adjacentVertices.next();
            if (!determinedVertices.contains(adjacentVertex)) {
                int distance = getShortestPathFromSource(vertex) + graph.getEdgeWeight(vertex, adjacentVertex);
                if (getShortestPathFromSource(adjacentVertex) > distance) {
                    setShortestPathFromStart(adjacentVertex, distance);
                    predecessors.put(adjacentVertex, vertex);
                    remainingVertices.insert(adjacentVertex, distance);
                }
            }
        }
    }

    private int getShortestPathFromSource(int vertex) {

        return (path.containsKey(vertex) ? path.get(vertex) : Integer.MAX_VALUE);
    }

    private void setShortestPathFromStart(int vertex, int path) {

        this.path.put(vertex, path);
    }

    private Path buildShortestPath(int start, int destination) {

        Path path = new Path();
        if (getShortestPathFromSource(destination) != Integer.MAX_VALUE) {
            ArrayList<Integer> pathList = new ArrayList<Integer>();
            int predecessor = destination;
            do {
                pathList.add(predecessor);
                if (predecessors.containsKey(predecessor)) {
                    predecessor = predecessors.get(predecessor);
                } else {
                    predecessor = -1;
                }
            } while ((predecessor != -1) && predecessor != start);

            pathList.add(start);
            Collections.reverse(pathList);
            return new Path(pathList);
        }

        return path;
    }

    private void checkVertexExist(int vertex) {

        if (!graph.vertexExist(vertex)) {
            throw new IllegalArgumentException("Vertex does not exist.");
        }
    }
}