package boardgame;

public class Piece {

		protected Piece piece; 
		private Board board;
		
		public Piece (Board board) {
			this.board = board;
			piece = null;  // n�o precisa fazer isso, s� did�tico
		}

		protected Board getBoard() {
			return board;
		}
		
		
}
