package com.kubik.ChessWebapp.model.pieces;

import com.kubik.ChessWebapp.model.AbstractPiece;
import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.model.Position;
import com.kubik.ChessWebapp.model.Square;
import com.kubik.ChessWebapp.statics.PlayerColor;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractPiece {
    public Rook(Position position, PlayerColor color) {
        super(position, color);
        this.setImgPath("images/" + color.name().toLowerCase().charAt(0) + "_rook.png");
        this.setName("R");
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
        for (int x = this.getPosition().getX() + 1; x < 8; x++) {
            direction.add(board.getSquareFromPos(new Position(x, this.getPosition().getY())));
        }
        possibleMoves.add(new ArrayList<>(direction));
        direction.clear();
        for (int y = this.getPosition().getY() + 1; y < 8; y++) {
            direction.add(board.getSquareFromPos(new Position(this.getPosition().getX(), y)));
        }
        possibleMoves.add(new ArrayList<>(direction));
        direction.clear();
        for (int x = this.getPosition().getX() - 1; x >= 0; x--) {
            direction.add(board.getSquareFromPos(new Position(x, this.getPosition().getY())));
        }
        possibleMoves.add(new ArrayList<>(direction));
        return possibleMoves;
    }

    @Override
    public void specificMove(Square square, Board board, Square prevSquare) {

    }
}
