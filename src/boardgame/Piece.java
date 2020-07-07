package boardgame;

public class Piece {

		protected Piece piece; 
		private Board board;
		
		public Piece (Board board) {
			this.board = board;
			piece = null;  // não precisa fazer isso, só didático
		}

		protected Board getBoard() {
			return board;
		}
		
		
}
