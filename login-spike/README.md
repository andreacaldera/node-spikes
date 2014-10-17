# Setup
## Prerequisites
- Node
- MongoDB
- Nodemon: 'npm install -g nodemon' (development only)
- mongod (development only)

## Development
1. `mkdir -p <your-db-dir>/data && cd <your-db-dir>/data && mongod --dbpath <your-db-dir>/data`
1. `cd login-spike && nodemon server.js` (or node server.js for production)
1. add configuration `login-spike/config/private/facebook.js` as following

 ```javascript
module.exports = {
    clientID: '<your-client-id>',
    clientSecret: '<your-client-secret>',
    callbackUrl: 'http://localhost:8080/login/facebook/callback'
};
 ```
 
# Usage
## Development
[Login Spike Home](http://localhost:8080/)
 
  
  

