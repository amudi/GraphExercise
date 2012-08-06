import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: amudi
 * Date: 5/8/12
 * Time: 5:40 PM
 */
public class Main {

    public static void main(String[] args) {

        Graph graph = readInputFile("graph.txt");
        Dijkstra dijkstra = new Dijkstra(graph);
        List<Integer> endpointVertices = graph.getAllEndpointVertices();

        int startVertex = 1;
        if (!graph.vertexExist(startVertex)) {
            System.out.println("vertex 1 does not exist!");
            return;
        }

        int minDistance = Integer.MAX_VALUE;
        int maxDistance = Integer.MIN_VALUE;

        for (int vertex : endpointVertices) {
            if (vertex != startVertex) {
                int shortestWeightDistance = dijkstra.getShortestDistance(startVertex, vertex);
                // TODO: find longest distance
                int longestWeightDistance = Integer.MIN_VALUE;
                if (shortestWeightDistance < minDistance) {
                    minDistance = shortestWeightDistance;
                }
                if (longestWeightDistance > maxDistance) {
                    maxDistance = longestWeightDistance;
                }
            }
        }

        System.out.printf("%d %d", minDistance, maxDistance);
    }

    private static Graph readInputFile(final String filename) {

        Graph graph = null;
        try {
            graph = new Graph(getNumberOfVertices(filename));
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] nodeData = line.split(" ");

                if (nodeData.length == 3) {
                    int vertex1 = Integer.parseInt(nodeData[0]);
                    int vertex2 = Integer.parseInt(nodeData[1]);
                    int weight = Integer.parseInt(nodeData[2]);
                    if (!graph.vertexExist(vertex1)) {
                        graph.addVertex(vertex1);
                    }
                    if (!graph.vertexExist(vertex2)) {
                        graph.addVertex(vertex2);
                    }
                    graph.addEdge(vertex1, vertex2, weight);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }

    private static int getNumberOfVertices(final String filename) {

        Set<Integer> vertices = new HashSet<Integer>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] nodeData = line.split(" ");
                if (nodeData.length == 3) {
                    int vertex1 = Integer.parseInt(nodeData[0]);
                    int vertex2 = Integer.parseInt(nodeData[1]);
                    vertices.add(vertex1);
                    vertices.add(vertex2);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vertices.size();
    }
}
