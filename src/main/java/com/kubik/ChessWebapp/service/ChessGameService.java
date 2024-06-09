package com.kubik.ChessWebapp.service;

import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.model.ChessUser;
import com.kubik.ChessWebapp.model.Move;
import com.kubik.ChessWebapp.repository.BoardRepository;
import com.kubik.ChessWebapp.statics.BoardStatus;
import com.kubik.ChessWebapp.statics.GameResult;
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
        Optional<Board> optionalGame = boardRepository.findFirstBySecondChessPlayerIsNullAndBoardStatus(BoardStatus.NEW);
        optionalGame.orElseThrow(() -> new RuntimeException("All games are occupied"));
        Board board = optionalGame.get();
        board.setSecondChessPlayer(user);
        board.setBoardStatus(BoardStatus.IN_PROGRESS);
        boardRepository.save(board);
        return board;
    }

    public Board connectToSpecific(ChessUser user, String gameId) {
        Optional<Board> optionalGame = boardRepository.findById(gameId);
        optionalGame.orElseThrow(() -> new RuntimeException("There is no such game"));
        Board board = optionalGame.get();
        board.setSecondChessPlayer(user);
        board.setBoardStatus(BoardStatus.IN_PROGRESS);
        boardRepository.save(board);
        return board;
    }

    public Board getGame(String gameId){
        Optional<Board> optionalGame = boardRepository.findById(gameId);
        optionalGame.orElseThrow(() -> new RuntimeException("All games are occupied"));
        return optionalGame.get();
    }

    public Board move(Move move) {
        Optional<Board> optionalGame = boardRepository.findById(move.getGameId());

        optionalGame.orElseThrow(() -> new RuntimeException("Game with provided id doesn't exist"));
        Board board = optionalGame.get();

        String squareIndex = move.getSquareIndex();
        board.mouseClick(Character.getNumericValue(squareIndex.charAt(0)), Character.getNumericValue(squareIndex.charAt(1)));
        GameResult gameResult = board.checkGameOver();
        if(gameResult != null){
            board.setGameResult(gameResult);
            board.setBoardStatus(BoardStatus.FINISHED);
            System.out.println("finish");
        }
        boardRepository.save(board);
        return board;
    }


}
