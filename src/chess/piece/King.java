package chess.piece;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook & p.getColor() == getColor() && p.getMoveCount() == 0;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// Acima
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			matriz[p.getRow()][p.getColumn()] = true;
		}

		// Abaixo
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			matriz[p.getRow()][p.getColumn()] = true;
		}
		// Esquerda
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			matriz[p.getRow()][p.getColumn()] = true;
		}
		// Direita
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			matriz[p.getRow()][p.getColumn()] = true;
		}
		// Diagonal Acima Esquerda
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			matriz[p.getRow()][p.getColumn()] = true;
		}
		// Diagonal Acima Direita
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			matriz[p.getRow()][p.getColumn()] = true;
		}
		// Diagonal Abaixo Esquerda
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			matriz[p.getRow()][p.getColumn()] = true;
		}
		// Diagonal Abaixo Direita
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			matriz[p.getRow()][p.getColumn()] = true;
		}

		// Roque
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// Lado do Rei - roque pequeno
			Position torre1 = new Position(position.getRow(), position.getColumn() + 3);
			if (testRookCastling(torre1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					matriz[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			// Lado da Rainha - roque grande
			Position torre2 = new Position(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(torre2)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					matriz[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}

		return matriz;
	}

}
