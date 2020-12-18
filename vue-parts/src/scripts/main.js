let rGlNum = 1;
let code = 0;
let tableOfShots = new Array();

import router from "@/router";
// window.alert = function () {
// };

function updateClock() {
    let now = new Date();
    let time = now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds();
    // a cleaner way than string concatenation
    let date = [now.getDate(),
        now.getMonth() + 1,
        now.getFullYear()].join('.');
    document.getElementById('time').innerHTML = [time, date].join(' | ');
    setTimeout(updateClock, 1000);
}

function setR(newR) {
    rGlNum = newR;
    printPaint();
}

const clickAnswer = function (event) {

    var box = document.getElementById("image-coordinates").getBoundingClientRect();

    var body = document.body;
    var docEl = document.documentElement;

    // (2)
    //var scrollTop = window.pageYOffset || docEl.scrollTop || body.scrollTop;
    //var scrollLeft = window.pageXOffset || docEl.scrollLeft || body.scrollLeft;

    // (3)
    var clientTop = docEl.clientTop || body.clientTop || 0;
    var clientLeft = docEl.clientLeft || body.clientLeft || 0;

    // (4)
    var top = box.top - clientTop;
    var left = box.left - clientLeft;

    var xCord = ((event.clientX - left) / box.width) * 300;
    var yCord = ((event.clientY - top) / box.height) * 300;

    xCord = xCord - 150;
    yCord = yCord - 150;

    var r = parseFloat(rGlNum);

    var x = (xCord / 120) * r;
    var y = -(yCord / 120) * r;

    x = parseFloat(x.toString().slice(0, 5));
    y = parseFloat(y.toString().slice(0, 5));
    r = parseFloat(r.toString().slice(0, 5));

    send(x, y, r);
};

function send(x, y, r) {
    let numOflinesInTable = document.getElementById("tableBody").getElementsByTagName("tr").length;
    let numOfCirclesInPaint = document.getElementById("image-coordinates").getElementsByTagName("circle").length - 1;

    if (numOflinesInTable != code || numOfCirclesInPaint != code) {
        code = 0;
    } else {
        code = numOflinesInTable;
    }
    console.log("X: " + x + " Y: " + y + " R: " + r);
    // eslint-disable-next-line no-undef
    $.ajax({
        url: '/Lab4/faces/checkShot',
        headers: {
            "rows": code
        },
        method: 'POST',
        dataType: 'text',
        data: {coord_x: x, coord_y: y, coord_r: r},
        success: function (data, textStatus, request) {
            console.log(data);
            console.log(request.getResponseHeader('rows'));

            saveToApp(data);
            writeTable();
            printPaint();
        }
    });
}

function saveToApp(data) {
    if (code !== 0 && code !== -1) {
        tableOfShots.push(data.slice(0, data.length - 1).split(" "));
    } else {
        let ar = data.slice(0, data.length - 1).split("\n");
        tableOfShots = new Array();
        for (let i = 0; i < ar.length; i++) {
            tableOfShots.push(ar[i].split(" "));
        }
    }
    console.log(tableOfShots);
}

function writeTable() {
    let inHTML = "";
    for (let i = 0; i < tableOfShots.length; i++) {
        inHTML += "<tr>";
        for (let j = 0; j < 6; j++) {
            inHTML += "<td>" + tableOfShots[i][j] + "</td>";
        }
        inHTML += "</tr>";
    }
    document.getElementById("tableBody").innerHTML = inHTML;
}

