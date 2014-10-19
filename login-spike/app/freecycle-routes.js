module.exports = function (app) {

    var Freecytem = require('./models/freecytem.js');

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

    app.get('/freecycle/add', isLoggedIn, function (req, res) {
        res.render('page.ejs', {
            content: "add-freecycle",
            title: "Add a freecycle item",
            loggedIn: req.isAuthenticated(),
            info: undefined,
            warn: req.flash('mustLoginMessage')
        });
    });

    app.post('/api/freecycle/freecytem', function (req, res) {
        var newFreecytem = new Freecytem({
            title: req.body.title,
            description: req.body.description,
            location: req.body.location
        });
        newFreecytem.save(function (err) {
            if (err) throw err;
            req.flash('itemCreatedMessage', 'Thanks for adding your item to Freecycle.');
            res.redirect("/freecycle");
        });
    });

};

function isLoggedIn(req, res, next) {
    if (req.isAuthenticated()) return next();
    req.flash('mustLoginMessage', 'Please login first.');
    res.redirect('/');
};


