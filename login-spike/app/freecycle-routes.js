module.exports = function (app) {

    var Freecytem = require('./models/freecytem.js');
    var formidable = require('formidable');
    var util = require('util');
    var fs = require('fs');
    var gm = require('gm').subClass({ imageMagick: true });
    var uuid = require('node-uuid');
    var path = require('path');

    app.get('/freecycle', function (req, res) {
        var query = !!req.param("q_title") ? {title: new RegExp(req.param("q_title"), "i")} : {};
        Freecytem.find(query).exec(function (err, result) {
            if (err) throw err;
            res.render('page.ejs', {
                content: "get-freecycle",
                title: "Freecycle items",
                loggedIn: req.isAuthenticated(),
                info: req.flash('itemCreatedMessage'),
                warn: undefined,
                freecytems: result
            });
        });
    });

    app.get('/freecycle/add', function (req, res) {
        res.render('page.ejs', {
            content: "add-freecycle",
            title: "Add a freecycle item",
            loggedIn: req.isAuthenticated(),
            info: undefined,
            warn: req.flash('mustLoginMessage')
        });
    });

    app.post('/api/freecycle/freecytem', function (req, res) {
        var form = new formidable.IncomingForm();
        form.parse(req, function (err, fields, files) {
            var uid =  uuid.v1();
            var imageFilename = '/images/freecytem/' + uid + path.extname(files.image.name);
            console.log(files.image.path);
            console.log(imageFilename);
            gm(files.image.path)
                .autoOrient()
                .write("public" + imageFilename, function (err) {
                    if (err) throw err;
                    var newFreecytem = new Freecytem({
                        id: uid,
                        title: fields.title,
                        description: fields.description,
                        image: imageFilename,
                        location: fields.location
                    });
                    console.dir(newFreecytem);
                    newFreecytem.save(function (err) {
                        if (err) throw err;
                        req.flash('itemCreatedMessage', 'Thanks for adding your item to Freecycle.');
                        res.redirect("/freecycle");
                    });
                });
        });

        form.on('end', function (fields, files) {

        });

    });

};

function isLoggedIn(req, res, next) {
    if (req.isAuthenticated()) return next();
    req.flash('mustLoginMessage', 'Please login first.');
    res.redirect('/');
};


