// Setup basic express server
var express = require('express'); 
var socket = require('socket.io'); 

var server = express();
var io = socket(server.listen(3001));

var objectClients = [];
var objclients = {};
var players=[];

console.log("listening zzzzzzzzzzzzzzzzzz");
// Routing
server.use('/', express.static(__dirname + '/'));

io.on('connection', function(objectSocket) {
	console.log("event");
	objectSocket.strIdent = Math.random().toString(36).substr(2, 8)
	
	objectClients.push(objectSocket.strIdent);
	
		objclients[objectSocket.strIdent]= {
			'strIdent': objectSocket.strIdent,
			'objectSocket': objectSocket
		};
		console.log("id : "+ objectSocket.strIdent);
		
		if(objectClients.length == 1)
		{
			var connected="first user connected";
			objectSocket.emit("firstPlayer",{'first':connected});	
			objectSocket.broadcast.emit("firstPlayer",{'first':connected});	
		}
		
		if(objectClients.length == 2)
		{
			var connected="second user connected";
			objectSocket.emit("secondPlayer",{'second':connected});	
			objectSocket.broadcast.emit("secondPlayer",{'second':connected});	
			
	     objectSocket.emit("players",{'first':players[0],'second':players[1]});	
		}
objectSocket.on('new msg', function (objectData) {
var spl = objectData.split(",");
players.push(spl[0]);
console.log("message :  "+objectData);
if((objectClients.length)%2 != 0)
		{
			var connected="first user connected";
			objectSocket.emit("firstPlayer",{'first':connected});	
			objectSocket.broadcast.emit("firstPlayer",{'first':connected});	
		}
		
		if((objectClients.length)%2 == 0)
		{
			var connected="second user connected";
			objectSocket.emit("secondPlayer",{'second':connected});	
			
         objectSocket.broadcast.emit("players",{'first':players[0],'second':players[1]});				
	     objectSocket.emit("players",{'first':players[0],'second':players[1]});	
		// console.log("second client   "+objectClients[1]);
		 objclients[objectClients[1]].objectSocket.emit('rounds&size',{});
		 
		}
});

objectSocket.on('call', function (objectData) {
var spl = objectData.split(",");
console.log("row : "+ spl[0] + "  column: " +spl[1] + "   player :  " + spl[2]);
objectSocket.broadcast.emit("column_event", {'row':spl[0],'column':spl[1],'player':spl[2]});
});

objectSocket.on('gamewon', function (objectData) {
//var spl = objectData.split(",");
//console.log("row : "+ spl[0] + "  column: " +spl[1] + "   player :  " + spl[2]);
objectSocket.broadcast.emit("gamewon", {'winner':objectData});
});
objectSocket.on('newGame', function (objectData) {
//var spl = objectData.split(",");
//console.log("row : "+ spl[0] + "  column: " +spl[1] + "   player :  " + spl[2]);
objectSocket.broadcast.emit("newGame", {'newgame':objectData});

});



});
