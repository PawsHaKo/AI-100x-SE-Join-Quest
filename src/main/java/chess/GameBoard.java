package chess;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {
    private Map<String, Piece> boardState;

    public GameBoard() {
        boardState = new HashMap<>();
    }

    public void placePiece(Piece piece, int row, int col) {
        boardState.put(row + "," + col, piece);
    }

    public Piece getPiece(int row, int col) {
        return boardState.get(row + "," + col);
    }

    public boolean movePiece(String pieceType, int fromRow, int fromCol, int toRow, int toCol) {
        String[] parts = pieceType.split(" ");
        String color = parts[0];
        String basePieceType = parts[1];

        boolean legalMove = false;

        switch (basePieceType) {
            case "General":
                legalMove = isValidGeneralMove(fromRow, fromCol, toRow, toCol, color);
                break;
            case "Guard":
                legalMove = isValidGuardMove(fromRow, fromCol, toRow, toCol, color);
                break;
            case "Rook":
                legalMove = isValidRookMove(fromRow, fromCol, toRow, toCol, color);
                break;
            case "Horse":
                legalMove = isValidHorseMove(fromRow, fromCol, toRow, toCol, color);
                break;
            case "Cannon":
                legalMove = isValidCannonMove(fromRow, fromCol, toRow, toCol, color);
                break;
            case "Elephant":
                legalMove = isValidElephantMove(fromRow, fromCol, toRow, toCol, color);
                break;
            case "Soldier":
                legalMove = isValidSoldierMove(fromRow, fromCol, toRow, toCol, color);
                break;
        }

        if (legalMove) {
            // Remove captured piece if any
            if (getPiece(toRow, toCol) != null) {
                boardState.remove(toRow + "," + toCol);
            }
            Piece movingPiece = getPiece(fromRow, fromCol);
            boardState.remove(fromRow + "," + fromCol);
            placePiece(movingPiece, toRow, toCol);
            return true;
        }
        return false;
    }

    private boolean isValidElephantMove(int fromRow, int fromCol, int toRow, int toCol, String color) {
        // Check if move is 2-step diagonal
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        if (!(rowDiff == 2 && colDiff == 2)) {
            return false;
        }

        // Check if crossing the river
        if (color.equals("Red")) {
            if (toRow > 5) {
                return false;
            }
        } else { // Black Elephant
            if (toRow < 6) {
                return false;
            }
        }

        // Check for blocking pieces at the midpoint
        if (getPiece(fromRow + (toRow - fromRow) / 2, fromCol + (toCol - fromCol) / 2) != null) {
            return false;
        }

        return true;
    }

    private boolean isValidSoldierMove(int fromRow, int fromCol, int toRow, int toCol, String color) {
        // Before crossing the river
        if (color.equals("Red")) {
            if (fromRow <= 5) {
                if (toRow == fromRow + 1 && toCol == fromCol) {
                    return true;
                }
            } else { // After crossing the river
                if ((toRow == fromRow + 1 && toCol == fromCol) || (Math.abs(toCol - fromCol) == 1 && toRow == fromRow)) {
                    return true;
                }
            }
        } else { // Black Soldier
            if (fromRow >= 6) {
                if (toRow == fromRow - 1 && toCol == fromCol) {
                    return true;
                }
            } else { // After crossing the river
                if ((toRow == fromRow - 1 && toCol == fromCol) || (Math.abs(toCol - fromCol) == 1 && toRow == fromRow)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidCannonMove(int fromRow, int fromCol, int toRow, int toCol, String color) {
        // Check if move is horizontal or vertical
        if (fromRow != toRow && fromCol != toCol) {
            return false;
        }

        int piecesInPath = 0;
        if (fromRow == toRow) { // Horizontal move
            int startCol = Math.min(fromCol, toCol) + 1;
            int endCol = Math.max(fromCol, toCol);
            for (int c = startCol; c < endCol; c++) {
                if (getPiece(fromRow, c) != null) {
                    piecesInPath++;
                }
            }
        } else { // Vertical move
            int startRow = Math.min(fromRow, toRow) + 1;
            int endRow = Math.max(fromRow, toRow);
            for (int r = startRow; r < endRow; r++) {
                if (getPiece(r, fromCol) != null) {
                    piecesInPath++;
                }
            }
        }

        if (getPiece(toRow, toCol) == null) { // Non-capture move
            return piecesInPath == 0;
        } else { // Capture move
            return piecesInPath == 1;
        }
    }

    private boolean isValidHorseMove(int fromRow, int fromCol, int toRow, int toCol, String color) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // Check for L-shape move
        if (!((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2))) {
            return false;
        }

        // Check for blocking pieces
        if (rowDiff == 2) { // Vertical L-move
            if (getPiece(fromRow + (toRow - fromRow) / 2, fromCol) != null) {
                return false;
            }
        } else { // Horizontal L-move
            if (getPiece(fromRow, fromCol + (toCol - fromCol) / 2) != null) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidGeneralMove(int fromRow, int fromCol, int toRow, int toCol, String color) {
        // Check if move is within the palace
        boolean inPalace;
        if (color.equals("Red")) {
            inPalace = (toRow >= 1 && toRow <= 3 && toCol >= 4 && toCol <= 6);
        } else { // Black General
            inPalace = (toRow >= 8 && toRow <= 10 && toCol >= 4 && toCol <= 6);
        }
        if (!inPalace) {
            return false;
        }

        // Check if move is one step orthogonally
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        if (!((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1))) {
            return false;
        }

        // Check for general facing rule
        int currentGeneralRow = toRow;
        int currentGeneralCol = toCol;
        int opponentGeneralRow = -1;
        int opponentGeneralCol = -1;

        for (Map.Entry<String, Piece> entry : boardState.entrySet()) {
            if (entry.getValue() != null) {
                String pieceType = entry.getValue().getType();
                if (color.equals("Red") && pieceType.equals("Black General")) {
                    String[] coords = entry.getKey().split(",");
                    opponentGeneralRow = Integer.parseInt(coords[0]);
                    opponentGeneralCol = Integer.parseInt(coords[1]);
                    break;
                } else if (color.equals("Black") && pieceType.equals("Red General")) {
                    String[] coords = entry.getKey().split(",");
                    opponentGeneralRow = Integer.parseInt(coords[0]);
                    opponentGeneralCol = Integer.parseInt(coords[1]);
                    break;
                }
            }
        }

        if (opponentGeneralRow != -1 && currentGeneralCol == opponentGeneralCol) {
            boolean pathClear = true;
            int startRow = Math.min(currentGeneralRow, opponentGeneralRow) + 1;
            int endRow = Math.max(currentGeneralRow, opponentGeneralRow);

            for (int r = startRow; r < endRow; r++) {
                if (getPiece(r, currentGeneralCol) != null) {
                    pathClear = false;
                    break;
                }
            }
            if (pathClear) {
                return false; // Illegal move if path is clear and generals face each other
            }
        }
        return true;
    }

    private boolean isValidGuardMove(int fromRow, int fromCol, int toRow, int toCol, String color) {
        // Check if move is within the palace
        boolean inPalace;
        if (color.equals("Red")) {
            inPalace = (toRow >= 1 && toRow <= 3 && toCol >= 4 && toCol <= 6);
        } else { // Black Guard
            inPalace = (toRow >= 8 && toRow <= 10 && toCol >= 4 && toCol <= 6);
        }
        if (!inPalace) {
            return false;
        }

        // Check if move is one step diagonally
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        if (!(rowDiff == 1 && colDiff == 1)) {
            return false;
        }
        return true;
    }

    private boolean isValidRookMove(int fromRow, int fromCol, int toRow, int toCol, String color) {
        // A piece cannot move to its current position
        if (fromRow == toRow && fromCol == toCol) {
            return false;
        }

        // Check if move is horizontal or vertical
        if (fromRow != toRow && fromCol != toCol) {
            return false;
        }

        // Check if path is clear
        if (fromRow == toRow) { // Horizontal move
            int startCol = Math.min(fromCol, toCol) + 1;
            int endCol = Math.max(fromCol, toCol);
            for (int c = startCol; c < endCol; c++) {
                if (getPiece(fromRow, c) != null) {
                    return false; // Path is blocked
                }
            }
        } else { // Vertical move
            int startRow = Math.min(fromRow, toRow) + 1;
            int endRow = Math.max(fromRow, toCol);
            for (int r = startRow; r < endRow; r++) {
                if (getPiece(r, fromCol) != null) {
                    return false; // Path is blocked
                }
            }
        }
        return true;
    }

    public boolean isGameOver() {
        boolean redGeneralExists = false;
        boolean blackGeneralExists = false;
        for (Map.Entry<String, Piece> entry : boardState.entrySet()) {
            if (entry.getValue() != null) {
                if (entry.getValue().getType().equals("Red General")) {
                    redGeneralExists = true;
                } else if (entry.getValue().getType().equals("Black General")) {
                    blackGeneralExists = true;
                }
            }
        }
        return !redGeneralExists || !blackGeneralExists;
    }
}
