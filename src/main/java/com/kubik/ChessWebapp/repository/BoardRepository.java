package com.kubik.ChessWebapp.repository;

import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.statics.BoardStatus;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

@Document(collection = "boards")
public interface BoardRepository extends MongoRepository<Board, Long> {
    Optional<Board> findFirstBySecondChessPlayerIsNullAndBoardStatus(BoardStatus boardStatus);
    Optional<Board> findById(String gameId);
}
