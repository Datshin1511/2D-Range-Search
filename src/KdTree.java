import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node root;
    private int size;

    private static class Node {
        private Point2D p;      
        private RectHV rect;    
        private Node left;        
        private Node right;        
        private boolean isVertical;  

        public Node(Point2D p, RectHV rect, boolean isVertical) {
            this.p = p;
            this.rect = rect;
            this.isVertical = isVertical;
        }
    }

    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument to insert() is null");
        root = insert(root, p, true, new RectHV(0, 0, 1, 1));
    }

    private Node insert(Node node, Point2D p, boolean isVertical, RectHV rect) {
        if (node == null) {
            size++;
            return new Node(p, rect, isVertical);
        }
        
        if (node.p.equals(p)) return node;
        
        if (node.isVertical) {
            if (p.x() < node.p.x()) {
                RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), node.p.x(), rect.ymax());
                node.left = insert(node.left, p, !isVertical, leftRect);
            } else {
                RectHV rightRect = new RectHV(node.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
                node.right = insert(node.right, p, !isVertical, rightRect);
            }
        } else {
            if (p.y() < node.p.y()) {
                RectHV lbRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.p.y());
                node.left = insert(node.left, p, !isVertical, lbRect);
            } else {
                RectHV rightRect = new RectHV(rect.xmin(), node.p.y(), rect.xmax(), rect.ymax());
                node.right = insert(node.right, p, !isVertical, rightRect);
            }
        }
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument to contains() is null");
        return contains(root, p);
    }

    private boolean contains(Node node, Point2D p) {
        if (node == null) return false;
        if (node.p.equals(p)) return true;
        
        if (node.isVertical) {
            if (p.x() < node.p.x()) return contains(node.left, p);
            else return contains(node.right, p);
        } else {
            if (p.y() < node.p.y()) return contains(node.left, p);
            else return contains(node.right, p);
        }
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;
        
        // draw the point in black
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();
        
        // draw the splitting line
        if (node.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        
        draw(node.left);
        draw(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangle is null");
        List<Point2D> result = new ArrayList<>();
        range(root, rect, result);
        return result;
    }

    private void range(Node node, RectHV rect, List<Point2D> result) {
        if (node == null) return;
        if (!node.rect.intersects(rect)) return;
        if (rect.contains(node.p)) result.add(node.p);
        range(node.left, rect, result);
        range(node.right, rect, result);
    }

    public Point2D nearest(Point2D point) {
        if (point == null) throw new IllegalArgumentException("Rectangle is null");
        if (isEmpty()) return null;

        return nearest(root, point, root.p, root.p.distanceSquaredTo(point));
    }

    private Point2D nearest(Node node, Point2D query, Point2D nearest, double nearestDistance) {
        if (node == null) return nearest;
        
        if (node.rect.distanceSquaredTo(query) >= nearestDistance) return nearest;
        
        double d = node.p.distanceSquaredTo(query);
        if (d < nearestDistance) {
            nearest = node.p;
            nearestDistance = d;
        }
        
        Node first, second;
        if (node.isVertical) {
            if (query.x() < node.p.x()) {
                first = node.left;
                second = node.right;
            } else {
                first = node.right;
                second = node.left;
            }
        } else {
            if (query.y() < node.p.y()) {
                first = node.left;
                second = node.right;
            } else {
                first = node.right;
                second = node.left;
            }
        }
        
        nearest = nearest(first, query, nearest, nearestDistance);
        nearestDistance = nearest.distanceSquaredTo(query);
        nearest = nearest(second, query, nearest, nearestDistance);
        
        return nearest;
    }

    public static void main(String[] args) {
        KdTree kd = new KdTree();

        
        kd.insert(new Point2D(0.5, 0.0));
        kd.insert(new Point2D(0.0, 0.875));
        kd.insert(new Point2D(0.125, 0.125));
        kd.insert(new Point2D(1.0, 0.375));
        kd.insert(new Point2D(0.875, 0.5));
        
        kd.draw();
        
        // Perform a range search
        RectHV queryRect = new RectHV(0.3, 0.25, 0.8, 0.75);
        System.out.println("Points inside the rectangle " + queryRect + ":");
        for (Point2D p : kd.range(queryRect))
            System.out.println(p);
        
        // Find the nearest neighbor to a query point
        Point2D queryPoint = new Point2D(0.25, 0.625);
        System.out.println("Nearest point to " + queryPoint + ": " + kd.nearest(queryPoint));
    }
}
