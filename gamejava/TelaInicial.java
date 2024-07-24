package gamejava;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TelaInicial extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);
        
        Text mensagem = new Text("A rainha reportou um assalto ao reino! Os acusados estão tentando fugir, seu objetivo é detê-los!");
        mensagem.setFill(Color.WHITE);
        mensagem.setFont(new Font(24));
        root.getChildren().add(mensagem);

        root.setStyle("-fx-background-color: black;");
        
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true); // Configura para tela cheia
        primaryStage.show();
        
        PauseTransition pausa = new PauseTransition(Duration.seconds(12));
        pausa.setOnFinished(event -> iniciarMapa());
        pausa.play();
    }
    private void iniciarMapa() {
        Mapa mapa = new Mapa();
        try {
            mapa.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}