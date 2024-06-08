package com.kubik.ChessWebapp.model.pieces;

import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.model.AbstractPiece;
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
        List<List<Square>> result = new ArrayList<>();
        List<Position> temp = new ArrayList<>();

        //todo position should be relative
        if (this.getColor() == PlayerColor.WHITE_PLAYER) {
            temp.add(new Position(0, -1));
            doubleMove = false;
            if (!this.isHasMoved()) {
                temp.add(new Position(0, -2));
                doubleMove = true;
            }
        } else if (this.getColor() == PlayerColor.BLACK_PLAYER) {
            temp.add(new Position(0, 1));
            doubleMove = false;
            if (!this.isHasMoved()) {
                temp.add(new Position(0, 2));
                doubleMove = true;
            }
        }
//        System.out.println("board.getSquareFromPos = " + board.getSquareFromPos(new Position(0, 2)));
        List<Square> direction = new ArrayList<>();
        for (Position move : temp) {
            int newY = this.getPosition().getY() + move.getY();
            if (newY >= 0 && newY < 8) {
                direction.add(board.getSquareFromPos(new Position(this.getPosition().getX(), newY)));
            }
        }
        result.add(new ArrayList<>(direction));
        return result;
    }

    @Override
    public List<Square> getMoves(Board board) {
        // todo pawn can jump over his color figures
        // todo pawn cant make enPassant
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
