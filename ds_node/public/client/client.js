var socket;

function setup() {
  createCanvas(800, 800)
  background(50)
  socket = io.connect("http://roborio-2783-frc.local:2783");
}

function mousePressed() {
  ellipse(mouseX, mouseY, 100, 100)
}
