package com.kubik.ChessWebapp.model.pieces;

import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.model.AbstractPiece;
import com.kubik.ChessWebapp.model.Position;
import com.kubik.ChessWebapp.model.Square;
import com.kubik.ChessWebapp.statics.Color;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends AbstractPiece {
    public Bishop(Position pos, Color color) {
        super(pos, color);

        this.setImgPath("images/" + color.toString().toLowerCase().charAt(0) + "_bishop.png");

        this.setName("B");
    }

    @Override
    public List<Square> getPossibleMoves(Board board) {
        List<Square> result = new ArrayList<>();

        List<Square> temp = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            if (this.getPosition().getX() + i > 7 || this.getPosition().getY() - i < 0) {
                break;
            }
            temp.add(board.getSquareFromPos(new Position(this.getPosition().getX() + i, this.getPosition().getY() - i)));
        }
        result.addAll(temp);
        temp.clear();

        for (int i = 1; i < 8; i++) {
            if (this.getPosition().getX() + i > 7 || this.getPosition().getY() + i > 7) {
                break;
            }
            temp.add(board.getSquareFromPos(new Position(this.getPosition().getX() + i, this.getPosition().getY() + i)));
        }
        result.addAll(temp);
        temp.clear();

        for (int i = 1; i < 8; i++) {
            if (this.getPosition().getX() - i < 0 || this.getPosition().getY() + i > 7) {
                break;
            }
            temp.add(board.getSquareFromPos(new Position(this.getPosition().getX() - i, this.getPosition().getY() + i)));
        }
        result.addAll(temp);
        temp.clear();

        for (int i = 1; i < 8; i++) {
            if (this.getPosition().getX() - i < 0 || this.getPosition().getY() - i < 0) {
                break;
            }
            temp.add(board.getSquareFromPos(new Position(this.getPosition().getX() - i, this.getPosition().getY() - i)));
        }
        result.addAll(temp);

        return result;
    }

    @Override
    public void specificMove(Square square, Board board, Square prevSquare) {

    }
}
