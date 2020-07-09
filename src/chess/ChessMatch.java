package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.piece.King;
import chess.piece.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	public Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	public ChessPiece king(Color color) {
		List<Piece> lista = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : lista) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("Nao tem Rei " + color + " no tabuleiro");
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPiece = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPiece) {
			boolean[][] matriz = p.possibleMoves();
			if (matriz[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> lista = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : lista) {
			boolean[][] matriz = p.possibleMoves();
			for (int i=0; i<board.getRows(); i++) {
				for (int j=0; j<board.getColumns(); j++) {
					if (matriz[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testaXeque = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testaXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
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
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("Voce nao pode se colocar em Xeque");
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
				nextTurn();
		}
		return (ChessPiece) capturedPiece;
		
	}
	
	private Piece makeMove(Position source, Position target) {
		//Piece p = board.removePiece(source);
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		if (capturedPiece != null) {
			capturedPieces.add(capturedPiece);
			piecesOnTheBoard.remove(capturedPiece);
		}
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		//Piece p = board.removePiece(target);
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("Nao existe peca na posicao " + position);
		}
		
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("Voce escolheu peca de cor errada!!");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("Nao ha movimento possivel para a peca");
		}
	}
	
	public void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).posssibleMove(target)) {
			throw new ChessException("Destino invalido.");
		}
	}

	public void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	public void initialSetup() {
		// board.placePiece(new King(board, Color.WHITE), new Position(0, 5));
		// board.placePiece(new King(board, Color.BLACK), new Position(7, 5));
		// board.placePiece(new Rook(board, Color.WHITE), new Position(0, 0));
		// placeNewPiece('f', 8, new King(board, Color.WHITE));
		// placeNewPiece('f', 3, new King(board, Color.BLACK));
		// placeNewPiece('a', 8, new Rook(board, Color.WHITE));
		placeNewPiece('h', 7, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new Rook(board, Color.WHITE));
		//placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		//placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE));

		//placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		//placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		//placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		//placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 8, new King(board, Color.BLACK));
	}
}
