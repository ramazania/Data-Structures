/**
 * Name: Elise and Ali
 * Email: knutsene@carleton.edu, ramazania@carleton.edu
 * Description: K-D tree
 */
import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class KdTree<Value> {

    private class Node {
        private Point2D p; // the point
        private Value val; // the tree maps the point to this value
        private RectHV rect; // the axis-aligned rectangle corresponding to this node
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree

        public Node(Point2D p, Value val, RectHV rect) {
            this.p = p;
            this.val = val;
            this.rect = rect;
        }
    }

    private Node overallroot;
    private int size = 0;

    public KdTree() {
        overallroot = null;
    }

    public boolean isEmpty() {
        return overallroot == null;
    }

    public int size() {
        return size;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        int numnum = 0;
        RectHV newRect = new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY);
        if (overallroot == null) {
            overallroot = new Node(p, val, newRect);
        } else {
            putHelper(overallroot, p, val, numnum, newRect);
        }
    }

    private void putHelper(Node currentroot, Point2D p, Value val, int numnum, RectHV newRect) {
        size++;
        if (numnum % 2 == 0) {
            double cmp = p.x() - currentroot.p.x();
            if (cmp < 0) { // our new point has a smaller x
                if (currentroot.lb == null) {
                    currentroot.lb = new Node(p, val, newRect);
                    newRect = new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, p.x(),
                            Double.POSITIVE_INFINITY);
                    System.out.println("hello");
                    System.out.println(newRect.toString());
                } else {
                    putHelper(currentroot.lb, p, val, numnum++, newRect);
                }
            } else {
                if (currentroot.rt == null) {
                    currentroot.rt = new Node(p, val, newRect);
                    new RectHV(p.x(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
                } else {
                    putHelper(currentroot.rt, p, val, numnum++, newRect);
                }
            }
        } else {
            double cmp = p.y() - currentroot.p.y();
            if (cmp < 0) {
                if (currentroot.lb == null) {
                    currentroot.lb = new Node(p, val, null);
                    new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, p.y());
                } else {
                    putHelper(currentroot.lb, p, val, numnum++, newRect);
                }
            } else {
                if (currentroot.rt == null) {
                    currentroot.rt = new Node(p, val, null);
                    new RectHV(Double.NEGATIVE_INFINITY, p.y(), Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
                } else {
                    putHelper(currentroot.rt, p, val, numnum++, newRect);
                }
            }
        }
    }

    public Value get(Point2D p) {
        int numnum = 0;
        return getHelper(overallroot, p, numnum);
    }

    private Value getHelper(Node currentroot, Point2D p, int numnum) {
        if (currentroot == null) {
            return null;
        } else if (numnum % 2 == 0) {
            double cmp = p.x() - currentroot.p.x();
            if (cmp < 0) {
                return getHelper(currentroot.lb, p, numnum++);
            } else if (cmp > 0) {
                return getHelper(currentroot.rt, p, numnum++);
            } else {
                cmp = p.y() - currentroot.p.y();
                if (cmp < 0) {
                    return getHelper(currentroot.lb, p, numnum++);
                } else if (cmp > 0) {
                    return getHelper(currentroot.rt, p, numnum++);
                } else {
                    return currentroot.val;
                }
            }
        }
        return currentroot.val;
    }

    // does the tree contain point p?
    public boolean contains(Point2D p) {
        return get(p) != null;
    }

    // all points in the map in level order
    public Iterable<Point2D> points() {
        if (overallroot == null) {
            return null;
        }
        Queue<Node> q = new Queue<Node>();
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        q.enqueue(overallroot);

        while (!q.isEmpty()) {
            Node next = q.dequeue(); // creating node to add to queue
            list.add(next.p); // adding point to list
            if (next.lb != null) {
                q.enqueue(next.lb);
            } else if (next.rt != null) {
                q.enqueue(next.rt);
            }
        }
        return list;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        return rangeHelper(rect, overallroot);
    }

    private Iterable<Point2D> rangeHelper(RectHV rect, Node currentroot) {
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        if (rect.contains(currentroot.p)) {
            list.add(currentroot.p);
        }
        else if (currentroot.lb != null && rect.intersects(currentroot.lb.rect)) {
            rangeHelper(rect, currentroot.lb);
        }
        else if (currentroot.rt != null && rect.intersects(currentroot.rt.rect)) {
            rangeHelper(rect, currentroot.rt);
        }
        
        return list;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        Node bestNode = overallroot;
        double distance = overallroot.rect.distanceTo(p);
        return nearestHelper(p, bestNode, distance);
    }
    private Point2D nearestHelper(Point2D p, Node bestNode, double distance) {
        Point2D left = new Point2D(10000000, 100000000);
        Point2D right = new Point2D(100000000, 1000000000);
        boolean updatedleft = false;
        boolean updatedright = false;

        if (bestNode.lb == null && bestNode.rt == null) {
            return bestNode.p;
        }
        if (bestNode.lb.rect.distanceTo(p) < distance) {
            double x = bestNode.lb.rect.distanceTo(p);
            left = nearestHelper(p, bestNode.lb, x);
            updatedleft = true;
        }
        if (bestNode.rt.rect.distanceTo(p) < distance) {
            double x = bestNode.rt.rect.distanceTo(p);
            right = nearestHelper(p, bestNode.rt, x);
            updatedright = true;
        }
        if (!updatedleft && !updatedright) {
            return bestNode.p;
        }
        if (left.distanceTo(p) > right.distanceTo(p)) {
            return right;
        }
           return left; 
    }   

    public static void main(String[] args) {
        KdTree<String> tree = new KdTree<String>();
        tree.put(new Point2D(1, 3), "he1");
        tree.put(new Point2D(2, 4), "he2");
        tree.put(new Point2D(1, 3), "he3");
        tree.put(new Point2D(1, 4), "he4");
        // String point = tree.get(new Point2D(1, 3));
        // String p2 = tree.get(new Point2D(1, 4));
        // String p1=tree.get(new Point2D(1,3));
        // System.out.println(tree.());
        // System.out.println(p2);
        // System.out.println(p1);

    }
}