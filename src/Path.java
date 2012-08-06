import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: amudi
 * Date: 5/8/12
 * Time: 6:57 PM
 */
public class Path {

    private ArrayList<Integer> vertices;

    public Path() {

        vertices = new ArrayList<Integer>();
    }

    public Path(ArrayList<Integer> path) {

        vertices = path;
    }

    public int getLength() {

        return vertices.size() - 1;
    }

    public Path addVertex(int vertex) {

        vertices.add(vertex);
        return this;
    }

    public Path addPath(Path path) {

        vertices.addAll(path.vertices);
        return this;
    }

    public int get(int index) {

        return vertices.get(index);
    }
}
