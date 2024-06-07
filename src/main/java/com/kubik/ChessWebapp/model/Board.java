package com.kubik.ChessWebapp.model;

import com.kubik.ChessWebapp.statics.BoardStatus;
import com.kubik.ChessWebapp.statics.Color;
import com.kubik.ChessWebapp.model.pieces.*;
import com.kubik.ChessWebapp.statics.PlayerTurn;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Board {
    private String id;
    private ChessUser firstChessPlayer;
    private ChessUser secondChessPlayer;
    private ChessUser winner;
    private AbstractPiece selectedPiece;
    private PlayerTurn turn;
    private Color textColor = Color.GRAY;
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
        this.turn = PlayerTurn.WHITE_PLAYER;
        this.boardStatus = BoardStatus.NEW;
        this.winner = null;
        this.squares = squaresInit();
        setField();
    }

    private List<Square> squaresInit() {
        List<Square> result = new ArrayList<>();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                result.add(new Square(new Position(x,y)));
            }
        }
        return result;
    }

    public Square getSquareFromPos(Position pos) {
        for (Square square : squares) {
            Position position = square.getPosition();
            if (position.getX() == pos.getX() && position.getY() == pos.getY()) {
                return square;
            }
        }
        return null;
    }

    public AbstractPiece getPieceFromPos(Position pos) {
        Square square = getSquareFromPos(pos);
        return square != null ? square.getOccupyingPiece() : null;
    }

    private void setField() {
        for (int y = 0; y < config.length; y++) {
            for (int x = 0; x < config[y].length; x++) {
                String piece = config[y][x];
                if (!"  ".equals(piece)) {
                    Square square = getSquareFromPos(new Position(x, y));
                    Color color = piece.charAt(0) == 'w' ? Color.WHITE : Color.BLACK;

                    switch (piece.charAt(1)) {
                        case 'R': square.setOccupyingPiece(new Rook(new Position(x, y), color)); break;
                        case 'N': square.setOccupyingPiece(new Knight(new Position(x, y), color)); break;
                        case 'B': square.setOccupyingPiece(new Bishop(new Position(x, y), color)); break;
                        case 'Q': square.setOccupyingPiece(new Queen(new Position(x, y), color)); break;
                        case 'K': square.setOccupyingPiece(new King(new Position(x, y), color)); break;
                        case 'P': square.setOccupyingPiece(new Pawn(new Position(x, y), color)); break;
                    }
                }
            }
        }
    }

    public boolean mouseClick(int x, int y) {
        System.out.println(x + " and " + y);
        Square clickedSquare = getSquareFromPos(new Position(x, y));
        // todo assign selectedPiece to board
//        if (selectedPiece == null) {
//            if (clickedSquare.occupyingPiece != null && clickedSquare.occupyingPiece.color.equals(turn)) {
//                selectedPiece = clickedSquare.occupyingPiece;
//            }
//        } else if (selectedPiece.move(this, clickedSquare)) {
//            clip.start();
//            turn = "white".equals(turn) ? "black" : "white";
//            if (isStalemate(turn)) {
//                return false;
//            }
//        } else if (clickedSquare.occupyingPiece != null && clickedSquare.occupyingPiece.color.equals(turn)) {
//            selectedPiece = clickedSquare.occupyingPiece;
//        }
        return true;
    }

    public boolean isInCheck(Color color, Position[] AttackingKingPositions) {
        boolean result = false;
        Position kingPos = null;

        AbstractPiece changingPiece = null;
        Square oldSquare = null;
        Square newSquare = null;
        AbstractPiece newSquareOldPiece = null;

//        якщо король під ударом
        if (AttackingKingPositions != null) {
            for (Square square : squares) {
//                Якщо клітка збігається із кліткою вибраної фігури - зберігаємо фігуру і її клітку
                if (square.getPosition().equals(AttackingKingPositions[0])) {
                    changingPiece = square.getOccupyingPiece();
                    oldSquare = square;
                    oldSquare.setOccupyingPiece(null);
                    break;
                }
            }
//            Якщо клітка збігається із новою кліткою вибраної фігури - зберігаємо фігуру і її клітку
            for (Square square : squares) {
                if (square.getPosition().equals(AttackingKingPositions[1])) {
                    newSquare = square;
                    newSquareOldPiece = newSquare.getOccupyingPiece();
                    newSquare.setOccupyingPiece(changingPiece);
                    break;
                }
            }
        }
//        Отримуємо масив фігур для аналізу чи король буде під ударом у разі можливого ходу
        List<AbstractPiece> pieces = new ArrayList<>();
        for (Square square : squares) {
            if (square.getOccupyingPiece() != null) {
                pieces.add(square.getOccupyingPiece());
            }
        }
//        якщо вибрана фігура - король то переміщаємо його на нову клітку
        if (changingPiece != null && "K".equals(changingPiece.getName())) {
            kingPos = newSquare.getPosition();
        }
//        Отримуємо позицію нашого короля
        if (kingPos == null) {
            for (AbstractPiece piece : pieces) {
                if ("K".equals(piece.getName()) && color.equals(piece.getColor())) {
                    kingPos = piece.getPosition();
                    break;
                }
            }
        }
//        Перевіряємо чи хоча б одна ворожа фігура атакує короля
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
//        "Повертаємо" фігуру на місце
        if (AttackingKingPositions != null) {
            oldSquare.setOccupyingPiece(changingPiece);
            newSquare.setOccupyingPiece(newSquareOldPiece);
        }
        return result;
    }

    public boolean isInCheckmate(Color color) {
        if (isInCheck(color, null)) {
            List<AbstractPiece> kingPieces = new ArrayList<>();
            for (Square square : squares) {
                if (square.getOccupyingPiece() != null && color.equals(square.getOccupyingPiece().getColor())) {
                    kingPieces.add(square.getOccupyingPiece());
                }
            }
//            Перевірка чи є якісь фігури які можуть захистити короля, якщо немає - повертається True
            for (AbstractPiece piece : kingPieces) {
                if (!piece.getValidMoves(this).isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isStalemate(String color) {
//        Якщо жодна з фігур короля немає можливих ходів включаючи самого короля- повертає True
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
}
