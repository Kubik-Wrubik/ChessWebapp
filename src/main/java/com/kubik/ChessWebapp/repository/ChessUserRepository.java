package com.kubik.ChessWebapp.repository;

import com.kubik.ChessWebapp.entity.ChessUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChessUserRepository extends MongoRepository<ChessUser, Long> {
    ChessUser findByNickname(String nickname);
}
