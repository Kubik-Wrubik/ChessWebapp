package com.kubik.ChessWebapp.repository;

import com.kubik.ChessWebapp.entity.ChessUser;
import org.springframework.data.repository.CrudRepository;

public interface ChessUserRepository extends CrudRepository<ChessUser, Long> {
    ChessUser findByNickname(String nickname);
    ChessUser findByEmail(String email);
}
