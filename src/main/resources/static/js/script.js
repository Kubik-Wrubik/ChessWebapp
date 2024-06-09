var playerTurnNow = "";
var imgTransparent = "data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
let chessSquareAndIndexesMap  = {
    "00": "18", "10": "28", "20": "38", "30": "48", "40": "58", "50": "68", "60": "78", "70": "88",
    "01": "17", "11": "27", "21": "37", "31": "47", "41": "57", "51": "67", "61": "77", "71": "87",
    "02": "16", "12": "26", "22": "36", "32": "46", "42": "56", "52": "66", "62": "76", "72": "86",
    "03": "15", "13": "25", "23": "35", "33": "45", "43": "55", "53": "65", "63": "75", "73": "85",
    "04": "14", "14": "24", "24": "34", "34": "44", "44": "54", "54": "64", "64": "74", "74": "84",
    "05": "13", "15": "23", "25": "33", "35": "43", "45": "53", "55": "63", "65": "73", "75": "83",
    "06": "12", "16": "22", "26": "32", "36": "42", "46": "52", "56": "62", "66": "72", "76": "82",
    "07": "11", "17": "21", "27": "31", "37": "41", "47": "51", "57": "61", "67": "71", "77": "81"
};

const getKeyByValue = (object, value) => {
    return Object.keys(object).find(key => object[key] === value);
};
function playerTurn(id) {
    if (playerTurnNow != playerType) {
        alert("It's not your turn!")
    } else {
            makeAMove(id);
    }
}

function makeAMove(id) {
    $.ajax({
        url: url + "/game-move",
        type: 'POST',
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "gameId": gameId,
            "squareIndex": id
        }),
        success: function (board) {
            refreshChessBoard(board);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function refreshChessBoard(board) {
    console.log(board);
    let squares = board.squares;
    squares.forEach((square) => {
        var piece = square.occupyingPiece;
        var position = square.position;
        var htmlPosition = chessSquareAndIndexesMap['' + position.x + position.y];
        if(piece != null) {
            var imgPath = "/" + piece.imgPath;
            $("#square_" + htmlPosition + " img").attr("src", imgPath);
        }
        else{
            $("#square_" + htmlPosition + " img").attr("src", imgTransparent);
        }
        if(square.highlight){
            $("#square_" + htmlPosition).addClass("highlighted");
        }
        else{
            $("#square_" + htmlPosition).removeClass("highlighted");
        }
    });

    if (board.gameResult != null) {
        alert(board.gameResult);
    }
    playerTurnNow = board.turn;
    console.log(playerTurnNow);

    if(board.firstChessPlayer != null && board.secondChessPlayer != null){
        $("#first_player").text(board.firstChessPlayer.nickname == null ? "" : board.firstChessPlayer.nickname);
        $("#second_player").text(board.secondChessPlayer.nickname == null ? "" : board.secondChessPlayer.nickname);
    }

    if (playerTurnNow == "WHITE_PLAYER") {
        console.log("WHITE_PLAYER");
        $("#first_player").addClass("playerTurn");
        $("#second_player").removeClass("playerTurn");
    } else {
        console.log("BLACK_PLAYER");
        $("#second_player").addClass("playerTurn")
        $("#first_player").removeClass("playerTurn");
    }

}


$(".light, .dark").click(function () {
    var squareId = $(this).attr('id');
    var s = squareId.substring(7);
    var keyByValue = getKeyByValue(chessSquareAndIndexesMap, s);
    playerTurn(keyByValue);
});




