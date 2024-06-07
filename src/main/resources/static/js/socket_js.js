const url = 'http://localhost:8080';
let stompClient;
let gameId;
let playerType;

function connectToSocket(gameId) {
    console.log(gameId);
    console.log("connecting to the game");
    let socket = new SockJS(url + "/move");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to the frame: " + frame);
        stompClient.subscribe("/topic/game-progress/" + gameId, function (response) {
            let data = JSON.parse(response.body);
            console.log(data);
            refreshChessBoard(data);
        })
    })
}

function create_game(name) {
    console.log("hooray create! " + name);
    $.ajax({
        url: url + "/game-create",
        type: 'POST',
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "nickname": name
        }),
        success: function (board) {
            gameId = board.id;
            playerType = 'FIRST_PLAYER';

            refreshChessBoard(board);
            connectToSocket(gameId);
            alert("Your created a game. Game id is: " + board.id);
        },
        error: function (error) {
            console.log(error);
        }
    })
}


function connectToRandom(name) {
    console.log("hooray random! " + name);
    $.ajax({
        url: url + "/connect-random",
        type: 'POST',
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "name": name
        }),
        success: function (board) {
            gameId = board.id;
            playerType = "SECOND_PLAYER";
            refreshChessBoard(board);
            connectToSocket(gameId);
            alert("Congrats you're playing with: " + board.firstPlayer.name);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

// function connectToSpecificGame() {
//     let name = document.getElementById("name").value;
//     if (name == null || name === '') {
//         alert("Please enter name");
//     } else {
//         gameId = document.getElementById("game_id").value;
//         if (gameId == null || gameId === '') {
//             alert("Please enter game id");
//         }
//         $.ajax({
//             url: url + "/game/connect",
//             type: 'POST',
//             dataType: "json",
//             contentType: "application/json",
//             data: JSON.stringify({
//                 "player": {
//                     "name": name
//                 },
//                 "gameId": gameId
//             }),
//             success: function (data) {
//                 gameId = data.id;
//                 playerType = "SECOND_PLAYER";
//                 refreshGameBoard(data);
//                 connectToSocket(gameId);
//                 alert("Congrats you're playing with: " + data.firstPlayer.name);
//             },
//             error: function (error) {
//                 console.log(error);
//             }
//         })
//     }
// }