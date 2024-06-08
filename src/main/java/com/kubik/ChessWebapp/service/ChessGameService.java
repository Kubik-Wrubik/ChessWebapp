package com.kubik.ChessWebapp.service;

import com.kubik.ChessWebapp.dto.ChessPlayerDto;
import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.model.ChessUser;
import com.kubik.ChessWebapp.model.Move;
import com.kubik.ChessWebapp.repository.BoardRepository;
import com.kubik.ChessWebapp.statics.BoardStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChessGameService {
    private final BoardRepository boardRepository;
    public Board createGame(ChessUser user) {
        Board board = new Board(); // todo make it bean
        board.setFirstChessPlayer(user);
        boardRepository.save(board);
        return board;
    }

    public Board connectToRandom(ChessUser user) {
        System.out.println("connect random service");
        Optional<Board> optionalGame = boardRepository.findFirstBySecondChessPlayerIsNull();
        optionalGame.orElseThrow(() -> new RuntimeException("All games are occupied"));
        Board board = optionalGame.get();
        System.out.println(board);
        board.setSecondChessPlayer(user);
        board.setBoardStatus(BoardStatus.IN_PROGRESS);
        boardRepository.save(board);
        return board;
    }

    public Board move(Move move) {
        Optional<Board> optionalGame = boardRepository.findById(move.getGameId());

        optionalGame.orElseThrow(() -> new RuntimeException("Game with provided id doesn't exist"));
        Board board = optionalGame.get();

        String squareIndex = move.getSquareIndex();
        board.mouseClick(Character.getNumericValue(squareIndex.charAt(0)), Character.getNumericValue(squareIndex.charAt(1)));

//        Board boardAfterMove = sowService.sow(board,sow.getPitIndex());
        boardRepository.save(board);

//        return boardAfterMove;
        return board;
    }
}
