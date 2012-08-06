import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: amudi
 * Date: 5/8/12
 * Time: 7:16 PM
 */
public class PriorityQueue {

    class QueueElement implements Comparable {

        public int element;
        public int priority;

        public QueueElement(int element, int priority) {

            this.element = element;
            this.priority = priority;
        }

        public int compareTo(Object o) {

            QueueElement otherQueueElement = (QueueElement) o;
            int otherPriority = otherQueueElement.priority;
            if (this.priority == otherPriority) {
                if (this.element == otherQueueElement.element) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                if (this.priority < otherPriority) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }

    private TreeSet<QueueElement> queue;

    public PriorityQueue() {

        queue = new TreeSet<QueueElement>();
    }

    public void clear() {

        queue.clear();
    }

    public boolean isEmpty() {

        return queue.isEmpty();
    }

    public void insert(int element, int priority) {

        if (element < 1) {
            throw new IllegalArgumentException("Invalid element: " + element);
        }
        if (priority < 0) {
            throw new IllegalArgumentException("Invalid priority: " + priority);
        }
        QueueElement queueElement = new QueueElement(element, priority);
        queue.add(queueElement);
    }

    public int dequeueElement(boolean lowest) {

        if (!isEmpty()) {
            QueueElement queueElement = (lowest ? queue.first() : queue.last());
            int element = queueElement.element;
            queue.remove(queueElement);
            return element;
        }
        return -1;
    }
}