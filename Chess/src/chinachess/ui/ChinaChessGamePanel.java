package chinachess.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import chinachess.ChinaChess;
import chinachess.pieces.Bishop;
import chinachess.pieces.Guardian;
import chinachess.pieces.Gun;
import chinachess.pieces.King;
import chinachess.pieces.Knight;
import chinachess.pieces.Pawn;
import chinachess.pieces.Rook;
import chinachess.ui.images.ChinaChessImages;
import game.core.Game;
import game.core.Piece;
import game.core.PieceColor;
import game.ui.AsiaBoardWithCastle;
import game.ui.GamePanel;
import game.ui.listeners.MovePieceListener;

/**
 * Панель для игры в китайские шахматы.
 * 
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ChinaChessGamePanel extends GamePanel {
	public ChinaChessGamePanel(Composite parent) {
		super(parent, new ChinaChess());
		
		insertSquares( new ChinaChessBoardPanel(this, game) );
	}
}

