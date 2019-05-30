$(function () {
    'use strict';
    // HEADER: Adjust Slider Height
    var windowHeigh = $(window).height();
    var upperH = $('.upper-bar').innerHeight();
    var navH = $('.navbar').innerHeight();
    $('.slider, .carousel-item').height(windowHeigh - (upperH + navH));
});


// ++++++++++++++++++++++++++ Start Change the Color if the link is clicked +++++++++++++++++++++++++++++++++++++++++
var header = document.getElementById("myDIV");
var btns = header.getElementsByClassName("nav-item");
for (var i = 0; i < btns.length; i++) {
    btns[i].addEventListener("click", function () {
        var current = document.getElementsByClassName("active");
        current[0].className = current[0].className.replace(" active", "");
        this.className += " active";
    });
}
// ++++++++++++++++++++++++++ End Change the Color if the link is clicked +++++++++++++++++++++++++++++++++++++++++++

// ++++++++++++++++++++++++++++++ Start scroll from navbar to the determined element ++++++++++++++++++++++++++++++++
$('.nav-item .einfuer').click(function () {

    $('html, body').animate({

        scrollTop: $('.einf').offset().top

    }, 1000);
});

$('.nav-item .anwendun').click(function () {

    $('html, body').animate({

        scrollTop: $('.anwend').offset().top

    }, 1000);
});

$('.nav-item .exakte').click(function () {

    $('html, body').animate({

        scrollTop: $('.exakt').offset().top

    }, 1000);
});

$('.nav-item .heuristische').click(function () {

    $('html, body').animate({

        scrollTop: $('.heuristi').offset().top

    }, 1000);
});

$('.nav-item .implementier').click(function () {

    $('html, body').animate({

        scrollTop: $('.implemen').offset().top

    }, 1000);
});

$('.nav-item .observat').click(function () {

    $('html, body').animate({

        scrollTop: $('.observ').offset().top

    }, 1000);
});
// ++++++++++++++++++++++++++++++ Start Select Algorithmen +++++++++++++++++++++++++++++++++
var expanded = false;

function showCheckboxes() {
    var checkboxes = document.getElementById("checkboxes");
    if (!expanded) {
        checkboxes.style.display = "block";
        expanded = true;
    } else {
        checkboxes.style.display = "none";
        expanded = false;
    }
}

/*
* in this methods send a list of Algorithms to Java Server in order to implement this selected Algorithms and send it
* back using AJAX.
 */
function submitSelectedAlgorithms() {
    /* declare an checkbox array */
    var chkArray = [];

    /* look for all checkboes that have a class 'chk' attached to it and check if it was checked */
    $(".chk:checked").each(function () {
        chkArray.push($(this).val());
    });

    /* we join the array separated by the comma */
    var selected = JSON.stringify(chkArray);
    console.log(selected);

    if (chkArray.length > 0) {
        let x = "";
        $.ajax({
            contentType: "application/json",
            type: "POST",
            data: selected,
            url: "/check",
            success: function (data) {
                console.log('done done done');

                if (isJson(data)) {
                    let myObj = JSON.parse(data);
                    let y = "";
                    x = "<div>" + "<hr>";
                    x += "<h4>" + "Das Ergebnis der Implementierung der Algorithmen:" + "</h4>";
                    for (var i = 0; i < myObj.algorithms.length; i++) {
                        y += "<p>" + "Es wird für " + myObj.algorithms[i].algorithm + " " + myObj.algorithms[i].numberColors +
                            " Farben gebraucht." + "</p>"
                    }
                    x += "<p>" + y + "</p>";
                    x += "<b>" + "<a " + " id = " + "showJsonText " + " onclick=" + "showAndHide()" + " >" + "Das ganze Ergebnis in JSON Format anzeigen"
                        + "</a>" + "</b>";
                    x += "<pre>" + JSON.stringify(myObj, null, '\t') + "</pre>";
                    x += "<hr>" + "</div>";
                    document.getElementById("showmyjson").innerHTML = x;

                } else
                    document.getElementById("showmyjson").innerHTML = "<h3>" + data + "</h3>";
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('error while post to java');
            }
        });
    } else {
        alert("Bitte mindestens eines der Checkboxen anzreuzen");
    }
}

function showAndHide() {

    document.getElementsByTagName('pre')[0].style.display =
        (document.getElementsByTagName('pre')[0].style.display !== "block") ?
            "block" : "none";
    if (document.getElementsByTagName('pre')[0].style.display === "block") {
        document.getElementById("showJsonText").innerText = "Das Ergebnis ausblenden"
    } else {
        document.getElementById("showJsonText").innerText = "Das ganze Ergebnis in JSON Format anzeigen"
    }
}

function isJson(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return true;
}

// ++++++++++++++++++++++++++++++ End Select Algorithmen +++++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++ Start Susoku +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

