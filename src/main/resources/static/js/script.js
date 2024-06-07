var playerTurnNow = "";
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
function playerTurn(id) {
    if (playerTurnNow != playerType) {
        alert("It's not your turn!")
    } else {
        if ((playerType == "FIRST_PLAYER" && id > 7) || (playerType == "SECOND_PLAYER" && id < 7)) {
            alert("choose from your pits!")
            return;
        }
        var stones = $("#" + id).text();
        if (stones != "0") {
            makeAMove(id);
        }

    }
}

function makeAMove(id) {
    $.ajax({
        url: url + "/game/sow",
        type: 'POST',
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "gameId": gameId,
            "pitIndex": id
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
    let squares = board.squares;
    console.log(squares);
    squares.forEach((square) => {
        var piece = square.occupyingPiece;
        var position = square.position;
        var htmlPosition = chessSquareAndIndexesMap['' + position.x + position.y];
        if(piece != null) {
            var imgPath = "/" + piece.imgPath;
            console.log(piece);
            console.log(htmlPosition);
            console.log(imgPath);
            $("#square_" + htmlPosition + " img").attr("src", imgPath);
        }
        //add null
    });

    if (board.winner != null) {
        alert("Winner is " + board.winner.nickname);
    }
    playerTurnNow = board.playerTurn;

    // $("#firstPlayerName").text(board.firstPlayer.name + "'s larger pit");
    // $("#secondPlayerName").text(board.secondPlayer== null ? "second player" : board.secondPlayer.name + "'s larger pit");

    // if (playerType == "FIRST_PLAYER") {
    //     $("#firstPlayerName").background="#1472a9";
    //     $("#secondPlayerName").background="#333";
    //     $("#opponentLogin").text(board.secondPlayer!= null ? board.secondPlayer.name : "");
    //
    // } else {
    //     $("#secondPlayerName").background ="#1472a9";
    //     $("#firstPlayerName").background="#333";
    //     $("#opponentLogin").text(board.firstPlayer.name);
    //
    // }

}


// $(".pitValue").click(function () {
//     var pitId = $(this).attr('id');
//     playerTurn(parseInt(pitId.substring(4)) + 1);
// });




