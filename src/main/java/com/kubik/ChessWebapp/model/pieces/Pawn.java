package com.kubik.ChessWebapp.model.pieces;

import com.kubik.ChessWebapp.model.AbstractPiece;
import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.model.Position;
import com.kubik.ChessWebapp.model.Square;
import com.kubik.ChessWebapp.statics.PlayerColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pawn extends AbstractPiece {
    boolean doubleMove;

    public Pawn(Position position, PlayerColor color) {
        super(position, color);
        this.setImgPath("images/" + color.name().toLowerCase().charAt(0) + "_pawn.png");
        this.setName("P");
        this.doubleMove = false;
    }

    @Override
    public List<List<Square>> getPossibleMoves(Board board) {
        List<List<Square>> possibleMoves = new ArrayList<>();
        List<Position> direction = new ArrayList<>();
        if (this.getColor() == PlayerColor.WHITE_PLAYER) {
            direction.add(new Position(0, -1));
            doubleMove = false;
            if (!this.isHasMoved()) {
                direction.add(new Position(0, -2));
                doubleMove = true;
            }
        } else if (this.getColor() == PlayerColor.BLACK_PLAYER) {
            direction.add(new Position(0, 1));
            doubleMove = false;
            if (!this.isHasMoved()) {
                direction.add(new Position(0, 2));
                doubleMove = true;
            }
        }
        for (Position move : direction) {
            int newY = this.getPosition().getY() + move.getY();
            if (newY >= 0 && newY < 8) {
                possibleMoves.add(Collections.singletonList(
                        board.getSquareFromPos(new Position(this.getPosition().getX(), newY))));
            }
        }
        return possibleMoves;
    }

    @Override
    public List<Square> getMoves(Board board) {
        List<Square> result = new ArrayList<>();
        List<List<Square>> possibleMoves = getPossibleMoves(board);
        for (List<Square> direction : possibleMoves) {
            for (Square square : direction) {
                if (square.getOccupyingPiece() != null) {
                    break;
                } else {
                    result.add(square);
                }
            }
        }
        if (this.getColor() == PlayerColor.WHITE_PLAYER) {
            if (this.getPosition().getX() + 1 < 8 && this.getPosition().getY() - 1 >= 0) {
                Square square = board.getSquareFromPos(
                        new Position(this.getPosition().getX() + 1, this.getPosition().getY() - 1)
                );
                if (square.getOccupyingPiece() != null && square.getOccupyingPiece().getColor() != this.getColor()) {
                    result.add(square);
                }
            }
            if (this.getPosition().getX() - 1 >= 0 && this.getPosition().getY() - 1 >= 0) {
                Square square = board.getSquareFromPos(new Position(this.getPosition().getX() - 1, this.getPosition().getY() - 1));
                if (square.getOccupyingPiece() != null && square.getOccupyingPiece().getColor() != this.getColor()) {
                    result.add(square);
                }
            }
        } else if (this.getColor() == PlayerColor.BLACK_PLAYER) {
            if (this.getPosition().getX() + 1 < 8 && this.getPosition().getY() + 1 < 8) {
                Square square = board.getSquareFromPos(new Position(this.getPosition().getX() + 1, this.getPosition().getY() + 1));
                if (square.getOccupyingPiece() != null && square.getOccupyingPiece().getColor() != this.getColor()) {
                    result.add(square);
                }
            }
            if (this.getPosition().getX() - 1 >= 0 && this.getPosition().getY() + 1 < 8) {
                Square square = board.getSquareFromPos(new Position(this.getPosition().getX() - 1, this.getPosition().getY() + 1));
                if (square.getOccupyingPiece() != null && square.getOccupyingPiece().getColor() != this.getColor()) {
                    result.add(square);
                }
            }
        }
        return result;
    }

    @Override
    public void specificMove(Square square, Board board, Square prevSquare) {
        if (this.getPosition().getY() == 0 || this.getPosition().getY() == 7) {
            square.setOccupyingPiece(new Queen(new Position(this.getPosition().getX(), this.getPosition().getY()), this.getColor()));
        }
    }
}
