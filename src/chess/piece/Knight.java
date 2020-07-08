package chess.piece;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "C";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];
		return matriz;
	}
}
