package com.kubik.ChessWebapp.statics;

public enum PlayerColor {
    WHITE_PLAYER, BLACK_PLAYER;

    public static PlayerColor togglePlayerTurn(PlayerColor currentPlayerColor) {
        switch (currentPlayerColor) {
            case WHITE_PLAYER: return BLACK_PLAYER;
            case BLACK_PLAYER: return WHITE_PLAYER;
            default: throw new RuntimeException("Invalid player turn");
        }
    }
}
