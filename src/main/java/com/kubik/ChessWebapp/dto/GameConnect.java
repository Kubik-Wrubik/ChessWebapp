package com.kubik.ChessWebapp.dto;

import lombok.Data;

@Data
public class GameConnect {
    ChessPlayerDto player;
    String gameId;
}
