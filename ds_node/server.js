console.log("Hello from the roborio");

var socket = require("socket.io");
var express = require("express");

var app = express();
var server = app.listen(2783);
var io = socket(server);

app.use(express.static('client'));

io.sockets.on("connection", function(socket) {
  console.log("new connection");
})