function generateSudoku() {

    var grid = [
        [1, 2, 3, 4, 5, 6, 7, 8, 9],
        [4, 5, 6, 7, 8, 9, 1, 2, 3],
        [7, 8, 9, 1, 2, 3, 4, 5, 6],
        [2, 3, 4, 5, 6, 7, 8, 9, 1],
        [5, 6, 7, 8, 9, 1, 2, 3, 4],
        [8, 9, 1, 2, 3, 4, 5, 6, 7],
        [3, 4, 5, 6, 7, 8, 9, 1, 2],
        [6, 7, 8, 9, 1, 2, 3, 4, 5],
        [9, 1, 2, 3, 4, 5, 6, 7, 8]
    ];

    var hGrid = [
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0]
    ];

    shuffle(grid);
    hideTiles(grid, hGrid);

    this.getTileNumber = function (row, col) {
        return hGrid[row][col];
    };
}

function shuffle(grid) {

    var i, j, k, temp, col, col1, col2,
        row1, row2, sub, sub1, sub2, num1, num2;

    //swap the same columns of each subsquare
    for (i = 0; i < 25; i++) {
        col = Math.floor(Math.random() * 3);
        sub1 = Math.floor(Math.random() * 3);
        sub2 = Math.floor(Math.random() * 3);
        for (j = 0; j < grid.length; j++) {
            temp = grid[j][col + sub1 * 3];
            grid[j][col + sub1 * 3] = grid[j][col + sub2 * 3];
            grid[j][col + sub2 * 3] = temp;
        }
    }

    //swap all columns within each subsquare
    for (i = 0; i < 25; i++) {
        sub = Math.floor(Math.random() * 3);
        col1 = Math.floor(Math.random() * 3);
        col2 = Math.floor(Math.random() * 3);
        while (col1 === col2) col2 = Math.floor(Math.random() * 3);
        for (j = 0; j < grid.length; j++) {
            temp = grid[j][sub * 3 + col1];
            grid[j][sub * 3 + col1] = grid[j][sub * 3 + col2];
            grid[j][sub * 3 + col2] = temp;
        }
    }

    //swap all rows within each subsquare
    for (i = 0; i < 25; i++) {

        sub = Math.floor(Math.random() * 3);
        row1 = Math.floor(Math.random() * 3);
        row2 = Math.floor(Math.random() * 3);
        while (row1 === row2) row2 = Math.floor(Math.random() * 3);
        for (j = 0; j < grid.length; j++) {
            temp = grid[sub * 3 + row1][j];
            grid[sub * 3 + row1][j] = grid[sub * 3 + row2][j];
            grid[sub * 3 + row2][j] = temp;
        }
    }

    //swap one number with another
    for (i = 0; i < 25; i++) {
        num1 = Math.floor(Math.random() * 9 + 1);
        num2 = Math.floor(Math.random() * 9 + 1);
        while (num1 === num2) num2 = Math.floor(Math.random() * 9 + 1);
        for (j = 0; j < grid.length; j++) {
            for (k = 0; k < grid[j].length; k++) {
                if (grid[j][k] === num1)
                    grid[j][k] = num2;
                else if (grid[j][k] === num2)
                    grid[j][k] = num1;
            }
        }
    }
}

function hideTiles(aGrid, hiddenGrid) {

    // Randomly hide tiles, no guarantee for a unique solution
    var numTiles, k;

    for (var c = 0; c < 9; c++) {
        for (var d = 0; d < 9; d++) {
            hiddenGrid[c][d] = aGrid[c][d];
        }
    }

    for (var i = 0; i < 4; i++) {
        numTiles = Math.floor(Math.random() * 8 + 6);
        while (numTiles > 0) {
            k = Math.floor(Math.random() * 9);
            hiddenGrid[i][k] = 0;
            hiddenGrid[8 - i][8 - k] = 0;
            numTiles--;

        }
    }

    numTiles = Math.floor(Math.random() * 4 + 2);
    while (numTiles > 0) {
        k = Math.floor(Math.random() * 4);
        hiddenGrid[4][k] = 0;
        hiddenGrid[4][8 - k] = 0;
        numTiles--;
    }
}

function generateMyOwn() {

    var hGrid = [
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0]
    ];

    this.getTileNumber = function (row, col) {
        return hGrid[row][col];
    };
}

function is_natural(s) {
    if (s === "") {
        return true;
    } else {
        let n = parseInt(s);
        return n > 0 && n < 10 && n.toString() === s;
    }
}

function isValid(arraySolution) {

    for (let y = 0; y < 9; ++y) {
        for (let x = 0; x < 9; ++x) {

            let value = arraySolution[y][x];

            if (is_natural(value)) {
                if (value) {
                    // Check the line
                    for (var x2 = 0; x2 < 9; ++x2) {
                        if (x2 !== x && arraySolution[y][x2] === value) {
                            return false;
                        }
                    }

                    // Check the column
                    for (let y1 = 0; y1 < 9; ++y1) {
                        if (y1 !== y && arraySolution[y1][x] === value) {
                            return false;
                        }
                    }

                    // Check the square
                    let startY = Math.floor(y / 3) * 3;
                    for (let y2 = startY; y2 < startY + 3; ++y2) {
                        let startX = Math.floor(x / 3) * 3;
                        for (x2 = startX; x2 < startX + 3; ++x2) {
                            if ((x2 !== x || y2 !== y) && arraySolution[y2][x2] === value) {
                                return false;
                            }
                        }
                    }
                }
            } else {
                return false;
            }
        }
    }
    return true;
}