package com.kubik.ChessWebapp.model.pieces;

import com.kubik.ChessWebapp.model.AbstractPiece;
import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.model.Position;
import com.kubik.ChessWebapp.model.Square;
import com.kubik.ChessWebapp.statics.PlayerColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class King extends AbstractPiece {

    public King(Position position, PlayerColor color) {
        super(position, color);
        this.setImgPath("images/" + color.toString().toLowerCase().charAt(0) + "_king.png");
        this.setName("K");
    }

    @Override
    public List<List<Square>> getPossibleMoves(Board board) {
        List<List<Square>> possibleMoves = new ArrayList<>();
        int[][] moves = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
        for (int[] move : moves) {
            int newX = this.getPosition().getX() + move[0];
            int newY = this.getPosition().getY() + move[1];
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                possibleMoves.add(Collections.singletonList(board.getSquareFromPos(new Position(newX, newY))));
            }
        }
        return possibleMoves;
    }

    public boolean canCastleLeft(Board board) {
        if (!this.isHasMoved()) {
            if (this.getColor() == PlayerColor.WHITE_PLAYER) {
                AbstractPiece leftRook = board.getSquareFromPos(new Position(0, 7)).getOccupyingPiece();
                if (leftRook != null && !leftRook.isHasMoved()) {
                    boolean pathClear = true;
                    for (int i = 1; i <= 3; i++) {
                        if (board.getSquareFromPos(new Position(i, 7)).getOccupyingPiece() != null) {
                            pathClear = false;
                            break;
                        }
                    }
                    if (pathClear) {
                        List<AbstractPiece> pieces = new ArrayList<>();
                        for (Square square : board.getSquares()) {
                            if (square.getOccupyingPiece() != null && square.getOccupyingPiece().getColor() == PlayerColor.BLACK_PLAYER) {
                                pieces.add(square.getOccupyingPiece());
                            }
                        }
                        for (AbstractPiece piece : pieces) {
                            List<Square> squares = piece.getMoves(board);
                            for (Square square : squares) {
                                for (int i = 1; i <= 3; i++) {
                                    if (square.getPosition().equals(new Position(i, 7))) {
                                        return false;
                                    }
                                }
                            }
                        }
                        return true;
                    }
                }
            } else if (this.getColor() == PlayerColor.BLACK_PLAYER) {
                AbstractPiece leftRook = board.getSquareFromPos(new Position(0, 0)).getOccupyingPiece();
                if (leftRook != null && !leftRook.isHasMoved()) {
                    boolean pathClear = true;
                    for (int i = 1; i <= 3; i++) {
                        if (board.getSquareFromPos(new Position(i, 0)).getOccupyingPiece() != null) {
                            pathClear = false;
                            break;
                        }
                    }
                    if (pathClear) {
                        List<AbstractPiece> pieces = new ArrayList<>();
                        for (Square square : board.getSquares()) {
                            if (square.getOccupyingPiece() != null && square.getOccupyingPiece().getColor() == PlayerColor.WHITE_PLAYER) {
                                pieces.add(square.getOccupyingPiece());
                            }
                        }
                        for (AbstractPiece piece : pieces) {
                            List<Square> squares = piece.getMoves(board);
                            for (Square square : squares) {
                                for (int i = 1; i <= 3; i++) {
                                    if (square.getPosition().equals(new Position(i, 0))) {
                                        return false;
                                    }
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canCastleRight(Board board) {
        if (!this.isHasMoved()) {
            if (this.getColor() == PlayerColor.WHITE_PLAYER) {
                AbstractPiece rightRook = board.getSquareFromPos(new Position(7, 7)).getOccupyingPiece();
                if (rightRook != null && !rightRook.isHasMoved()) {
                    boolean pathClear = true;
                    for (int i = 5; i <= 6; i++) {
                        if (board.getSquareFromPos(new Position(i, 7)).getOccupyingPiece() != null) {
                            pathClear = false;
                            break;
                        }
                    }
                    if (pathClear) {
                        List<AbstractPiece> pieces = new ArrayList<>();
                        for (Square square : board.getSquares()) {
                            if (square.getOccupyingPiece() != null && square.getOccupyingPiece().getColor() == PlayerColor.BLACK_PLAYER) {
                                pieces.add(square.getOccupyingPiece());
                            }
                        }
                        for (AbstractPiece piece : pieces) {
                            List<Square> squares = piece.getMoves(board);
                            for (Square square : squares) {
                                for (int i = 5; i <= 6; i++) {
                                    if (square.getPosition().equals(new Position(i, 7))) {
                                        return false;
                                    }
                                }
                            }
                        }
                        return true;
                    }
                }
            } else if (this.getColor() == PlayerColor.BLACK_PLAYER) {
                AbstractPiece rightRook = board.getSquareFromPos(new Position(7, 0)).getOccupyingPiece();
                if (rightRook != null && !rightRook.isHasMoved()) {
                    boolean pathClear = true;
                    for (int i = 5; i <= 6; i++) {
                        if (board.getSquareFromPos(new Position(i, 0)).getOccupyingPiece() != null) {
                            pathClear = false;
                            break;
                        }
                    }
                    if (pathClear) {
                        List<AbstractPiece> pieces = new ArrayList<>();
                        for (Square square : board.getSquares()) {
                            if (square.getOccupyingPiece() != null && square.getOccupyingPiece().getColor() == PlayerColor.WHITE_PLAYER) {
                                pieces.add(square.getOccupyingPiece());
                            }
                        }
                        for (AbstractPiece piece : pieces) {
                            List<Square> squares = piece.getMoves(board);
                            for (Square square : squares) {
                                for (int i = 5; i <= 6; i++) {
                                    if (square.getPosition().equals(new Position(i, 0))) {
                                        return false;
                                    }
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<Square> getValidMoves(Board board) {
        List<Square> output = new ArrayList<>();
        List<Square> moves = this.getMoves(board);
        for (Square square : moves) {
            if (!board.isInCheck(this.getColor(), new Position[]{this.getPosition(), square.getPosition()})) {
                output.add(square);
            }
        }
        if (this.canCastleLeft(board)) {
            output.add(board.getSquareFromPos(new Position(this.getPosition().getX() - 2, this.getPosition().getY())));
        }
        if (this.canCastleRight(board)) {
            output.add(board.getSquareFromPos(new Position(this.getPosition().getX() + 2, this.getPosition().getY())));
        }
        return output;
    }

    @Override
    public void specificMove(Square square, Board board, Square prevSquare) {
        if (this.getName().equals("K")) {
            if (prevSquare.getPosition().getX() - this.getPosition().getX() == 2) {
                AbstractPiece rook = board.getPieceFromPos(new Position(0, this.getPosition().getY()));
                rook.move(board, board.getSquareFromPos(new Position(3, this.getPosition().getY())), true);
            } else if (prevSquare.getPosition().getX() - this.getPosition().getX() == -2) {
                AbstractPiece rook = board.getPieceFromPos(new Position(7, this.getPosition().getY()));
                rook.move(board, board.getSquareFromPos(new Position(5, this.getPosition().getY())), true);
            }
        }
    }
}
