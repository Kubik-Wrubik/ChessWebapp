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
//        звільняємо клітки від підсвічування можливих ходів фігури
        for (Square s : board.getSquares()) {
            s.setHighlight(false);
        }
//        Задаємо нову позицію для фігури і прибираємо фігуру із старої позиції
        if (getValidMoves(board).contains(square) || force) {
            Square prevSquare = board.getSquareFromPos(position); //todo to conclude for what this line
            this.setPosition(square.getPosition());
            square.setOccupyingPiece(this);
            prevSquare.setOccupyingPiece(null);
            this.hasMoved = true;
            board.setSelectedPiece(null);
            specificMove(square, board, prevSquare);
            return true;
        }
//        Якщо вибрано некоректна клітка для ходу - повертаємо false
        else {
            board.setSelectedPiece(null);
            return false;
        }
    }

    public List<Square> getMoves(Board board) {
        List<Square> output = new ArrayList<>();
//        Користуючись правилами за якими ходить певна фігура, отримуємо можливі клітки для ходу
        List<Square> possibleMoves = getPossibleMoves(board);
        for (Square square : possibleMoves) {
//                Якщо фігура впирається в фігуру - провіряємо чи це ворожа фігура і прериваємо можливі ходи в цьому напрямку
            if (square.getOccupyingPiece() != null) {
                if (square.getOccupyingPiece().getColor() != this.color) {
                    output.add(square);
                }
                break;
            }
//                Якщо клітка порожня - додаємо її до можливих ходів
            else {
                output.add(square);
            }
        }
        return output;
    }


    public List<Square> getValidMoves(Board board) {
        List<Square> result = new ArrayList<>();
        for (Square square : getMoves(board)) {
//            Провіряємо чи можливий хід захистить короля або принаймні не поставить його під удар.
//            Якщо так - добавимо його в можливі ходи
            if (!board.isInCheck(this.color, new Position[]{this.getPosition(), square.getPosition()})) {
                result.add(square);
            }
        }
        return result;
    }

    public abstract List<Square> getPossibleMoves(Board board);

    public abstract void specificMove(Square square, Board board, Square prevSquare);
}
