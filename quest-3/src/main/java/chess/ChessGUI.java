package chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChessGUI extends Application {

    private static final int BOARD_SIZE = 10; // Chinese Chess board is 10x9
    private static final int SQUARE_SIZE = 80;
    private GameBoard gameBoard;
    private StackPane selectedSquare = null;
    private int sourceRow = -1;
    private int sourceCol = -1;
    private GridPane chessBoardUI;
    private Label statusLabel;
    private String currentPlayer = "Red"; // Red starts the game

    @Override
    public void start(Stage primaryStage) {
        gameBoard = new GameBoard();
        initializeBoard();

        primaryStage.setTitle("Chinese Chess");

        BorderPane root = new BorderPane();
        chessBoardUI = new GridPane();
        updateBoardUI();
        root.setCenter(chessBoardUI);

        statusLabel = new Label(currentPlayer + "'s Turn");
        statusLabel.setFont(new Font("Arial", 20));
        root.setTop(statusLabel);

        Scene scene = new Scene(root, 9 * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE + 50); // Add space for status label
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateBoardUI() {
        chessBoardUI.getChildren().clear(); // Clear existing pieces and squares
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < 9; col++) { // Chinese Chess board is 9 columns wide
                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
                square.setFill(Color.web("#F0D9B5")); // All squares are light for now, will add lines later
                square.setStroke(Color.GRAY);
                square.setStrokeWidth(0.5);

                StackPane stack = new StackPane();
                stack.getChildren().add(square);

                Piece piece = gameBoard.getPiece(row + 1, col + 1); // Adjust for 1-based indexing in GameBoard
                if (piece != null) {
                    ImageView pieceImageView = getPieceImageView(piece.getType());
                    if (pieceImageView != null) {
                        stack.getChildren().add(pieceImageView);
                    } else {
                        // Fallback to text if image not found
                        Text pieceText = new Text(piece.getType());
                        pieceText.setFont(new Font("Arial", 20));
                        if (piece.getType().startsWith("Red")) {
                            pieceText.setFill(Color.RED);
                        } else {
                            pieceText.setFill(Color.BLACK);
                        }
                        stack.getChildren().add(pieceText);
                    }
                }

                final int r = row;
                final int c = col;
                stack.setOnMouseClicked(event -> handleSquareClick(stack, r, c));

                chessBoardUI.add(stack, c, r);
            }
        }
    }

    private ImageView getPieceImageView(String pieceType) {
        String imagePath = "/images/" + pieceType.toLowerCase().replace(" ", "_") + ".png";
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(SQUARE_SIZE * 0.8);
            imageView.setFitHeight(SQUARE_SIZE * 0.8);
            return imageView;
        } catch (Exception e) {
            System.err.println("Could not load image: " + imagePath + ", Error: " + e.getMessage());
            return null;
        }
    }

    private void handleSquareClick(StackPane clickedSquare, int row, int col) {
        if (selectedSquare == null) {
            // No piece selected, select this one
            Piece piece = gameBoard.getPiece(row + 1, col + 1);
            if (piece != null && piece.getType().startsWith(currentPlayer)) {
                selectedSquare = clickedSquare;
                sourceRow = row;
                sourceCol = col;
                selectedSquare.setStyle("-fx-border-color: blue; -fx-border-width: 2;");
            } else if (piece != null) {
                System.out.println("It's " + currentPlayer + "'s turn.");
            }
        } else {
            // A piece is already selected, attempt to move
            Piece movingPiece = gameBoard.getPiece(sourceRow + 1, sourceCol + 1);
            if (movingPiece != null) {
                boolean moveSuccessful = gameBoard.movePiece(movingPiece.getType(), sourceRow + 1, sourceCol + 1, row + 1, col + 1);
                if (moveSuccessful) {
                    updateBoardUI(); // Update the entire board
                    if (gameBoard.isGameOver()) {
                        statusLabel.setText("Game Over! " + currentPlayer + " wins!");
                    } else {
                        currentPlayer = (currentPlayer.equals("Red")) ? "Black" : "Red";
                        statusLabel.setText(currentPlayer + "'s Turn");
                    }
                } else {
                    // Invalid move, deselect the piece
                    System.out.println("Invalid move!");
                }
            }
            // Deselect the piece regardless of move success
            selectedSquare.setStyle(""); // Remove highlight
            selectedSquare = null;
            sourceRow = -1;
            sourceCol = -1;
        }
    }

    private void initializeBoard() {
        // Red Pieces
        gameBoard.placePiece(new Piece("Red Rook"), 1, 1);
        gameBoard.placePiece(new Piece("Red Horse"), 1, 2);
        gameBoard.placePiece(new Piece("Red Elephant"), 1, 3);
        gameBoard.placePiece(new Piece("Red Guard"), 1, 4);
        gameBoard.placePiece(new Piece("Red General"), 1, 5);
        gameBoard.placePiece(new Piece("Red Guard"), 1, 6);
        gameBoard.placePiece(new Piece("Red Elephant"), 1, 7);
        gameBoard.placePiece(new Piece("Red Horse"), 1, 8);
        gameBoard.placePiece(new Piece("Red Rook"), 1, 9);
        gameBoard.placePiece(new Piece("Red Cannon"), 3, 2);
        gameBoard.placePiece(new Piece("Red Cannon"), 3, 8);
        gameBoard.placePiece(new Piece("Red Soldier"), 4, 1);
        gameBoard.placePiece(new Piece("Red Soldier"), 4, 3);
        gameBoard.placePiece(new Piece("Red Soldier"), 4, 5);
        gameBoard.placePiece(new Piece("Red Soldier"), 4, 7);
        gameBoard.placePiece(new Piece("Red Soldier"), 4, 9);

        // Black Pieces
        gameBoard.placePiece(new Piece("Black Rook"), 10, 1);
        gameBoard.placePiece(new Piece("Black Horse"), 10, 2);
        gameBoard.placePiece(new Piece("Black Elephant"), 10, 3);
        gameBoard.placePiece(new Piece("Black Guard"), 10, 4);
        gameBoard.placePiece(new Piece("Black General"), 10, 5);
        gameBoard.placePiece(new Piece("Black Guard"), 10, 6);
        gameBoard.placePiece(new Piece("Black Elephant"), 10, 7);
        gameBoard.placePiece(new Piece("Black Horse"), 10, 8);
        gameBoard.placePiece(new Piece("Black Rook"), 10, 9);
        gameBoard.placePiece(new Piece("Black Cannon"), 8, 2);
        gameBoard.placePiece(new Piece("Black Cannon"), 8, 8);
        gameBoard.placePiece(new Piece("Black Soldier"), 7, 1);
        gameBoard.placePiece(new Piece("Black Soldier"), 7, 3);
        gameBoard.placePiece(new Piece("Black Soldier"), 7, 5);
        gameBoard.placePiece(new Piece("Black Soldier"), 7, 7);
        gameBoard.placePiece(new Piece("Black Soldier"), 7, 9);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
