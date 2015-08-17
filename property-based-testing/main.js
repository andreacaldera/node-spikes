console.log("Property based testing in Node.JS");

var add = require('./add').add;

var x = 1;
var y = 8;
console.log(x + ' + ' + y + ' = ' + add(x, y));