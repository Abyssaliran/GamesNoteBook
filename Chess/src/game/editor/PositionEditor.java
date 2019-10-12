package game.editor;

import game.core.*;
import game.ui.AdornedBoard;
import game.ui.GameBoard;
import game.ui.GamePanel;

import java.util.List;

/**
 * Редактор начальных позиций игр.
 */
public class PositionEditor {
    private final Game game;
    private final Board board;

    /**
     * Экземпляры белых фигур.
     */
//    private final List<Piece> whitePieces;

    /**
     * Экземпляры черных фигур.
     */
//    private final List<Piece> blackPieces;

    public PositionEditor(GamePanel gamePanel) {
        game = gamePanel.game;
        board = game.board;

        // Создаем доску с ящиками для фигур.
        // В ящиках фигуры подготовленные для расстановки на доске.
        BoardWithBoxes boardForEdit = new BoardWithBoxes();
        boardForEdit.topBox = game.getPieces(PieceColor.BLACK);
        boardForEdit.bottomBox = game.getPieces(PieceColor.WHITE);
        boardForEdit.reset(game.board.nV, game.board.nV);
        game.board = boardForEdit;

        AdornedBoard adorned = gamePanel.adorned;
        gamePanel.gameBoard.board = boardForEdit;
        adorned.updatePieceBoxes();
    }
}