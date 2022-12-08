package tamerlan.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.EuropeBoard;
import game.ui.GamePanel;
import game.ui.listeners.MovePieceListener;
import tamerlan.TamerlanChess;
import tamerlan.pieces.Bishop;
import tamerlan.pieces.Giraffe;
import tamerlan.pieces.King;
import tamerlan.pieces.Knight;
import tamerlan.pieces.Pawn;
import tamerlan.pieces.Queen;
import tamerlan.pieces.Rook;
import tamerlan.pieces.Vizir;
import tamerlan.pieces.WarMachine;
import tamerlan.ui.images.TamerlanChessImages;

/**
 * Панель для игры в шахматы.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class TamerlanChessGamePanel extends GamePanel {

	public TamerlanChessGamePanel(Composite parent) {
		super(parent, new TamerlanChess());
		
		insertSquares( new TamerlanChessBoardPanel(this, game) );
	}
}

