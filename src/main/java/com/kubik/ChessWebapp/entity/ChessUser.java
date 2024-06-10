package com.kubik.ChessWebapp.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "chessUsers")
public class ChessUser {
    @Id
    private String id;
    private String email;
    private String nickname;
    private String password;

}