function printPaint() {
    let inHTML = "<rect width=\"300\" height=\"300\" fill=\"rgb(255,255,255)\" stroke-width=\"1\" stroke=\"rgb(0,0,0)\"></rect>\n" +
        "\n" +
        "                <circle cx=\"150\" cy=\"150\" r=\"60\" fill=\"rgb(0,255,255)\" stroke-width=\"1\" stroke=\"rgb(0,0,0)\"></circle>\n" +
        "                <polyline points=\"150,150 150,300 300,300 300,0 0,0 0,150 150,150\" fill=\"rgb(255,255,255)\"\n" +
        "                          stroke-width=\"1\" stroke=\"rgb(0,0,0)\"></polyline>\n" +
        "\n" +
        "                <rect x=\"150\" y=\"150\" width=\"120\" height=\"60\" fill=\"rgb(0,255,255)\" stroke-width=\"1\"\n" +
        "                      stroke=\"rgb(50,50,50)\"></rect>\n" +
        "\n" +
        "                <polyline points=\"150,30 90,150 150,150 150,90\" fill=\"rgb(0,255,255)\" stroke-width=\"1\"\n" +
        "                          stroke=\"rgb(0,0,0)\"></polyline>\n" +
        "\n" +
        "                <line x1=\"150\" y1=\"0\" x2=\"150\" y2=\"500\" stroke-width=\"1\" stroke=\"rgb(0,0,0)\"></line>\n" +
        "                <line x1=\"0\" y1=\"150\" x2=\"300\" y2=\"150\" stroke-width=\"1\" stroke=\"rgb(0,0,0)\"></line>\n" +
        "\n" +
        "                <line x1=\"145\" y1=\"30\" x2=\"155\" y2=\"30\" stroke-width=\"1\" stroke=\"rgb(0,0,0)\"></line>\n" +
        "                <line x1=\"145\" y1=\"90\" x2=\"155\" y2=\"90\" stroke-width=\"1\" stroke=\"rgb(0,0,0)\"></line>\n" +
        "\n" +
        "                <line x1=\"30\" y1=\"145\" x2=\"30\" y2=\"155\" stroke-width=\"1\" stroke=\"rgb(0,0,0)\"></line>\n" +
        "                <line x1=\"90\" y1=\"145\" x2=\"90\" y2=\"155\" stroke-width=\"1\" stroke=\"rgb(0,0,0)\"></line>\n" +
        "\n" +
        "                <line x1=\"210\" y1=\"145\" x2=\"210\" y2=\"155\" stroke-width=\"1\" stroke=\"rgb(0,0,0)\"></line>\n" +
        "                <line x1=\"270\" y1=\"145\" x2=\"270\" y2=\"155\" stroke-width=\"1\" stroke=\"rgb(0,0,0)\"></line>\n" +
        "\n" +
        "                <line x1=\"145\" y1=\"210\" x2=\"155\" y2=\"210\" stroke-width=\"1\" stroke=\"rgb(0,0,0)\"></line>\n" +
        "                <line x1=\"145\" y1=\"270\" x2=\"155\" y2=\"270\" stroke-width=\"1\" stroke=\"rgb(0,0,0)\"></line>\n" +
        "\n" +
        "                <polyline points=\"150,0 140,15 150,5 160,15 150,0\" fill=\"rgb(249,249,249)\" stroke-width=\"1\"\n" +
        "                          stroke=\"rgb(0,0,0)\"></polyline>\n" +
        "                <polyline points=\"300,150 285,140 295,150 285,160 300,150\" fill=\"rgb(249,249,249)\" stroke-width=\"1\"\n" +
        "                          stroke=\"rgb(0,0,0)\"></polyline>";

    for (let i = 0; i < tableOfShots.length; i++) {
        inHTML += "<circle cx=\"" +
            (150 + 120 * (parseFloat(tableOfShots[i][0]) / rGlNum)) +
            "\" cy=\"" +
            (150 - 120 * (parseFloat(tableOfShots[i][1]) / rGlNum)) +
            "\" r=\"5\" ";
        if ("true".localeCompare(tableOfShots[i][3])) {
            inHTML += "fill=\"rgb(255,0,0)\" ";
        } else {
            inHTML += "fill=\"rgb(0,255,0)\" ";
        }
        inHTML += "stroke-width=\"1\"\n stroke=\"rgb(0,0,0)\"/>";
    }
    document.getElementById("image-coordinates").innerHTML = inHTML;
}

function exit() {
    // eslint-disable-next-line no-undef
    $.ajax({
        url: '/Lab4/faces/exit',
        method: 'POST',
        dataType: 'text',
        // eslint-disable-next-line no-unused-vars
        success: function (data) {
            tableOfShots = new Array();
            localStorage.removeItem("jwt");
            router.push('/Lab4/login');
        }
    });
}

function clear(){
    console.log("Start clearing")
    // eslint-disable-next-line no-undef
    $.ajax({
        url: '/Lab4/faces/checkShot',
        headers: {
            "rows": -2
        },
        method: 'POST',
        dataType: 'text',
        // eslint-disable-next-line no-unused-vars
        success: function (data, textStatus, request) {
            tableOfShots = new Array();
            code = 0;
            writeTable();
            printPaint();
            console.log("clearing success")
        }
    });
}

function start(){
    document.getElementById('image-coordinates').addEventListener('click', clickAnswer);
    updateClock();
    // eslint-disable-next-line no-undef
    $.ajax({
        url: '/Lab4/faces/checkShot',
        headers: {
            "rows": -1
        },
        method: 'POST',
        dataType: 'text',
        success: function (data, textStatus, request) {
            if (request.getResponseHeader('rows') > 0) {
                saveToApp(data);
                writeTable();
                printPaint();
            }
        }
    });
}



export {clear, exit, send, setR, start};