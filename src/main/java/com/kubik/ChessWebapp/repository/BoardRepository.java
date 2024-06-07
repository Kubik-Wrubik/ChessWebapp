package com.kubik.ChessWebapp.repository;

import com.kubik.ChessWebapp.entity.ChessUser;
import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.statics.BoardStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BoardRepository extends MongoRepository<Board, Long> {
    Optional<Board> findFirstBySecondChessPlayerIsNull();
}
