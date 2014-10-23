var mongoose = require('mongoose');

var schema = mongoose.Schema({
    title: String,
    description: String,
    location: String,
    status: String,
    image: String
});

module.exports = mongoose.model('Freecytem', schema);