package com.kubik.ChessWebapp.web;

import com.kubik.ChessWebapp.dto.ChessPlayerDto;
import com.kubik.ChessWebapp.dto.GameConnect;
import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.entity.ChessUser;
import com.kubik.ChessWebapp.model.Move;
import com.kubik.ChessWebapp.service.ChessGameService;
import com.kubik.ChessWebapp.service.ChessUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class GameController {
    private final ChessGameService chessGameService;
    private final ChessUserService chessUserService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/board-create")
    public String createBoard(@RequestBody String gameId, Model model) {
        model.addAttribute("board", chessGameService.getGame(gameId));
        return "fragments/game-page :: game-board";
    }

    @PostMapping("/game-create-json")
    public ResponseEntity<Board> createGame(@RequestBody ChessPlayerDto chessPlayer) {
        ChessUser userByNickname = chessUserService.getUserByNickname(chessPlayer.getNickname());
        return ResponseEntity.ok(chessGameService.createGame(userByNickname));
    }

    @PostMapping("/connect-random-json")
    public ResponseEntity<Board> connectRandom(@RequestBody ChessPlayerDto chessPlayer) throws RuntimeException {
        ChessUser userByNickname = chessUserService.getUserByNickname(chessPlayer.getNickname());
        return ResponseEntity.ok(chessGameService.connectToRandom(userByNickname));
    }

    @PostMapping("/connect-json")
    public ResponseEntity<Board> connectSpecific(@RequestBody GameConnect connect) throws RuntimeException {
        ChessUser userByNickname = chessUserService.getUserByNickname(connect.getPlayer().getNickname());
        return ResponseEntity.ok(chessGameService.connectToSpecific(userByNickname, connect.getGameId()));
    }


    @PostMapping("/game-move")
    public ResponseEntity<Board> move(@RequestBody Move move) throws RuntimeException {
        Board board = chessGameService.move(move);
        simpMessagingTemplate.convertAndSend("/topic/game-progress/" + board.getId(), board);
        return ResponseEntity.ok(board);
    }
}
