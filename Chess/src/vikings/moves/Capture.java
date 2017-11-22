package vikings.moves;
 
import java.util.List;
import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;
import vikings.pieces.VikingsPiece;
import vikings.pieces.Сyning;

/*
ПОБЕДА БЕЛЫХ
1.     Если им удаётся поставить короля на открытую прямую к одной из таких клеток, они объявляют «Raichi» (шах), если сразу на две прямые — Tuichi (мат).
 
ПОБЕДА ЧЕРНЫХ
1.     Король считается захваченным, когда его окружают с четырёх сторон. При этом сторонами могут считаться угловые клетки, трон, и стороны доски. Король может быть захвачен вместе с одной белой фишкой, будучи окружён чёрными со всех сторон.
2.     Когда королю угрожает опасность быть захваченным следующим ходом, чёрные предупреждают белых (шах королю).
 
УНИЧТОЖЕНИЕ ФИШЕК
1.     Если фишка своим ходом зажимает фишку противника между собой и другой фишкой или между собой и угловым квадратом, фишка противника считается съеденной. Съедаться может более одной фишки за раз.
2.     Черные фишки может входить между двумя вражескими, в этом случае она не считается съеденной. При этом белые могут спокойно поставить свою фишку между двумя чёрными.
 */

 
public class Capture extends SimpleMove implements ICaptureMove {
           
            private Piece capturedPiece;
            private Square capturedSquare;
           
            public Capture(List<Square> captured, Square[] squares) {
                        super(squares);
                        this.captured = captured;
            }
           
            private List<Square> captured;
            public List<Square> getCaptured() {
                        return captured;
            }
           
            public void undoMove() {
                        super.undoMove();
                        capturedSquare.setPiece(capturedPiece);
            }
           
            public void doMove() throws GameOver{
                       
                        capturedPiece.remove();
                        super.doMove();
                        piece.moveTo(target);
                       
                        if ((piece instanceof Сyning) && piece.hasNext(null)) {
                                   throw new GameOver(GameResult.BLACK_WIN);
                        }
                       
        int whitefigure = piece.getFriends().size();
        int blackfigure = piece.getFriends().size();
        if (whitefigure == 0) {
            throw new GameOver(GameResult.WHITE_WIN);
        }
        if (blackfigure == 0) {
            throw new GameOver(GameResult.BLACK_WIN);
        }
 
 
            }
                       
            public void removePiece() {}
 
            public void restotePiece() {}
           
    public String toString() { return "" + piece + source + "-" + target; }
 
}
 