package fisher;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;
import game.core.Game;
import game.core.PieceColor;
import game.players.IPlayer;
import game.players.Neznaika;

public class FisherChess extends Game {
	static {
		addPlayer(FisherChess.class, IPlayer.HOMO_SAPIENCE);
		addPlayer(FisherChess.class, new Neznaika());

		addPieces();
	}

	static
	protected  void addPieces() {
		addPiece(PieceColor.WHITE, new Pawn());
		addPiece(PieceColor.WHITE, new Rook());
		addPiece(PieceColor.WHITE, new Knight());
		addPiece(PieceColor.WHITE, new Bishop());
		addPiece(PieceColor.WHITE, new Queen());
		addPiece(PieceColor.WHITE, new King());

		addPiece(PieceColor.BLACK, new Pawn());
		addPiece(PieceColor.BLACK, new Rook());
		addPiece(PieceColor.BLACK, new Knight());
		addPiece(PieceColor.BLACK, new Bishop());
		addPiece(PieceColor.BLACK, new Queen());
		addPiece(PieceColor.BLACK, new King());
	}

	/**
	 * Расстановка шахматных фигур в начальную позицию.
	 */
	public FisherChess() {
		initBoardDefault();
		
		board.setWhitePlayer( IPlayer.HOMO_SAPIENCE );
		board.setBlackPlayer( new Neznaika() );
	}

	@Override
	public void initBoardDefault() {
		super.initBoard(8, 8);
		
		// Расставляем пешки.
		for (int v = 0; v < board.nV; v++) {
			new Pawn(board.getSquare(v, 1), PieceColor.BLACK);
			new Pawn(board.getSquare(v, 6), PieceColor.WHITE);
		}
	}
}
