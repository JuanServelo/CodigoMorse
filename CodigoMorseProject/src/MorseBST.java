import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MorseBST {
    private Node root;

    public MorseBST() {
        root = null;
    }

    public void insert(char letter, String morseCode) {
        root = insertRecursive(root, letter, morseCode, 0);
    }

    private Node insertRecursive(Node node, char letter, String code, int index) {
        if (node == null) node = new Node(' ');

        if (index == code.length()) {
            node.letter = letter;
            return node;
        }

        char signal = code.charAt(index);
        if (signal == '.') {
            node.left = insertRecursive(node.left, letter, code, index + 1);
        } else if (signal == '-') {
            node.right = insertRecursive(node.right, letter, code, index + 1);
        }

        return node;
    }

    public char decodeCharacter(String code) {
        Node current = root;
        for (char c : code.toCharArray()) {
            current = (c == '.') ? current.left : current.right;
            if (current == null) return '?';
        }
        return current.letter;
    }

    public String decodeMessage(String morseMessage) {
        StringBuilder result = new StringBuilder();
        String[] codes = morseMessage.trim().split(" ");
        for (String code : codes) {
            result.append(decodeCharacter(code));
        }
        return result.toString();
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    public void drawTree(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        drawNodeRecursive(gc, root, canvas.getWidth() / 2, 40, canvas.getWidth() / 4, 1);
    }

    private void drawNodeRecursive(GraphicsContext gc, Node node, double x, double y, double xOffset, int level) {
        if (node == null) return;

        gc.strokeOval(x - 15, y - 15, 30, 30);
        gc.strokeText(String.valueOf(node.letter == ' ' ? ' ' : node.letter), x - 5, y + 5);

        if (node.left != null) {
            double newX = x - xOffset;
            double newY = y + 120;
            gc.strokeLine(x, y + 15, newX, newY - 15);
            drawNodeRecursive(gc, node.left, newX, newY, xOffset / 2, level + 1);
        }

        if (node.right != null) {
            double newX = x + xOffset;
            double newY = y + 120;
            gc.strokeLine(x, y + 15, newX, newY - 15);
            drawNodeRecursive(gc, node.right, newX, newY, xOffset / 2, level + 1);
        }
    }
}