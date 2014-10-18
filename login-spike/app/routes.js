module.exports = function (app, passport) {

    app.get('/', function (req, res) {
        res.render('page.ejs',
            {
                content: 'index',
                title: 'Welcome',
                loggedIn: req.isAuthenticated(),
                message: req.flash('message')
            }
        ); // load the index.ejs file
    });

    app.get('/login', function (req, res) {
        // render the page and pass in any flash data if it exists
        res.render('page.ejs',
            {
                content: 'login',
                title: 'Login',
                loggedIn: req.isAuthenticated(),
                message: req.flash('loginMessage')
            }
        );
    });

    app.get('/login/facebook', passport.authenticate('facebook'));

    // Facebook will redirect the user to this URL after approval.  Finish the
    // authentication process by attempting to obtain an access token.  If
    // access was granted, the user will be logged in.  Otherwise,
    // authentication has failed.
    app.get('/login/facebook/callback', passport.authenticate('facebook', { successRedirect: '/profile', failureRedirect: '/login' }));

    app.post('/login', passport.authenticate('local-login', {
        successRedirect: '/profile', // redirect to the secure profile section
        failureRedirect: '/login', // redirect back to the signup page if there is an error
        failureFlash: true
    }));

    app.get('/signup', function (req, res) {
        // render the page and pass in any flash data if it exists
        res.render('page.ejs', {
            content: "signup",
            loggedIn: false,
            title: "Signup",
            message: req.flash('signupMessage')
        });
    });

    app.post('/signup', passport.authenticate('local-signup', {
        successRedirect: '/profile', // redirect to the secure profile section
        failureRedirect: '/signup', // redirect back to the signup page if there is an error
        failureFlash: true // allow flash messages
    }));

    app.get('/profile', isLoggedIn, function (req, res) {
        console.dir(req.user);
        res.render('page.ejs', {
            content: "profile",
            loggedIn: req.isAuthenticated(),
            title: "Profile",
            user: req.user,
            message: undefined
        });
    });

    app.get('/logout', function (req, res) {
        req.logout();
        res.redirect('/');
    });
};

function isLoggedIn(req, res, next) {
    if (req.isAuthenticated()) return next();
    req.flash('message'); // reading message so that it empties previously set messages
    req.flash('message', 'Please login.');
    res.redirect('/');
};


