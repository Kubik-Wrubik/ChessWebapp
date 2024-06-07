package com.kubik.ChessWebapp.statics;

public enum PlayerTurn {
    WHITE_PLAYER, BLACK_PLAYER;

    public static PlayerTurn togglePlayerTurn(PlayerTurn currentPlayerTurn) {
        switch (currentPlayerTurn) {
            case WHITE_PLAYER: return BLACK_PLAYER;
            case BLACK_PLAYER: return WHITE_PLAYER;
            default: throw new RuntimeException("Invalid player turn");
        }
    }
}
