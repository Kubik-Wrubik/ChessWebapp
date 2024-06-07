package com.kubik.ChessWebapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Square {
    private Position position;
    private String color;
//    private Square selectedSquare; todo add this latter
    private AbstractPiece occupyingPiece;
    private boolean isHighlight;

    public Square(Position position) {
        this.position = position;
        this.color = (position.getX() + position.getY()) % 2 == 0 ? "light" : "dark";
        this.occupyingPiece = null;
        this.isHighlight = false;
    }
}
