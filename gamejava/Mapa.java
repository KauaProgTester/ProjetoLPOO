package gamejava;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class Mapa extends Application {

    private Label batalhaTexto;
    private VBox controleBatalha;
    private Button ataqueNormalButton;
    private Button habilidadeButton;
    private Personagem jogadorAtual;
    private Inimigo inimigoAtual;



    private Image treeImage;
    private Image castleImage;
    private Image spriteSheet;
    private Image ladraoImage, guardaImage, chefeImage;
    private Guerreiro guerreiro;
    private List<Inimigo> inimigos = new ArrayList<>();
    private Ladrao ladrao = new Ladrao(700, 450);
    private Guarda guarda = new Guarda(1200, 550);
    private Chefe chefe = new Chefe(1300,600);
    private Canvas backgroundCanvas;
    private Canvas foregroundCanvas;

    private double moveSpeed = 2; // Velocidade de movimento do personagem
    private double movimentoVertical = 0.7;
    private double dx = 0, dy = 0; // Direção do movimento
    private boolean isMoving = false; // Controle de animação com base no movimento
    private boolean facingRight = true; // Direção em que o personagem está virado

    private List<CaixaDeColisao> obstacles = new ArrayList<>(); // Lista de obstáculos para colisão

    // Configurações da spritesheet
    private final int spriteWidth = 32; // Largura de cada quadro
    private final int spriteHeight = 32; // Altura de cada quadro
    private final int numRows = 1; // Número de linhas na spritesheet
    private final int numCols = 10; // Número de colunas na spritesheet
    private int currentFrame = 0; // Quadro atual da animação
    private long lastUpdate = 0; // Tempo da última atualização
    private final long frameDuration = 100_000_000; // Duração de cada quadro em nanosegundos

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        backgroundCanvas = new Canvas();
        foregroundCanvas = new Canvas();
        root.getChildren().addAll(backgroundCanvas, foregroundCanvas);
    
        // Adiciona controles de batalha
        batalhaTexto = new Label();
        ataqueNormalButton = new Button("Ataque Normal");
        habilidadeButton = new Button("Habilidade Especial");
        controleBatalha = new VBox(10, batalhaTexto, ataqueNormalButton, habilidadeButton);
        controleBatalha.setLayoutX(20);
        controleBatalha.setLayoutY(20);
        root.getChildren().add(controleBatalha);
    
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Detailed Map Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    
        // Carregar as imagens
        treeImage = new Image(getClass().getResourceAsStream("arvere.png")); // imagem da arvore
        castleImage = new Image(getClass().getResourceAsStream("castelo.png")); // imagem do castelo
        spriteSheet = new Image(getClass().getResourceAsStream("andar.png")); // Imagem da spritesheet
        ladraoImage = new Image(getClass().getResourceAsStream("Ladrao.png")); // Imagem do ladrao
        guardaImage = new Image(getClass().getResourceAsStream("Guarda.png")); // Imagem do guarda
        chefeImage = new Image(getClass().getResourceAsStream("Chefe.png")); // Imagem do chefe
    
        // Criar o guerreiro
        Inventario inventario = new Inventario();
        Armadura armadura = new Armadura("Armadura de Couro", "Fraca, mas não inútil", 5); // Exemplo de armadura
        guerreiro = new Guerreiro("Guerreiro", inventario, armadura, 100, 100);
        guerreiro.setX(100); // Definir posição inicial
        guerreiro.setY(100);
    
        // Adicionando os inimigos
        Inimigo ladrao = new Inimigo("Ladrão", 50, "ataque rápido", 10, 200, 200);
        Inimigo guarda = new Inimigo("Guarda", 80, "ataque forte", 15, 300, 300);
        Inimigo chefe = new Inimigo("Chefe", 150, "ataque devastador", 20, 400, 400);
        inimigos.add(ladrao);
        inimigos.add(guarda);
        inimigos.add(chefe);
    
        // Atualizar o tamanho dos canvases e desenhar o mapa
        updateCanvasSize(scene.getWidth(), scene.getHeight());
    
        // Adicionar controle de movimentação
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.W) {
                dy = -moveSpeed * movimentoVertical;
                isMoving = true;
            } else if (event.getCode() == KeyCode.S) {
                dy = moveSpeed * movimentoVertical;
                isMoving = true;
            } else if (event.getCode() == KeyCode.A) {
                dx = -moveSpeed;
                isMoving = true;
                facingRight = false; // Virar para a esquerda
            } else if (event.getCode() == KeyCode.D) {
                dx = moveSpeed;
                isMoving = true;
                facingRight = true; // Virar para a direita
            }
        });
    
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.S) {
                dy = 0;
            }
            if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.D) {
                dx = 0;
            }
            // Quando todas as teclas de movimento forem soltas, parar a animação
            if (dx == 0 && dy == 0) {
                isMoving = false;
            }
        });
    
        // Redimensionar o canvas quando o tamanho da tela mudar
        scene.widthProperty().addListener((obs, oldVal, newVal) -> updateCanvasSize(newVal.doubleValue(), scene.getHeight()));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> updateCanvasSize(scene.getWidth(), newVal.doubleValue()));
    
        // Manejo do combate:
        ataqueNormalButton.setOnAction(e -> {
            if (jogadorAtual != null && inimigoAtual != null) {
                jogadorAtual.atacar(inimigoAtual);
                if (inimigoAtual.getVidaInimigo() > 0) {
                    inimigoAtual.atacar(jogadorAtual);
                    atualizarTextoBatalha();
                }
                verificarFimDeBatalha();
            }
        });
    
        habilidadeButton.setOnAction(e -> {
            if (jogadorAtual != null && inimigoAtual != null) {
                if (jogadorAtual.getMana() >= 10) { // Custo de mana para habilidade
                    jogadorAtual.usarHabilidade(inimigoAtual);
                    jogadorAtual.setMana(jogadorAtual.getMana() - 10); // Reduz a mana
                    if (inimigoAtual.getVidaInimigo() > 0) {
                        inimigoAtual.atacar(jogadorAtual);
                        atualizarTextoBatalha();
                    }
                    verificarFimDeBatalha();
                } else {
                    batalhaTexto.setText("Mana insuficiente!");
                }
            }
        });
    
        // Criar um AnimationTimer para movimentação suave e animação
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updatePosition();
                updateAnimation(now);
                drawForeground();
                checarColisao(guerreiro);
            }
        }.start();
    }
    
    

    private void updateCanvasSize(double width, double height) {
        backgroundCanvas.setWidth(width);
        backgroundCanvas.setHeight(height);
        foregroundCanvas.setWidth(width);
        foregroundCanvas.setHeight(height);
    
        // Redefinir obstáculos
        obstacles.clear();
    
        // Desenhar o fundo e a grama
        drawBackground();
    }

    private void drawBackground() {
        GraphicsContext bgGC = backgroundCanvas.getGraphicsContext2D();
        double width = backgroundCanvas.getWidth();
        double height = backgroundCanvas.getHeight();
        
        // Limpar canvas
        bgGC.clearRect(0, 0, width, height);
    
        // Desenhar o chão verde
        bgGC.setFill(Color.GREEN);
        bgGC.fillRect(0, 0, width, height);
        
        // Desenhar detalhes da grama mais finos
        bgGC.setFill(Color.DARKGREEN);
        for (int i = 0; i < 500; i++) {
            double x = Math.random() * width;
            double y = Math.random() * height;
            double w = 2 + Math.random() * 5;
            double h = 2 + Math.random() * 5;
            bgGC.fillOval(x, y, w, h);
        }
        
        // Redefinir o caminho e as árvores ao longo do caminho
        double treeSize = Math.min(width, height) * 0.1;
        double padding = 20;
        double step = 80;
    
        // Criar o caminho (caminho delimitado por árvores)
        for (double x = padding; x < width - padding; x += step) {
            bgGC.drawImage(treeImage, x, padding, treeSize, treeSize);
            obstacles.add(new CaixaDeColisao(x, padding, treeSize, treeSize));
        }
        
        for (double y = padding + step; y < height - padding; y += step) {
            bgGC.drawImage(treeImage, width - padding - treeSize, y, treeSize, treeSize);
            obstacles.add(new CaixaDeColisao(width - padding - treeSize, y, treeSize, treeSize));
        }
        
        for (double x = width - padding - treeSize; x > padding; x -= step) {
            bgGC.drawImage(treeImage, x, height - padding - treeSize, treeSize, treeSize);
            obstacles.add(new CaixaDeColisao(x, height - padding - treeSize, treeSize, treeSize));
        }
        
        for (double y = height - padding - 2 * step; y > padding; y -= step) {
            bgGC.drawImage(treeImage, padding, y, treeSize, treeSize);
            obstacles.add(new CaixaDeColisao(padding, y, treeSize, treeSize));
        }
        
        // Desenhar o castelo no canto superior direito, ao lado das árvores
        double castleOriginalWidth = 651;
        double castleOriginalHeight = 431;
        double castleScale = 0.4;
        double castleWidth = castleOriginalWidth * castleScale;
        double castleHeight = castleOriginalHeight * castleScale;
        double castleX = width - castleWidth - padding - treeSize;
        double castleY = padding + treeSize;
        
        bgGC.drawImage(castleImage, castleX, castleY, castleWidth, castleHeight);
        obstacles.add(new CaixaDeColisao(castleX, castleY, castleWidth, castleHeight));
        obstacles.add(new CaixaDeColisao(ladrao.getX()+6, ladrao.getY(),1, 32));
        obstacles.add(new CaixaDeColisao(guarda.getX()+6, guarda.getY(), 1, 48));
        obstacles.add(new CaixaDeColisao(chefe.getX()+6, chefe.getY(), 1, 80));
    }
    

    private void updatePosition() {
        double newX = guerreiro.getX() + dx;
        double newY = guerreiro.getY() + dy;

        // Certificar-se de que o personagem está dentro dos limites da tela
        double playerSize = spriteWidth; // Tamanho do personagem
        double width = foregroundCanvas.getWidth();
        double height = foregroundCanvas.getHeight();

        newX = Math.max(0, Math.min(newX, width - playerSize));
        newY = Math.max(0, Math.min(newY, height - playerSize));

        // Verificar colisão com obstáculos
        boolean collision = false;
        CaixaDeColisao playerBox = new CaixaDeColisao(newX, newY, playerSize, playerSize);
        for (CaixaDeColisao obstacle : obstacles) {
            if (playerBox.intersects(obstacle)) {
                collision = true;
                break;
            }
        }

        if (!collision) {
            guerreiro.setX(newX);
            guerreiro.setY(newY);
        }  
        
        checarColisao(guerreiro);
    }

    private void updateAnimation(long now) {
        if (isMoving) {
            if (now - lastUpdate >= frameDuration) {
                lastUpdate = now;
                currentFrame = (currentFrame + 1) % (numRows * numCols);
            }
        } else {
            currentFrame = 0; // Exibir o primeiro quadro quando parado
        }
    }

    private void drawForeground() {
        GraphicsContext fgGC = foregroundCanvas.getGraphicsContext2D();
        double width = foregroundCanvas.getWidth();
        double height = foregroundCanvas.getHeight();

        // Limpar o canvas de primeiro plano
        fgGC.clearRect(0, 0, width, height);

        // Desenhar o personagem
        double playerSize = spriteWidth; // Tamanho do personagem
        double px = guerreiro.getX();
        double py = guerreiro.getY();

        // Certificar-se de que o personagem está dentro dos limites da tela
        px = Math.max(0, Math.min(px, width - playerSize));
        py = Math.max(0, Math.min(py, height - playerSize));

        // Calcular o quadro da animação atual
        int frameX = (currentFrame % numCols) * spriteWidth;
        int frameY = (currentFrame / numCols) * spriteHeight;

        // Inverter a imagem horizontalmente se o personagem estiver virado para a esquerda
        if (!facingRight) {
            fgGC.drawImage(spriteSheet, frameX + spriteWidth, frameY, -spriteWidth, spriteHeight, px, py, playerSize, playerSize);
        } else {
            fgGC.drawImage(spriteSheet, frameX, frameY, spriteWidth, spriteHeight, px, py, playerSize, playerSize);
        }

        //desenhando os inimigos:
        fgGC.drawImage(ladraoImage, this.ladrao.getX(), this.ladrao.getY(), 16, 32);
        fgGC.drawImage(guardaImage, this.guarda.getX(), this.guarda.getY(), 48, 48);
        fgGC.drawImage(chefeImage, this.chefe.getX(), this.chefe.getY(), 80, 80);
    }

    private void iniciarCombate(Personagem jogador, Inimigo inimigo) {
        this.jogadorAtual = jogador;
        this.inimigoAtual = inimigo;
        batalhaTexto.setText("Combate iniciado contra " + inimigo.getNome() + "!");
    }
    
    private void checarColisao(Personagem jogador) {
        CaixaDeColisao playerBox = new CaixaDeColisao(jogador.getX(), jogador.getY(), spriteWidth, spriteHeight);
        CaixaDeColisao ladraoBox = new CaixaDeColisao(this.ladrao.getX(), this.ladrao.getY(), 16, 32);
        CaixaDeColisao guardaBox = new CaixaDeColisao(this.guarda.getX(), this.guarda.getY(), 48, 48);
        CaixaDeColisao chefeBox = new CaixaDeColisao(this.chefe.getX(), this.chefe.getY(), 80, 80);
    
        if (playerBox.intersects(ladraoBox)) {
            iniciarCombate(jogador, ladrao);
        } else if (playerBox.intersects(guardaBox)) {
            iniciarCombate(jogador, guarda);
        } else if (playerBox.intersects(chefeBox)) {
            iniciarCombate(jogador, chefe);
        }
    }

    private void atualizarTextoBatalha() {
        batalhaTexto.setText(
            jogadorAtual.getNome() + " atacou " + inimigoAtual.getNome() + " e causou dano.\n" +
            inimigoAtual.getNome() + " atacou " + jogadorAtual.getNome() + " e causou dano.\n" +
            jogadorAtual.getNome() + " - Vida: " + jogadorAtual.getVida() + ", Mana: " + jogadorAtual.getMana() + "\n" +
            inimigoAtual.getNome() + " - Vida: " + inimigoAtual.getVidaInimigo()
        );
    }

    private void verificarFimDeBatalha() {
        if (jogadorAtual.getVida() <= 0) {
            batalhaTexto.setText("Você morreu!");
            jogadorAtual = null;
            inimigoAtual = null;
        } else if (inimigoAtual.getVidaInimigo() <= 0) {
            batalhaTexto.setText(inimigoAtual.getNome() + " morreu! Você venceu!");
            jogadorAtual.ganharExperiencia(10); // Exemplo de ganho de experiência
            inimigoAtual = null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }    

}
