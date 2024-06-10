package com.kubik.ChessWebapp.model.pieces;

import com.kubik.ChessWebapp.model.AbstractPiece;
import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.model.Position;
import com.kubik.ChessWebapp.model.Square;
import com.kubik.ChessWebapp.statics.PlayerColor;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends AbstractPiece {
    public Bishop(Position position, PlayerColor color) {
        super(position, color);
        this.setImgPath("images/" + color.toString().toLowerCase().charAt(0) + "_bishop.png");
        this.setName("B");
    }

    @Override
    public List<List<Square>> getPossibleMoves(Board board) {
        List<List<Square>> possibleMoves = new ArrayList<>();
        List<Square> direction = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            if (this.getPosition().getX() + i > 7 || this.getPosition().getY() - i < 0) {
                break;
            }
            direction.add(board.getSquareFromPos(new Position(this.getPosition().getX() + i, this.getPosition().getY() - i)));
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
        for (int i = 1; i < 8; i++) {
            if (this.getPosition().getX() - i < 0 || this.getPosition().getY() + i > 7) {
                break;
            }
            direction.add(board.getSquareFromPos(new Position(this.getPosition().getX() - i, this.getPosition().getY() + i)));
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
