package com.kubik.ChessWebapp.model.pieces;

import com.kubik.ChessWebapp.model.AbstractPiece;
import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.model.Position;
import com.kubik.ChessWebapp.model.Square;
import com.kubik.ChessWebapp.statics.PlayerColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Knight extends AbstractPiece {
    public Knight(Position position, PlayerColor color) {
        super(position, color);
        this.setImgPath("images/" + color.toString().toLowerCase().charAt(0) + "_knight.png");
        this.setName("N");
    }

    @Override
    public List<List<Square>> getPossibleMoves(Board board) {
        List<List<Square>> possibleMoves = new ArrayList<>();
        int[][] moves = {
                {1, -2}, {2, -1}, {2, 1}, {1, 2},
                {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}
        };
        for (int[] move : moves) {
            int newX = this.getPosition().getX() + move[0];
            int newY = this.getPosition().getY() + move[1];
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                Square square = board.getSquareFromPos(new Position(newX, newY));
                possibleMoves.add(Collections.singletonList(square));
            }
        }
        return possibleMoves;
    }

    @Override
    public void specificMove(Square square, Board board, Square prevSquare) {
    }
}
