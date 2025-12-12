package attract.ui;

import attract.pieces.AttracPiece;
import attract.ui.images.AttractImages;
import game.core.*;
import game.players.IPlayer;
import game.ui.AsiaBoard;
import game.ui.listeners.PutPieceListener;
import org.eclipse.swt.graphics.Image;

import static game.core.PieceColor.WHITE;

public class AttractBoardPanel extends AsiaBoard implements IPieceProvider {
    public AttractBoardPanel(AttractGamePanel composite, Game game) {
        super(composite, game.board);

        // Слушатель мыши для перемещения новой фигуры на доске.
        listener = new PutPieceListener(this);
    }

    @Override
    public Piece getPiece(Square square, PieceColor color) {
        return new AttracPiece(square, color);
    }

    @Override
    public Image getPieceImage(Piece piece, PieceColor color) {
        IPlayer player = color == PieceColor.WHITE ? board.getWhitePlayer() : board.getBlackPlayer();

        Image image = player.getImage();
        if (image != null)
            return image;

        // 白色棋子使用熊图标，黑色棋子使用熊猫图标
        // White pieces use bear icon, black pieces use panda icon.
        // Белые фигуры используют иконку медведя, черные — иконку панды.
        return color == WHITE ? AttractImages.bearImage : AttractImages.pandaImage;
    }
}
