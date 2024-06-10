package com.kubik.ChessWebapp.model;


import com.kubik.ChessWebapp.statics.PlayerColor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class AbstractPiece {
    private Position position;
    private PlayerColor color;
    private boolean hasMoved = false;
    private boolean attackingKing = false;
    private String name = null;
    private boolean enPassant = false;
    private String imgPath;

    public AbstractPiece(Position position, PlayerColor color) {
        this.position = position;
        this.color = color;
    }

    public boolean move(Board board, Square square, boolean force) {
        for (Square s : board.getSquares()) {
            s.setHighlight(false);
        }
        if (getValidMoves(board).contains(square) || force) {
            Square prevSquare = board.getSquareFromPos(position); //todo to conclude for what this line
            this.setPosition(square.getPosition());
            square.setOccupyingPiece(this);
            prevSquare.setOccupyingPiece(null);
            this.hasMoved = true;
            board.setSelectedPiece(null);
            specificMove(square, board, prevSquare);
            return true;
        } else {
            board.setSelectedPiece(null);
            return false;
        }
    }

    public List<Square> getMoves(Board board) {
        List<Square> output = new ArrayList<>();
        List<List<Square>> possibleMoves = getPossibleMoves(board);
        for (List<Square> direction : possibleMoves) {
            for (Square square : direction) {
                if (square.getOccupyingPiece() != null) {
                    if (!square.getOccupyingPiece().getColor().equals(this.color)) {
                        output.add(square);
                        break;
                    } else {
                        break;
                    }
                } else {
                    output.add(square);
                }
            }
        }
        return output;
    }

    public List<Square> getValidMoves(Board board) {
        List<Square> result = new ArrayList<>();
        for (Square square : getMoves(board)) {
            if (!board.isInCheck(this.color, new Position[]{this.getPosition(), square.getPosition()})) {
                result.add(square);
            }
        }
        return result;
    }

    public abstract List<List<Square>> getPossibleMoves(Board board);

    public abstract void specificMove(Square square, Board board, Square prevSquare);
}
