import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.util.Scanner;

public class TreeVisualizer extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Visualizador de Árvore Morse");

        MorseBST bst = new MorseBST();
        adicionarAlfabeto(bst);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a mensagem em código Morse (use espaço entre letras):");
        String morseInput = scanner.nextLine();
        String decodificada = bst.decodeMessage(morseInput);
        System.out.println("Mensagem decodificada: " + decodificada);

        int height = bst.getHeight();
        int canvasHeight = 100 + height * 100;
        int canvasWidth = (int) Math.pow(2, height) * 40;

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        bst.drawTree(canvas);

        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, canvasWidth, canvasHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void adicionarAlfabeto(MorseBST bst) {
        bst.insert('A', ".-");
        bst.insert('B', "-...");
        bst.insert('C', "-.-.");
        bst.insert('D', "-..");
        bst.insert('E', ".");
        bst.insert('F', "..-.");
        bst.insert('G', "--.");
        bst.insert('H', "....");
        bst.insert('I', "..");
        bst.insert('J', ".---");
        bst.insert('K', "-.-");
        bst.insert('L', ".-..");
        bst.insert('M', "--");
        bst.insert('N', "-.");
        bst.insert('O', "---");
        bst.insert('P', ".--.");
        bst.insert('Q', "--.-");
        bst.insert('R', ".-.");
        bst.insert('S', "...");
        bst.insert('T', "-");
        bst.insert('U', "..-");
        bst.insert('V', "...-");
        bst.insert('W', ".--");
        bst.insert('X', "-..-");
        bst.insert('Y', "-.--");
        bst.insert('Z', "--..");
    }

    public static void main(String[] args) {
        launch(args);
    }
}