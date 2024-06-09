package com.kubik.ChessWebapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Square {
    private Position position;
    private String color;
    private AbstractPiece occupyingPiece;
    private boolean isHighlight;

    public Square(Position position) {
        this.position = position;
        this.color = (position.getX() + position.getY()) % 2 == 0 ? "light" : "dark";
        this.occupyingPiece = null;
        this.isHighlight = false;
    }
}
