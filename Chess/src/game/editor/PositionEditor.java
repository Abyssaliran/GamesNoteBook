package game.editor;

import chess.ui.ChessBoardPanel;
import game.core.Board;
import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.AdornedBoard;
import game.ui.GamePanel;
import game.ui.MovesJornal;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import java.util.List;

/**
 * Редактор начальных позиций игр.
 */
public class PositionEditor extends Composite {
    private final Game game;
    private final Board board;

    /**
     * Экземпляры белых фигур.
     */
    private final List<Piece> whitePieces;

    /**
     * Экземпляры черных фигур.
     */
    private final List<Piece> blackPieces;

    private final GamePanel gamePanel;
    private final Composite boardParent;

    public PositionEditor(Composite parent, GamePanel gamePanel) {
        super(parent, SWT.NONE);
        this.gamePanel = gamePanel;

        game = gamePanel.game;

        board = game.board;
        whitePieces = game.getPieces(PieceColor.WHITE);
        blackPieces = game.getPieces(PieceColor.BLACK);

        FillLayout layout = new FillLayout(SWT.VERTICAL);
        layout.spacing = 5;
        setLayout(layout);

        boardParent = gamePanel.adorned.getParent();
//        addBoardAndHistory(this, game);

        new PieceBoxes(this, game);
    }

    private Composite addBoardAndHistory(Composite parent, Game game) {
        Composite container = new Composite(this, SWT.NONE);
//        container.setLayout(new GridLayout(2, false));

//        AdornedBoard adornedBoard = gamePanel.adorned;
//        adornedBoard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//        adornedBoard.insertSquares(gameBoard);
//
//        MovesJornal jornal = new MovesJornal(container, board.history);
//        jornal.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, true));

        return container;
    }

    /**
     * Ящик с фигурами для расстановки их на доске.
     */
    class PieceBoxes extends Composite {
        public PieceBoxes(Composite parent, Game game) {
            super(parent, SWT.BORDER);
            setLayout(new GridLayout(1, true));

            List<Piece> whites = game.getPieces(PieceColor.WHITE);
            for (Piece piece: whites) {
                Image pieceImage = gamePanel.gameBoard.getPieceImage(piece, piece.getColor());

                Button button = new Button(boardParent, SWT.PUSH);
                button.setImage(pieceImage);
            }
        }
    }
}