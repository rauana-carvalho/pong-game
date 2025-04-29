# Jogo Pong

**Uma versão simples e elegante do clássico jogo de Ping Pong, desenvolvida em Java com Swing!**

---

## Estrutura do Projeto
```
JogoPong/               
├── bin/                 # Arquivos compilados (.class)
├── documentation/       
│   ├── modelagem/       
│   │   └── modelagem-de-classes.jpeg
│   └── relatorio/       
│       └── relatorio.pdf
├── resources/           
│   └── sons/            
│       ├── gameover.wav
│       ├── paddle.wav
│       ├── score.wav
│       ├── start.feio.wav
│       ├── start.wav
│       └── wall.wav
├── src/                 
│   ├── Bola.java
│   ├── ColorPalette.java
│   ├── ElementoJogo.java
│   ├── Janel Jogo.java
│   ├── JogoPong.java
│   ├── Movable.java
│   ├── PainelJogo.java
│   ├── Pontuacao.java
│   ├── Raquete.java
│   ├── Som.java
│   └── TelaInicio.java
├── scripts/             # Scripts de compilação e execução
│   └── run.sh
├── .gitignore           # Arquivos e pastas ignorados pelo Git
├── LICENSE              
└── README.md   
```

---

### Linguagens e Tecnologias

<a href="https://www.java.com/" target="_blank" alt="Java"><img align="center" alt="Java" height="54" width="72" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg"></a>
<a href="https://docs.oracle.com/javase/8/docs/api/javax/swing/package-summary.html" target="_blank" alt="Swing"><img align="center" alt="Swing" height="54" width="72" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original-wordmark.svg"></a>

---

## Como Configurar Localmente

1. **Clone este repositório**
   ```bash
   git clone https://github.com/rauana-carvalho/pong-game.git
   cd pong-game
   ```
2. **Compilação**
   Execute o script `run.sh` localizado na pasta `scripts/` para compilar os arquivos fonte e gerar os arquivos `.class` em `bin/`:

   ```bash
   scripts/run.sh
   ```
3. **Execução**
   Após a compilação, o jogo será executado automaticamente.

---

## Controles do Jogo

### Tela Inicial

- **ENTER**: Inicia a partida.
- **ESC**: Sai do jogo.

### Durante o Jogo

- **W/S**: Movem a raquete do jogador 1.
- **↑/↓**: Movem a raquete do jogador 2.
- **ESC**: Pausa ou retoma o jogo.
- **R** (quando pausado): Reinicia a partida.
- **Q** (quando pausado): Sai do jogo.

---

## Contribuindo

1. Abra uma **issue** para sugerir melhorias ou relatar bugs.
2. **Fork** o projeto e crie uma branch (`git checkout -b feature/nome-da-feature`).
3. Realize suas alterações e **commit** (`git commit -m "Adiciona nova feature"`).
4. **Push** para sua branch (`git push origin feature/nome-da-feature`).
5. Abra um **Pull Request** para revisão.

---

## Autoras

- 🍅 [Rauana Carvalho](https://github.com/rauanacarvalho)
- 🍓 [Bruna Valoes](https://github.com/bruvaloes)

Se gostou do projeto, não esqueça de dar uma estrela! ⭐