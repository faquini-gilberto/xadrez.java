package chess;

import boardgame.Board;
import chess.piece.King;
import chess.piece.Rook;

public class ChessMatch {

	private Board board;

	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] matriz = new ChessPiece[board.getRows()][board.getColumns()];
		// ChessPiece[][] matriz = new ChessPiece[8][8];
		for (int l = 0; l < board.getRows(); l++) {
			for (int c = 0; c < board.getColumns(); c++) {
				matriz[l][c] = (ChessPiece) board.piece(l, c);
			}

		}
		return matriz;
	}

	public void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	public void initialSetup() {
		// board.placePiece(new King(board, Color.WHITE), new Position(0, 5));
		// board.placePiece(new King(board, Color.BLACK), new Position(7, 5));
		// board.placePiece(new Rook(board, Color.WHITE), new Position(0, 0));
		// placeNewPiece('f', 8, new King(board, Color.WHITE));
		// placeNewPiece('f', 3, new King(board, Color.BLACK));
		// placeNewPiece('a', 8, new Rook(board, Color.WHITE));
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		placeNewPiece('d', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.WHITE));

		placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}