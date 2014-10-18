var LocalStrategy = require('passport-local').Strategy;
var facebookConfig = require('./private/facebook.js');
var FacebookStrategy = require('passport-facebook').Strategy;

// load up the user model
var User = require('../app/models/user');

// expose this function to our app using module.exports
module.exports = function (passport) {

    passport.serializeUser(function (user, done) {
        done(null, user.id);
    });

    passport.deserializeUser(function (id, done) {
        User.findById(id, function (err, user) {
            done(err, user);
        });
    });

    passport.use(new FacebookStrategy({
            clientID: facebookConfig.clientID,
            clientSecret: facebookConfig.clientSecret,
            callbackURL: facebookConfig.callbackUrl
        },
        function (accessToken, refreshToken, profile, done) {
            User.findOne({'facebook.id': profile.id}, function (err, user) {
                if (err) return done(err);
                if (user) return done(null, user);
                var newUser = new User();
                console.dir(profile);
                newUser.facebook.id = profile.id;
                newUser.facebook.token = accessToken;
                newUser.facebook.email = profile.email;
                newUser.facebook.name = profile.name.givenName + (!!profile.name.middleName ? (" " + profile.name.middleName) : "") + " " + profile.name.familyName;

                newUser.save(function (err) {
                    if (err) throw err;
                    return done(null, newUser);
                });
            });
        }
    ));

    passport.use('local-login', new LocalStrategy({
            usernameField: 'email',
            passwordField: 'password',
            passReqToCallback: true
        },
        function (req, email, password, done) {
            User.findOne({ 'local.email': email }, function (err, user) {
                if (err) return done(err);
                if (!user) return done(null, false, req.flash('loginMessage', 'Invalid username and password.'));
                if (!user.validPassword(password)) return done(null, false, req.flash('loginMessage', 'Invalid username and password.'));
                return done(null, user);
            });

        }));

    passport.use('local-signup', new LocalStrategy({
            usernameField: 'email',
            passwordField: 'password',
            passReqToCallback: true
        },
        function (req, email, password, done) {
            process.nextTick(function () {
                User.findOne({ 'local.email': email }, function (err, user) {
                    if (err) return done(err);
                    if (user) return done(null, false, req.flash('signupMessage', 'That email is already taken.'));
                    var newUser = new User();
                    newUser.local.email = email;
                    newUser.local.password = newUser.generateHash(password);
                    newUser.save(function (err) {
                        if (err) throw err;
                        return done(null, newUser);
                    });
                });

            });
        }));

};


