package com.kubik.ChessWebapp.service;

import com.kubik.ChessWebapp.dto.ChessPlayerDto;
import com.kubik.ChessWebapp.dto.ChessUserDto;
import com.kubik.ChessWebapp.entity.ChessUser;
import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.repository.BoardRepository;
import com.kubik.ChessWebapp.statics.BoardStatus;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChessGameService {
    private final BoardRepository boardRepository;
    public Board createGame(ChessPlayerDto user) {
        Board board = new Board(user); // todo make it bean
        board.setFirstChessPlayer(user);
        boardRepository.save(board);
        return board;
    }

    public Board connectToRandom(ChessPlayerDto user) {
        System.out.println("connect random service");
        Optional<Board> optionalGame = boardRepository.findFirstBySecondChessPlayerIsNull();
        optionalGame.orElseThrow(() -> new RuntimeException("All games are occupied"));
        Board board = optionalGame.get();
        board.setSecondChessPlayer(user);
        board.setBoardStatus(BoardStatus.IN_PROGRESS);
        boardRepository.save(board);
        return board;
    }
}
