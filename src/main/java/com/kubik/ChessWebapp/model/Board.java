package com.kubik.ChessWebapp.model;

import com.kubik.ChessWebapp.entity.ChessUser;
import com.kubik.ChessWebapp.model.pieces.*;
import com.kubik.ChessWebapp.statics.BoardStatus;
import com.kubik.ChessWebapp.statics.GameResult;
import com.kubik.ChessWebapp.statics.PlayerColor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Board {
    private String id;
    private ChessUser firstChessPlayer;
    private ChessUser secondChessPlayer;
    private GameResult gameResult;
    private AbstractPiece selectedPiece;
    private PlayerColor turn;
    private List<Square> squares;
    private BoardStatus boardStatus;
    private final static String[][] config = {
            {"bR", "bN", "bB", "bQ", "bK", "bB", "bN", "bR"},
            {"bP", "bP", "bP", "bP", "bP", "bP", "bP", "bP"},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
            {"wP", "wP", "wP", "wP", "wP", "wP", "wP", "wP"},
            {"wR", "wN", "wB", "wQ", "wK", "wB", "wN", "wR"}
    };

    public Board() {
        this.id = UUID.randomUUID().toString();
        this.selectedPiece = null;
        this.turn = PlayerColor.WHITE_PLAYER;
        this.boardStatus = BoardStatus.NEW;
        this.gameResult = null;
        this.squares = squaresInit();
        setField();
    }

    private List<Square> squaresInit() {
        List<Square> result = new ArrayList<>();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                result.add(new Square(new Position(x, y)));
            }
        }
        return result;
    }

    public Square getSquareFromPos(Position position) {
        for (Square square : squares) {
            Position squarePosition = square.getPosition();
            if (squarePosition.getX() == position.getX() && squarePosition.getY() == position.getY()) {
                return square;
            }
        }
        return null;
    }

    public AbstractPiece getPieceFromPos(Position position) {
        Square square = getSquareFromPos(position);
        return square != null ? square.getOccupyingPiece() : null;
    }

    private void setField() {
        for (int y = 0; y < config.length; y++) {
            for (int x = 0; x < config[y].length; x++) {
                String piece = config[y][x];
                if (!"  ".equals(piece)) {
                    Square square = getSquareFromPos(new Position(x, y));
                    PlayerColor color = piece.charAt(0) == 'w' ? PlayerColor.WHITE_PLAYER : PlayerColor.BLACK_PLAYER;
                    switch (piece.charAt(1)) {
                        case 'R':
                            square.setOccupyingPiece(new Rook(new Position(x, y), color));
                            break;
                        case 'N':
                            square.setOccupyingPiece(new Knight(new Position(x, y), color));
                            break;
                        case 'B':
                            square.setOccupyingPiece(new Bishop(new Position(x, y), color));
                            break;
                        case 'Q':
                            square.setOccupyingPiece(new Queen(new Position(x, y), color));
                            break;
                        case 'K':
                            square.setOccupyingPiece(new King(new Position(x, y), color));
                            break;
                        case 'P':
                            square.setOccupyingPiece(new Pawn(new Position(x, y), color));
                            break;
                    }
                }
            }
        }
    }

    public void mouseClick(int x, int y) {
        System.out.println(x + " and " + y);
        Square clickedSquare = getSquareFromPos(new Position(x, y));
        if (selectedPiece == null) {
            if (clickedSquare.getOccupyingPiece() != null && clickedSquare.getOccupyingPiece().getColor().equals(turn)) {
                selectedPiece = clickedSquare.getOccupyingPiece();
                showMoves();
            }
        } else if (selectedPiece.move(this, clickedSquare, false)) {
            turn = PlayerColor.togglePlayerTurn(turn);
        }
        checkGameOver();
    }

    public boolean isInCheck(PlayerColor color, Position[] AttackingKingPositions) {
        boolean result = false;
        Position kingPos = null;
        AbstractPiece changingPiece = null;
        Square oldSquare = null;
        Square newSquare = null;
        AbstractPiece newSquareOldPiece = null;
        if (AttackingKingPositions != null) {
            for (Square square : squares) {
                if (square.getPosition().equals(AttackingKingPositions[0])) {
                    changingPiece = square.getOccupyingPiece();
                    oldSquare = square;
                    oldSquare.setOccupyingPiece(null);
                    break;
                }
            }
            for (Square square : squares) {
                if (square.getPosition().equals(AttackingKingPositions[1])) {
                    newSquare = square;
                    newSquareOldPiece = newSquare.getOccupyingPiece();
                    newSquare.setOccupyingPiece(changingPiece);
                    break;
                }
            }
        }
        List<AbstractPiece> pieces = new ArrayList<>();
        for (Square square : squares) {
            if (square.getOccupyingPiece() != null) {
                pieces.add(square.getOccupyingPiece());
            }
        }
        if (changingPiece != null && "K".equals(changingPiece.getName())) {
            kingPos = newSquare.getPosition();
        }
        if (kingPos == null) {
            for (AbstractPiece piece : pieces) {
                if ("K".equals(piece.getName()) && color.equals(piece.getColor())) {
                    kingPos = piece.getPosition();
                    break;
                }
            }
        }
        for (AbstractPiece piece : pieces) {
            if (!color.equals(piece.getColor())) {
                for (Square square : piece.getMoves(this)) {
                    if (square.getPosition().equals(kingPos)) {
                        result = true;
                        break;
                    }
                }
            }
        }
        if (AttackingKingPositions != null) {
            oldSquare.setOccupyingPiece(changingPiece);
            newSquare.setOccupyingPiece(newSquareOldPiece);
        }
        return result;
    }

    public boolean isInCheckmate(PlayerColor color) {
        if (isInCheck(color, null)) {
            List<AbstractPiece> kingPieces = new ArrayList<>();
            for (Square square : squares) {
                if (square.getOccupyingPiece() != null && color.equals(square.getOccupyingPiece().getColor())) {
                    kingPieces.add(square.getOccupyingPiece());
                }
            }
            for (AbstractPiece piece : kingPieces) {
                if (!piece.getValidMoves(this).isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isStalemate(PlayerColor color) {
        for (Square square : squares) {
            if (square.getOccupyingPiece() != null && color.equals(square.getOccupyingPiece().getColor())) {
                if (!square.getOccupyingPiece().getValidMoves(this).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void showMoves() {
        if (selectedPiece != null) {
            getSquareFromPos(selectedPiece.getPosition()).setHighlight(true);
            for (Square square : selectedPiece.getValidMoves(this)) {
                square.setHighlight(true);
            }
        }
    }

    public GameResult checkGameOver() {
        if (isInCheckmate(PlayerColor.WHITE_PLAYER)) {
            return GameResult.BLACK_PLAYER_WIN;
        } else if (isInCheckmate(PlayerColor.BLACK_PLAYER)) {
            return GameResult.WHITE_PLAYER_WIN;
        } else if (isStalemate(turn)) {
            return GameResult.STALEMATE;
        }
        return null;
    }
}
