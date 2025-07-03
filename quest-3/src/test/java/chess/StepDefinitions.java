package chess;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StepDefinitions {

    private GameBoard board;
    private boolean moveResult;

    @Given("the board is empty except for a Red General at {int}, {int}")
    public void theBoardIsEmptyExceptForARedGeneralAt(int row, int col) {
        board = new GameBoard();
        board.placePiece(new Piece("Red General"), row, col);
    }

    @Given("the board has:")
    public void theBoardHas(DataTable dataTable) {
        board = new GameBoard();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            String pieceType = columns.get("Piece");
            String position = columns.get("Position");
            // Remove parentheses and split by comma
            String[] coords = position.replaceAll("[()]", "").split(",");
            int row = Integer.parseInt(coords[0].trim());
            int col = Integer.parseInt(coords[1].trim());
            board.placePiece(new Piece(pieceType), row, col);
        }
    }

    @When("Red moves the General from {int}, {int} to {int}, {int}")
    public void redMovesTheGeneralFromTo(int fromRow, int fromCol, int toRow, int toCol) {
        moveResult = board.movePiece("Red General", fromRow, fromCol, toRow, toCol);
    }

    @Given("the board is empty except for a Red Guard at {int}, {int}")
    public void theBoardIsEmptyExceptForARedGuardAt(int row, int col) {
        board = new GameBoard();
        board.placePiece(new Piece("Red Guard"), row, col);
    }

    @When("Red moves the Guard from {int}, {int} to {int}, {int}")
    public void redMovesTheGuardFromTo(int fromRow, int fromCol, int toRow, int toCol) {
        moveResult = board.movePiece("Red Guard", fromRow, fromCol, toRow, toCol);
    }

    @Given("the board is empty except for a Red Rook at {int}, {int}")
    public void theBoardIsEmptyExceptForARedRookAt(int row, int col) {
        board = new GameBoard();
        board.placePiece(new Piece("Red Rook"), row, col);
    }

    @When("Red moves the Rook from {int}, {int} to {int}, {int}")
    public void redMovesTheRookFromTo(int fromRow, int fromCol, int toRow, int toCol) {
        moveResult = board.movePiece("Red Rook", fromRow, fromCol, toRow, toCol);
    }

    @Given("the board is empty except for a Red Horse at {int}, {int}")
    public void theBoardIsEmptyExceptForARedHorseAt(int row, int col) {
        board = new GameBoard();
        board.placePiece(new Piece("Red Horse"), row, col);
    }

    @When("Red moves the Horse from {int}, {int} to {int}, {int}")
    public void redMovesTheHorseFromTo(int fromRow, int fromCol, int toRow, int toCol) {
        moveResult = board.movePiece("Red Horse", fromRow, fromCol, toRow, toCol);
    }

    @Given("the board is empty except for a Red Cannon at {int}, {int}")
    public void theBoardIsEmptyExceptForARedCannonAt(int row, int col) {
        board = new GameBoard();
        board.placePiece(new Piece("Red Cannon"), row, col);
    }

    @When("Red moves the Cannon from {int}, {int} to {int}, {int}")
    public void redMovesTheCannonFromTo(int fromRow, int fromCol, int toRow, int toCol) {
        moveResult = board.movePiece("Red Cannon", fromRow, fromCol, toRow, toCol);
    }

    @Given("the board is empty except for a Red Elephant at {int}, {int}")
    public void theBoardIsEmptyExceptForARedElephantAt(int row, int col) {
        board = new GameBoard();
        board.placePiece(new Piece("Red Elephant"), row, col);
    }

    @When("Red moves the Elephant from {int}, {int} to {int}, {int}")
    public void redMovesTheElephantFromTo(int fromRow, int fromCol, int toRow, int toCol) {
        moveResult = board.movePiece("Red Elephant", fromRow, fromCol, toRow, toCol);
    }

    @Given("the board is empty except for a Red Soldier at {int}, {int}")
    public void theBoardIsEmptyExceptForARedSoldierAt(int row, int col) {
        board = new GameBoard();
        board.placePiece(new Piece("Red Soldier"), row, col);
    }

    @When("Red moves the Soldier from {int}, {int} to {int}, {int}")
    public void redMovesTheSoldierFromTo(int fromRow, int fromCol, int toRow, int toCol) {
        moveResult = board.movePiece("Red Soldier", fromRow, fromCol, toRow, toCol);
    }

    @Then("the move is legal")
    public void theMoveIsLegal() {
        assertTrue(moveResult);
    }

    @Then("the move is illegal")
    public void theMoveIsIllegal() {
        assertFalse(moveResult);
    }

    @Then("Red wins immediately")
    public void redWinsImmediately() {
        assertTrue(board.isGameOver());
    }

    @Then("the game is not over just from that capture")
    public void theGameIsNotOverJustFromThatCapture() {
        assertFalse(board.isGameOver());
    }
}
