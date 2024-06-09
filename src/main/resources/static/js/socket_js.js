const url = 'http://localhost:8080'; //change for local game
let stompClient;
let gameId;
let playerType;

function connectToSocket(gameId) {
    let socket = new SockJS(url + "/move");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to the frame: " + frame);
        stompClient.subscribe("/topic/game-progress/" + gameId, function (response) {
            let board = JSON.parse(response.body);
            console.log(board);
            refreshChessBoard(board);
        })
    })
}

 function create_game(name) {
    $.ajax({
        url: url + "/game-create-json",
        type: 'POST',
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "nickname": name
        }),
        success: function (board) {
            gameId = board.id;
            playerType = "WHITE_PLAYER";
            createBoardHtml(gameId, board);

            connectToSocket(gameId);
            alert("Your created a game. Game id is: " + board.id);
        },
        error: function (error) {
            console.log(error);
        }
    });

}


function connectToRandom(name) {
    $.ajax({
        url: url + "/connect-random-json",
        type: 'POST',
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "nickname": name
        }),
        success: function (board) {
            gameId = board.id;
            playerType = "BLACK_PLAYER";
            createBoardHtml(gameId, board);
            connectToSocket(gameId);

            alert("Congrats you're playing with: " + board.firstChessPlayer.nickname);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function connectToSpecific(name) {
    gameId = document.getElementById("game_id").value;
    if (gameId === '' || gameId == null) {
        alert("Incorrect game id");
    }
    $.ajax({
        url: url + "/connect-json",
        type: 'POST',
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "player":{
                "nickname":name
            },
            "gameId": gameId
        }),
        success: function (board) {
            playerType = "BLACK_PLAYER";
            createBoardHtml(gameId, board);
            connectToSocket(gameId);

            alert("Congrats you're playing with: " + board.firstChessPlayer.nickname);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function createBoardHtml(gameId, board){
    $.ajax({
        url: url + "/board-create",
        type: 'POST',
        dataType: "html",
        contentType: "application/json",
        data: gameId,
        success: function (fragment) {
            $("#boardFrame").html(fragment);
            refreshChessBoard(board);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function connectToSpecificGame() {
    let name = document.getElementById("name").value;
    if (name == null || name === '') {
        alert("Please enter name");
    } else {
        gameId = document.getElementById("game_id").value;
        if (gameId == null || gameId === '') {
            alert("Please enter game id");
        }
        $.ajax({
            url: url + "/game/connect",
            type: 'POST',
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                "player": {
                    "name": name
                },
                "gameId": gameId
            }),
            success: function (data) {
                gameId = data.id;
                playerType = "SECOND_PLAYER";
                refreshGameBoard(data);
                connectToSocket(gameId);
                alert("Congrats you're playing with: " + data.firstPlayer.name);
            },
            error: function (error) {
                console.log(error);
            }
        })
    }
}
