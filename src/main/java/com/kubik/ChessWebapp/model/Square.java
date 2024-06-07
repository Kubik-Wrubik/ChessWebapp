package com.kubik.ChessWebapp.model;

import com.kubik.ChessWebapp.statics.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Square {
    private Position position;
    private String color;
    private Color drawColor;
    private Color selectedSquareColor;
    private AbstractPiece occupyingPiece;
    private boolean isHighlight;

    public Square(Position position) {
        this.position = position;
        this.color = (position.getX() + position.getY()) % 2 == 0 ? "light" : "dark";
        this.drawColor = this.color.equals("light") ? Color.LIGHT : Color.DARK;
        this.selectedSquareColor = this.color.equals("light") ? Color.GREEN_LIGHT : Color.GREEN_DARK;
        this.occupyingPiece = null;
        this.isHighlight = false;
    }
}
