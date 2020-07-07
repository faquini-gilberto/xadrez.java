package chess;

import boardgame.Board;

public class ChessMatch {

		private Board board;
		
		public ChessMatch() {
			board = new Board(8, 8);
		}
		
		public ChessPiece[][] getPieces() {
			ChessPiece[][] matriz = new ChessPiece[board.getRows()][board.getColumns()];
			//ChessPiece[][] matriz = new ChessPiece[8][8];
			for (int l=0; l<board.getRows(); l++) {
				for (int c=0; c<board.getColumns(); c++) {
					matriz[l][c] = (ChessPiece) board.piece(l, c);
				}
				
			}
			return matriz;
		}
}
