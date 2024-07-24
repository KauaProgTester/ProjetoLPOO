package gamejava;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

public class PainelDeCombate {
    private BorderPane painel;
    private VBox vbox;
    private VBox vboxAtualizacoes;
    private Text textoDeBatalha;
    private Text mensagemMana;
    private TextArea caixaDeAtualizacoes;
    private Button botaoAtacar;
    private Button botaoHabilidade;

    public PainelDeCombate() {
        painel = new BorderPane();;
        vbox = new VBox(10);
        
        vboxAtualizacoes = new VBox(10); 
        
        textoDeBatalha = new Text();
        mensagemMana = new Text();

        caixaDeAtualizacoes = new TextArea(); // Caixa de texto para atualizações
        caixaDeAtualizacoes.setPrefSize(800, 300); 
        caixaDeAtualizacoes.setEditable(true); 

        textoDeBatalha.setFill(Color.WHITE);
        textoDeBatalha.setFont(new Font(24));
        mensagemMana.setFill(Color.RED);
        mensagemMana.setFont(new Font(20));
        botaoAtacar = new Button("Atacar");
        botaoHabilidade = new Button("Habilidade");

        vbox.getChildren().addAll(mensagemMana, textoDeBatalha, botaoAtacar, botaoHabilidade);
        vbox.setAlignment(Pos.CENTER);

        vbox.setPadding(new javafx.geometry.Insets(0, 0, 0, 800)); // Margem à esquerda

        vboxAtualizacoes.getChildren().add(caixaDeAtualizacoes);
        vboxAtualizacoes.setAlignment(Pos.TOP_RIGHT);


        painel.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        painel.setCenter(vbox);
        painel.setRight(vboxAtualizacoes);
        painel.setVisible(false); // Inicialmente invisível
    }

    public void setVisible(boolean visible) {
        painel.setVisible(visible);
    }

    public BorderPane getPainel() {
        return painel;
    }

    public void exibirMensagemMana(String mensagem) {
        mensagemMana.setText(mensagem);
        painel.layout(); // atualização do layout
    }

    public void exibirTexto(String texto) {
        textoDeBatalha.setText(texto);
        painel.layout();
    }

    public void adicionarAtualizacao(String atualizacao) {
        caixaDeAtualizacoes.appendText(atualizacao + "\n"); // adiciona nova atualização
    }

    public void limparCaixaDeAtualizacoes(){
        caixaDeAtualizacoes.clear();
    }

    public Button getBotaoAtacar() {
        return botaoAtacar;
    }

    public Button getBotaoHabilidade() {
        return botaoHabilidade;
    }

    public void mostrar() {
        painel.setVisible(true);
    }

    public void esconder() {
        painel.setVisible(false);
    }
}