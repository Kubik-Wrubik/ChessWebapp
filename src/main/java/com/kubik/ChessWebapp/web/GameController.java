package com.kubik.ChessWebapp.web;

import com.kubik.ChessWebapp.dto.ChessPlayerDto;
import com.kubik.ChessWebapp.entity.ChessUser;
import com.kubik.ChessWebapp.model.Board;
import com.kubik.ChessWebapp.service.ChessGameService;
import com.kubik.ChessWebapp.service.ChessUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class GameController {
    private final ChessGameService chessGameService;
    private final ChessUserService chessUserService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/game-create")
    public ResponseEntity<Board> create(@RequestBody ChessPlayerDto chessPlayer) {
        System.out.println(chessPlayer);
//        ChessUser userByNickname = chessUserService.getUserByNickname(chessPlayer.getNickname());
        System.out.println(chessPlayer.getNickname());
        return ResponseEntity.ok(chessGameService.createGame(chessPlayer));
    }
    @PostMapping("/connect-random")
    public ResponseEntity<Board> connectRandom(@RequestBody ChessPlayerDto chessPlayer) throws RuntimeException{
        System.out.println("two players plays, Hooray");
        return ResponseEntity.ok(chessGameService.connectToRandom(chessPlayer));
    }

//    @PostMapping("/connect")
//    public ResponseEntity<Game> connect(@RequestBody ConnectRequest request) throws GameException {
//        return ResponseEntity.ok(chessGameService.connectToGame(request.getPlayer(), request.getGameId()));
//    }



//    @PostMapping("/sow")
//    public ResponseEntity<Game> sow(@RequestBody Sow sow) throws  GameException {
//        Game game = chessGameService.sow(sow);
//
//        simpMessagingTemplate.convertAndSend("/topic/game-progress/" + game.getId(), game);
//        return ResponseEntity.ok(game);
//    }
}
