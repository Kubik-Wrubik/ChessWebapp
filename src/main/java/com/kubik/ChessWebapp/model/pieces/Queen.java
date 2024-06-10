package com.kubik.ChessWebapp.model.pieces;

import com.kubik.ChessWebapp.model.AbstractPiece;
import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.model.Position;
import com.kubik.ChessWebapp.model.Square;
import com.kubik.ChessWebapp.statics.PlayerColor;

import java.util.ArrayList;
import java.util.List;

public class Queen extends AbstractPiece {
    public Queen(Position position, PlayerColor color) {
        super(position, color);
        this.setImgPath("images/" + color.name().toLowerCase().charAt(0) + "_queen.png");
        this.setName("Q");
    }

    @Override
    public List<List<Square>> getPossibleMoves(Board board) {
        List<List<Square>> possibleMoves = new ArrayList<>();
        List<Square> direction = new ArrayList<>();
        for (int y = this.getPosition().getY() - 1; y >= 0; y--) {
            direction.add(board.getSquareFromPos(new Position(this.getPosition().getX(), y)));
        }
        possibleMoves.add(new ArrayList<>(direction));
        direction.clear();
        for (int i = 1; i < 8; i++) {
            if (this.getPosition().getX() + i > 7 || this.getPosition().getY() - i < 0) {
                break;
            }
            direction.add(board.getSquareFromPos(new Position(this.getPosition().getX() + i, this.getPosition().getY() - i)));
        }
        possibleMoves.add(new ArrayList<>(direction));
        direction.clear();
        for (int x = this.getPosition().getX() + 1; x < 8; x++) {
            direction.add(board.getSquareFromPos(new Position(x, this.getPosition().getY())));
        }
        possibleMoves.add(new ArrayList<>(direction));
        direction.clear();
        for (int i = 1; i < 8; i++) {
            if (this.getPosition().getX() + i > 7 || this.getPosition().getY() + i > 7) {
                break;
            }
            direction.add(board.getSquareFromPos(new Position(this.getPosition().getX() + i, this.getPosition().getY() + i)));
        }
        possibleMoves.add(new ArrayList<>(direction));
        direction.clear();
        for (int y = this.getPosition().getY() + 1; y < 8; y++) {
            direction.add(board.getSquareFromPos(new Position(this.getPosition().getX(), y)));
        }
        possibleMoves.add(new ArrayList<>(direction));
        direction.clear();
        for (int i = 1; i < 8; i++) {
            if (this.getPosition().getX() - i < 0 || this.getPosition().getY() + i > 7) {
                break;
            }
            direction.add(board.getSquareFromPos(new Position(this.getPosition().getX() - i, this.getPosition().getY() + i)));
        }
        possibleMoves.add(new ArrayList<>(direction));
        direction.clear();
        for (int x = this.getPosition().getX() - 1; x >= 0; x--) {
            direction.add(board.getSquareFromPos(new Position(x, this.getPosition().getY())));
        }
        possibleMoves.add(new ArrayList<>(direction));
        direction.clear();
        for (int i = 1; i < 8; i++) {
            if (this.getPosition().getX() - i < 0 || this.getPosition().getY() - i < 0) {
                break;
            }
            direction.add(board.getSquareFromPos(new Position(this.getPosition().getX() - i, this.getPosition().getY() - i)));
        }
        possibleMoves.add(new ArrayList<>(direction));
        return possibleMoves;
    }

    @Override
    public void specificMove(Square square, Board board, Square prevSquare) {

    }
}
