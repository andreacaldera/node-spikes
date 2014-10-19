module.exports = function (app, passport) {

    app.get('/', function (req, res) {
        res.render('page.ejs',
            {
                content: 'index',
                title: 'Welcome',
                loggedIn: req.isAuthenticated(),
                info: undefined,
                warn: req.flash('mustLoginMessage')
            }
        );
    });

    app.get('/login', function (req, res) {
        res.render('page.ejs',
            {
                content: 'login',
                title: 'Login',
                loggedIn: req.isAuthenticated(),
                info: undefined,
                warn: req.flash('loginMessage')
            }
        );
    });

    app.get('/login/facebook', passport.authenticate('facebook'));

    app.get('/login/facebook/callback', passport.authenticate('facebook', { successRedirect: '/profile', failureRedirect: '/login' }));

    app.post('/login', passport.authenticate('local-login', {
        successRedirect: '/profile', // redirect to the secure profile section
        failureRedirect: '/login', // redirect back to the signup page if there is an error
        failureFlash: true
    }));

    app.get('/signup', function (req, res) {
        res.render('page.ejs', {
            content: "signup",
            loggedIn: false,
            title: "Signup",
            info: undefined,
            warn: req.flash('signupMessage')
        });
    });

    app.post('/signup', passport.authenticate('local-signup', {
        successRedirect: '/profile',
        failureRedirect: '/signup',
        failureFlash: true
    }));

    app.get('/profile', isLoggedIn, function (req, res) {
        res.render('page.ejs', {
            content: "profile",
            loggedIn: req.isAuthenticated(),
            title: "Profile",
            user: req.user,
            info: undefined,
            warn: undefined
        });
    });

    app.get('/logout', function (req, res) {
        req.logout();
        res.redirect('/');
    });
};

function isLoggedIn(req, res, next) {
    if (req.isAuthenticated()) return next();
    req.flash('mustLoginMessage', 'Please login first.');
    res.redirect('/');
};


