    package gamejava;

    import javafx.util.Duration;
    import javafx.animation.PauseTransition;
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

    import java.util.ArrayList;
    import java.util.List;

    public class Mapa extends Application {

        private Stage primaryStage;
        private Personagem jogadorAtual;
        private Inimigo inimigoAtual;
        private PainelDeCombate painelDeCombate;
        private Image treeImage;
        private Image castleImage;
        private Image spriteSheet;
        private Image ladraoImage, guardaImage, chefeImage, caveiraImage;
        private Guerreiro guerreiro;
        private List<Inimigo> inimigos = new ArrayList<>();
        private Ladrao ladrao = new Ladrao(700, 450);
        private Guarda guarda = new Guarda(1200, 550);
        private Chefe chefe = new Chefe(1300,600);
        private Canvas backgroundCanvas;
        private Canvas foregroundCanvas;

        private double velocidade = 1.5; // Velocidade de movimento do personagem
        private double movimentoVertical = 0.7;
        private double dx = 0, dy = 0; // Direção do movimento
        private boolean isMoving = false; // Controle de animação com base no movimento
        private boolean facingRight = true; // Direção em que o personagem está virado
        private boolean telaFinalExibida = false;

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
            this.primaryStage = primaryStage;
            StackPane root = new StackPane();
            backgroundCanvas = new Canvas();
            foregroundCanvas = new Canvas();
            root.getChildren().addAll(backgroundCanvas, foregroundCanvas);

            painelDeCombate = new PainelDeCombate();
            painelDeCombate.esconder(); // Inicialmente invisível
            root.getChildren().add(painelDeCombate.getPainel()); // Adiciona o painel ao StackPane


        
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Mapa");
            primaryStage.setScene(scene);
            primaryStage.setFullScreen(true); 
            primaryStage.show();
        
            // Carregar as imagens
            treeImage = new Image(getClass().getResourceAsStream("arvere.png")); // imagem da arvore
            castleImage = new Image(getClass().getResourceAsStream("castelo.png")); // imagem do castelo
            spriteSheet = new Image(getClass().getResourceAsStream("andar.png")); // Imagem da spritesheet
            ladraoImage = new Image(getClass().getResourceAsStream("Ladrao.png")); // Imagem do ladrao
            guardaImage = new Image(getClass().getResourceAsStream("Guarda.png")); // Imagem do guarda
            chefeImage = new Image(getClass().getResourceAsStream("Chefe.png")); // Imagem do chefe      
            caveiraImage = new Image(getClass().getResourceAsStream("caveira.png")); // Imagem da caveira  
        
            // Criar o guerreiro
            Inventario inventario = new Inventario();
            Armadura armadura = new Armadura("Armadura de Couro", "Fraca, mas não inútil", 5); // Exemplo de armadura
            guerreiro = new Guerreiro("Guerreiro", inventario, armadura, 130, 130);
        
            // Adicionando os inimigos
            inimigos.add(ladrao);
            inimigos.add(guarda);
            inimigos.add(chefe);
        
            // Atualizar o tamanho dos canvas e desenhar o mapa
            updateCanvasSize(scene.getWidth(), scene.getHeight());
        
            // Adicionar controle de movimentação
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.W) {
                    dy = - velocidade * movimentoVertical;
                    isMoving = true;
                } else if (event.getCode() == KeyCode.S) {
                    dy = velocidade * movimentoVertical;
                    isMoving = true;
                } else if (event.getCode() == KeyCode.A) {
                    dx = -velocidade;
                    isMoving = true;
                    facingRight = false; // Virar para a esquerda
                } else if (event.getCode() == KeyCode.D) {
                    dx = velocidade;
                    isMoving = true;
                    facingRight = true; // Virar para a direita
                }
            });
            
            //movimentação do personagem
            // vai parar a animacao todas as teclas de movimento forem soltas
            scene.setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.S) {
                    dy = 0;
                }
                if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.D) {
                    dx = 0;
                }
                
                if (dx == 0 && dy == 0) {
                    isMoving = false;
                }
            });
        
            // Redimensionar o canvas quando tirar e botar em tela cheia
            scene.widthProperty().addListener((obs, oldVal, newVal) -> updateCanvasSize(newVal.doubleValue(), scene.getHeight()));
            scene.heightProperty().addListener((obs, oldVal, newVal) -> updateCanvasSize(scene.getWidth(), newVal.doubleValue()));
        
        
            // Animação do personagem
            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    updatePosition();
                    updateAnimation(now);
                    drawFrenteCenario();
                    checarColisao(guerreiro);
                    verificarCondicoes();
                    

                }
            }.start();
        }
        
        

        private void updateCanvasSize(double largura, double altura) {
            backgroundCanvas.setWidth(largura);
            backgroundCanvas.setHeight(altura);
            foregroundCanvas.setWidth(largura);
            foregroundCanvas.setHeight(altura);
        
            // Redefinir obstáculos
            obstacles.clear();
        
            // Desenhar o "background"
            drawFundo();
        }

        private void drawFundo() {
            GraphicsContext bgGC = backgroundCanvas.getGraphicsContext2D();
            double width = backgroundCanvas.getWidth();
            double height = backgroundCanvas.getHeight();
            
            // Limpar canvas
            bgGC.clearRect(0, 0, width, height);
        
            // Desenhar o chão verde
            bgGC.setFill(Color.GREEN);
            bgGC.fillRect(0, 0, width, height);
            
            // desenhar detalhes da grama mais finos
            bgGC.setFill(Color.DARKGREEN);
            for (int i = 0; i < 500; i++) {
                double x = Math.random() * width;
                double y = Math.random() * height;
                double w = 2 + Math.random() * 5;
                double h = 2 + Math.random() * 5;
                bgGC.fillOval(x, y, w, h);
            }
            
            // fazer o caminho e as árvores ao longo do caminho
            double treeSize = Math.min(width, height) * 0.1;
            double padding = 20;
            double step = 80;
        
            // criar o caminho (caminho delimitado por árvores)
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
            
            // desenhar o castelo no canto superior direito, ao lado das árvores
            double castleOriginalWidth = 651;
            double castleOriginalHeight = 431;
            double castleScale = 0.4;
            double castleWidth = castleOriginalWidth * castleScale;
            double castleHeight = castleOriginalHeight * castleScale;
            double castleX = width - castleWidth - padding - treeSize;
            double castleY = padding + treeSize;
            
            bgGC.drawImage(castleImage, castleX, castleY, castleWidth, castleHeight);
            obstacles.add(new CaixaDeColisao(castleX, castleY, castleWidth, castleHeight));
            obstacles.add(new CaixaDeColisao(ladrao.getX()+12, ladrao.getY(),1, 32));
            obstacles.add(new CaixaDeColisao(guarda.getX()+12, guarda.getY(), 1, 48));
            obstacles.add(new CaixaDeColisao(chefe.getX()+12, chefe.getY(), 1, 80));
        }
        

        private void updatePosition() {
            double newX = guerreiro.getX() + dx;
            double newY = guerreiro.getY() + dy;

            // manter o personagem dentro da tela
            double playerSize = spriteWidth; // Tamanho do personagem
            double width = foregroundCanvas.getWidth();
            double height = foregroundCanvas.getHeight();

            newX = Math.max(0, Math.min(newX, width - playerSize));
            newY = Math.max(0, Math.min(newY, height - playerSize));

            // Verificar colisão com obstáculos
            boolean collision = false;
            CaixaDeColisao playerBox = new CaixaDeColisao(newX, newY, playerSize, playerSize);
            for (CaixaDeColisao obstacle : obstacles) {
                if (playerBox.encosta(obstacle)) {
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
                currentFrame = 0; //Exibir a primeira imagem quando parado
            }
        }

        private void drawFrenteCenario() {
            verificarCondicoes();
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

            //desenhando os inimigos e a caveira:
            fgGC.drawImage(ladraoImage, this.ladrao.getX(), this.ladrao.getY(), 16, 32);
            fgGC.drawImage(guardaImage, this.guarda.getX(), this.guarda.getY(), 48, 48);
            fgGC.drawImage(chefeImage, this.chefe.getX(), this.chefe.getY(), 80, 80);

            if(ladrao.getVidaInimigo()<=0){
                fgGC.drawImage(caveiraImage, ladrao.getX()-12, ladrao.getY()-3, 40, 40);
            } 
            if(guarda.getVidaInimigo()<=0){
                fgGC.drawImage(caveiraImage, guarda.getX()-12, guarda.getY()-10, 80, 95);
            }
            if(chefe.getVidaInimigo()<=0){
                fgGC.drawImage(caveiraImage, chefe.getX()-10, chefe.getY()-10, 120, 120);
            }
        }
        

        public void iniciarCombate(Personagem jogador, Inimigo inimigo) {
            jogadorAtual = jogador; 
            inimigoAtual = inimigo; 
        
            // Exibir o texto de batalha
            String texto = jogador.getNome() + " encontrou " + inimigo.getNome() + "!";
            painelDeCombate.exibirTexto(texto);
            painelDeCombate.getPainel().setVisible(true); // Exibe o painel de combate
            
            // Adicionar eventos para os botões
            painelDeCombate.getBotaoAtacar().setOnAction(event -> {
                if(jogadorAtual.getVida()>0){
                jogadorAtual.atacar(inimigoAtual);
                painelDeCombate.adicionarAtualizacao(jogadorAtual.getNome() + " ataca " + inimigo.getNome() + " com força, dando "+jogadorAtual.getForca()+" de dano e recebendo "+inimigo.getDano()+" de dano.");
                painelDeCombate.adicionarAtualizacao("Vida do Guerreiro: "+jogadorAtual.getVida()+" Vida do "+inimigo.getNome()+": " + inimigo.getVidaInimigo());
                verificarStatusDeCombate(jogadorAtual, inimigoAtual);
                } else{
                    painelDeCombate.adicionarAtualizacao("Tu ta morto boy, para de atacar kkkkkkkkkk");
                }
            });
            
            painelDeCombate.getBotaoHabilidade().setOnAction(event -> {
                if(jogadorAtual.getVida()>0){
                    if (jogadorAtual.getMana() >= 10) { // Custo de mana para habilidade
                        painelDeCombate.adicionarAtualizacao(jogadorAtual.getNome() + " usa sua habilidade contra o " + inimigo.getNome() + " dando "+jogadorAtual.getForca()*1.5+" de dano e recebendo "+inimigo.getDano()+" de dano.");
                        painelDeCombate.adicionarAtualizacao("Vida do Guerreiro: "+jogadorAtual.getVida()+" Vida do "+inimigo.getNome()+": " + inimigo.getVidaInimigo());
                        jogadorAtual.usarHabilidade(inimigoAtual);
                        jogadorAtual.setMana(jogadorAtual.getMana() - 10); // Reduz a mana
                        verificarStatusDeCombate(jogadorAtual, inimigoAtual);
                    } else{
                        painelDeCombate.exibirMensagemMana("VOCÊ NÃO TEM MAIS MANA PARA HABILIDADES");
                    }
                    
                } else{ painelDeCombate.adicionarAtualizacao("Tu ta morto boy, para de atacar kkkkkkkkkk");
            }
            });
        }

        private void verificarStatusDeCombate(Personagem jogador, Inimigo inimigo) {
            System.out.println("Vida do jogador: " + jogador.getVida());
            System.out.println("Vida do inimigo: " + inimigo.getVidaInimigo());
        
            if (jogador.getVida() <= 0) {
                jogador.setPosicao(jogador.getX()-40, jogador.getY());
                System.out.println("O jogador foi derrotado!");
                painelDeCombate.exibirTexto(jogador.getNome() + " foi derrotado!");
                painelDeCombate.adicionarAtualizacao("Vida do Guerreiro: "+jogadorAtual.getVida()+" Vida do "+inimigo.getNome()+": " + inimigo.getVidaInimigo());
                PauseTransition pausa = new PauseTransition(Duration.seconds(99999999));
                pausa.setOnFinished(event -> {
                    painelDeCombate.getPainel().setVisible(false); 
                    jogadorAtual = null;
                    inimigoAtual = null;
                });
                pausa.play();
            } else if (inimigo.getVidaInimigo() <= 0 && inimigo.getNome().equals("GUARDA")) {
                painelDeCombate.exibirTexto("Ao derrotar o GUARDA, o guerreiro despertou seu verdadeiro poder e agora tem 2000 de vida!");
                painelDeCombate.adicionarAtualizacao("Vida do Guerreiro: "+jogadorAtual.getVida()+" Vida do "+inimigo.getNome()+": " + inimigo.getVidaInimigo());
                jogador.setPosicao(jogador.getX()-40, jogador.getY());
                PauseTransition pausa = new PauseTransition(Duration.seconds(12));
                pausa.setOnFinished(event -> {
                painelDeCombate.getPainel().setVisible(false); 
                painelDeCombate.limparCaixaDeAtualizacoes(); 
                jogador.ganharExperiencia(10); 
                jogador.setVida(2000);
                inimigoAtual = null;
                });
                pausa.play();
            
                    }else if(inimigo.getVidaInimigo() <= 0 && (inimigo.getNome().equals("LADRAO") || inimigo.getNome().equals("CHEFE"))) {
                painelDeCombate.exibirTexto(inimigo.getNome() + " foi derrotado!");
                painelDeCombate.adicionarAtualizacao("Vida do Guerreiro: "+jogadorAtual.getVida()+" Vida do "+inimigo.getNome()+": " + inimigo.getVidaInimigo());
                jogador.setPosicao(jogador.getX()-40, jogador.getY());
                PauseTransition pausa = new PauseTransition(Duration.seconds(10));
                pausa.setOnFinished(event -> {
                painelDeCombate.getPainel().setVisible(false);
                painelDeCombate.limparCaixaDeAtualizacoes();  
                jogador.ganharExperiencia(10); 
                inimigoAtual = null;
                });
                pausa.play();

            
            } else {
                inimigo.atacar(jogador);
                if (jogador.getVida() <= 0) {
                    jogador.setPosicao(jogador.getX()-40, jogador.getY());
                    System.out.println("O jogador foi derrotado após o ataque do inimigo!");
                    painelDeCombate.exibirTexto("O jogador foi derrotado!");
                    painelDeCombate.adicionarAtualizacao("Vida do Guerreiro: "+jogadorAtual.getVida()+" Vida do "+inimigo.getNome()+": " + inimigo.getVidaInimigo());
                    PauseTransition pausa = new PauseTransition(Duration.seconds(99999999));
                    pausa.setOnFinished(event -> {
                        painelDeCombate.getPainel().setVisible(false); 
                        jogadorAtual = null;
                        inimigoAtual = null;
                    });
                    pausa.play();
                }
            }
        }

        
        private void checarColisao(Personagem jogador) {
            CaixaDeColisao playerBox = new CaixaDeColisao(jogador.getX(), jogador.getY(), spriteWidth, spriteHeight);
            CaixaDeColisao ladraoBox = new CaixaDeColisao(this.ladrao.getX(), this.ladrao.getY(), 16, 32);
            CaixaDeColisao guardaBox = new CaixaDeColisao(this.guarda.getX(), this.guarda.getY(), 48, 48);
            CaixaDeColisao chefeBox = new CaixaDeColisao(this.chefe.getX(), this.chefe.getY(), 80, 80);
        
            //inicia combate só quando o inimigo está vivo
            if (playerBox.encosta(ladraoBox) && ladrao.getVidaInimigo() != 0) {
                iniciarCombate(jogador, ladrao);
            } else if (playerBox.encosta(guardaBox) && guarda.getVidaInimigo() != 0) {
                iniciarCombate(jogador, guarda);
            } else if (playerBox.encosta(chefeBox) && chefe.getVidaInimigo() != 0) {
                iniciarCombate(jogador, chefe);
            }
        }

        private void exibirTelaFinal() {
            StackPane finalScreen = new StackPane();
            finalScreen.setStyle("-fx-background-color: black;");
        
            javafx.scene.control.Label finalMessage = new javafx.scene.control.Label(
    "A rainha muito generosamente te recompensou pelo seu incrível trabalho, se apaixonou por você e acabou traindo o rei.\n" +
    "Infelizmente, para ela, vocês foram descobertos e ambos foram enforcados na praça pública central.\n" +
    "Mais sorte da próxima vez!"
);
            finalMessage.setTextFill(Color.WHITE);
            finalMessage.setStyle("-fx-font-size: 20px; -fx-padding: 20px;");
            finalScreen.getChildren().add(finalMessage);
        
            Scene finalScene = new Scene(finalScreen, 800, 600);
            Stage finalStage = new Stage();
            finalStage.setScene(finalScene);
            finalStage.setTitle("Tela Final");
            finalStage.show();
        }
        
        public void verificarCondicoes(){
            if((((ladrao.getVidaInimigo() <= 0 && guarda.getVidaInimigo() <= 0 && chefe.getVidaInimigo() <= 0)) && ((guerreiro.getX()>=1250 && guerreiro.getX() <= 1320) && (guerreiro.getY() >=220 && guerreiro.getY() <= 300))) && !telaFinalExibida){

                    exibirTelaFinal();
                    telaFinalExibida = true;
                    this.primaryStage.close();

                
            }
        }



        public static void main(String[] args) {
            TelaInicial.main(args);
            
        }

    }
