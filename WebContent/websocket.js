var wsUri = "ws://" + document.location.host + "/WebComunicator/endpoint";

console.log(wsUri);

var websocket = new WebSocket(wsUri);
var output = document.getElementById("output");
var username = document.getElementById("username");
var msg = document.getElementById("message");

var msgId = 0
var lastDt = ""

function writeToScreen(message) {
	console.log('writeToScreen')
	innhtml = output.innerHTML
	console.log('innerhtml: '+ innhtml)	
	output.innerHTML = message + innhtml;
}

function writeMineMessage(dt, message) {
	console.log('writeMineMessage: '+message)
	writeToScreen("<div class='datetime'>"+dt+"</div><div class='mineTitle'>Ja</div><div class='mineBox'>" + message + "</div>");
}

function writeOtherMessage(dt, name, message) {
	console.log('writeOtherMessage: ' + name + "> "+message)	
	writeToScreen("<div class='datetime'>"+dt+"</div><div class='otherTitle'>" + name + "</div><div class='otherBox'>" + message + "</div>");
}

websocket.onerror = function(evt) {
	onError(evt)
};

function onError(evt) {
	writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

websocket.onopen = function(evt) {
	onOpen(evt)
};

function onOpen() {
	// writeToScreen("Connected to " + wsUri);
}

websocket.onmessage = function(evt) {
	onMessage(evt)
};

function onMessage(evt) {
	console.log("received: " + evt.data);
	data = evt.data.split("#")
	_cid = data[0]
	_dt = data[1]
	_n = data[2]
	_msg = data[3]
//	console.log("clientId: " + _cid)
//	console.log("date time: " + _dt)
//	console.log("client name: " + _n)
//	console.log("client message: " + _msg)

	data2 = _msg.split("$")
	_msgId = data2[0]
	_name = data2[1]
	_message = data2[2]

//	console.log("lastMsgId: " + lastMsgId)
//	console.log("msgId: " + _msgId)
//	console.log("user name: " + _name)
//	console.log("user message: " + _message)
    if (lastDt!=_dt) {
		if (msgId == _msgId) {
				writeMineMessage(_dt, _message)
			} else {
				writeOtherMessage(_dt, _name, _message)
	    }
    }
	lastDt = _dt
}

function sendMessage() {
	if (username.value.length == 0) {
		alert("Uzupelnij imie uzytkownika!")
		return;
	} 
	if (msg.value.length == 0) {
		alert("Podaj wiadomość do wysłania!")
		return;
	} 
	msgId = Math.floor((Math.random() * 100000) + 1);
	websocket.send(msgId + '$' + username.value + '$' + msg.value)
	
}
