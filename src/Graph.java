import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amudi
 * Date: 5/8/12
 * Time: 5:01 PM
 */
public class Graph {

    private int verticesNumber;
    private HashMap<Integer, Integer> verticesMap;
    private Integer[] vertices;
    private int indexCurrentVertex;
    private int[][] adjacencyMatrix;
    private int[][] adjacencyMatrixInverse;

    public Graph(int verticesNumber) {

        if (verticesNumber < 1) {
            throw new IllegalArgumentException("Invalid verticesNumber");
        }

        this.verticesNumber = verticesNumber;
        adjacencyMatrix = new int[verticesNumber][verticesNumber];
        adjacencyMatrixInverse = new int[verticesNumber][verticesNumber];
        verticesMap = new HashMap<Integer, Integer>(verticesNumber);
        vertices = new Integer[verticesNumber];
        indexCurrentVertex = 0;
    }

    public int getVerticesNumber() {

        return verticesNumber;
    }

    public void addVertex(int vertex) throws Exception {

        if (indexCurrentVertex >= verticesNumber) {
            throw new Exception("Can't add the vertex anymore");
        }
        verticesMap.put(vertex, indexCurrentVertex);
        vertices[indexCurrentVertex] = vertex;
        indexCurrentVertex++;
    }

    public void addEdge(int startVertex, int destinationVertex, int weight) throws Exception {

        int start = getVertexIndex(startVertex);
        int destination = getVertexIndex(destinationVertex);

        if (weight <= 0) {
            throw new Exception("Weight must be > 0");
        }

        if (weight >= Integer.MAX_VALUE) {
            throw new Exception("Weight must be < " + Integer.MAX_VALUE);
        }

        adjacencyMatrix[start][destination] = weight;
        adjacencyMatrixInverse[destination][start] = weight;
    }

    public boolean vertexExist(int vertex) {

        return verticesMap.containsKey(vertex);
    }

    public int getEdgeWeight(int startVertex, int destinationVertex) {

        int start = getVertexIndex(startVertex);
        int destination = getVertexIndex(destinationVertex);

        return adjacencyMatrix[start][destination];
    }

    public int getEdgeWeight(Path path) {

        if (path.getLength() < 1) {
            return 0;
        }

        int total = 0;
        for (int i = 0; i < path.getLength(); i++) {
            int edgeWeight = getEdgeWeight(path.get(i), path.get(i + 1));
            if (edgeWeight == 0) {
                return 0;
            } else {
                total += edgeWeight;
            }
        }

        return total;
    }

    public Iterator<Integer> getAdjacentVertices(int vertex) {

        return getAdjacentVerticesFromMatrix(vertex, adjacencyMatrix).iterator();
    }

    private ArrayList<Integer> getAdjacentVerticesFromMatrix(int vertex, int[][] matrix) {

        int start = getVertexIndex(vertex);
        ArrayList<Integer> adjacentVertices = new ArrayList<Integer>();
        for (int i = 0; i < matrix.length; i++) {
            int weight = matrix[start][i];
            if (weight != 0) {
                adjacentVertices.add(getVertexObject(i));
            }
        }
        return adjacentVertices;
    }

    private int getVertexIndex(int vertex) {

        if (!verticesMap.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex does not exist in.");
        }
        return verticesMap.get(vertex);
    }

    private int getVertexObject(int index) {

        return vertices[index];
    }

    public List<Integer> getAllEndpointVertices() {

        List<Integer> endpointVertices = new ArrayList<Integer>();
        for (int i = 0; i < this.vertices.length; ++i) {
            Iterator<Integer> iterator = getAdjacentVertices(this.vertices[i]);
            if (!iterator.hasNext()) {
                endpointVertices.add(this.vertices[i]);
            }
        }
        return endpointVertices;
    }
}
