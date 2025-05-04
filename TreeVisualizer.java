import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class TreeVisualizer extends Application {

    static class TreeNode {
        String character;
        TreeNode left;   // dot
        TreeNode right;  // dash

        TreeNode(String character) {
            this.character = character;
        }
    }

    static class MorseBST {
        private final TreeNode root;
        private final Map<String, String> morseMap = new HashMap<>();

        public MorseBST() {
            root = new TreeNode("");
            buildTree();
        }

        private void buildTree() {
            morseMap.put(".", "E"); morseMap.put("-", "T");
            morseMap.put("..", "I"); morseMap.put(".-", "A"); morseMap.put("-.", "N"); morseMap.put("--", "M");
            morseMap.put("...", "S"); morseMap.put("..-", "U"); morseMap.put(".-.", "R"); morseMap.put(".--", "W");
            morseMap.put("-..", "D"); morseMap.put("-.-", "K"); morseMap.put("--.", "G"); morseMap.put("---", "O");
            morseMap.put("....", "H"); morseMap.put("...-", "V"); morseMap.put("..-.", "F"); morseMap.put(".--.", "P");
            morseMap.put(".---", "J"); morseMap.put("-...", "B"); morseMap.put("-..-", "X"); morseMap.put("-.-.", "C");
            morseMap.put("-.--", "Y"); morseMap.put("--..", "Z"); morseMap.put("--.-", "Q");

            for (Map.Entry<String, String> entry : morseMap.entrySet()) {
                insert(entry.getValue(), entry.getKey());
            }
        }

        public void insert(String letter, String morseCode) {
            TreeNode current = root;
            for (char symbol : morseCode.toCharArray()) {
                if (symbol == '.') {
                    if (current.left == null) current.left = new TreeNode("");
                    current = current.left;
                } else if (symbol == '-') {
                    if (current.right == null) current.right = new TreeNode("");
                    current = current.right;
                }
            }
            current.character = letter;
        }

        public String decode(String morse) {
            StringBuilder result = new StringBuilder();
            for (String word : morse.trim().split(" / ")) {
                for (String code : word.split(" ")) {
                    TreeNode current = root;
                    for (char c : code.toCharArray()) {
                        current = (c == '.') ? current.left : current.right;
                        if (current == null) break;
                    }
                    result.append((current != null) ? current.character : "");
                }
                result.append(" ");
            }
            return result.toString().trim();
        }

        public int getHeight() {
            return getHeight(root);
        }

        private int getHeight(TreeNode node) {
            if (node == null) return 0;
            return 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }

        public void drawTree(Canvas canvas) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            drawNode(gc, root, canvas.getWidth() / 2, 40, canvas.getWidth() / 4);
        }

        private void drawNode(GraphicsContext gc, TreeNode node, double x, double y, double xOffset) {
            if (node == null) return;

            gc.strokeOval(x - 15, y - 15, 30, 30);
            gc.strokeText(node.character, x - 5, y + 5);

            if (node.left != null) {
                double newX = x - xOffset;
                double newY = y + 100;
                gc.strokeLine(x, y + 15, newX, newY - 15);
                drawNode(gc, node.left, newX, newY, xOffset / 2);
            }
            if (node.right != null) {
                double newX = x + xOffset;
                double newY = y + 100;
                gc.strokeLine(x, y + 15, newX, newY - 15);
                drawNode(gc, node.right, newX, newY, xOffset / 2);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Visualizador de Árvore Morse");
        MorseBST bst = new MorseBST();

        int height = bst.getHeight();
        int canvasHeight = 100 + height * 120;
        int canvasWidth = (int) Math.pow(2, height) * 40;

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        bst.drawTree(canvas);

        TextField input = new TextField();
        input.setPromptText("Digite código Morse (ex: .... . .-.. .-.. --- / .-- --- .-. .-.. -..)");
        Label output = new Label();
        Button decodeButton = new Button("Decodificar");

        decodeButton.setOnAction(e -> {
            String morse = input.getText();
            String result = bst.decode(morse);
            output.setText("Texto: " + result);
        });

        VBox layout = new VBox(10, input, decodeButton, output, canvas);
        Scene scene = new Scene(layout, canvasWidth, canvasHeight + 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}