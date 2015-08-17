var expect = require("chai").expect;
var jsc = require("jsverify");

var add = require('../add').add;

describe("Add", function() {

	it("should add two fixed numbers", function() {
		expect(add(1, 1)).to.equal(2);		
    });

    it("should add two random numbers", function() {

    	var randomAdd = jsc.forall("integer", "integer", function (x, y) {
    		return add(x, y) === add(y, x);
  		});

  		jsc.assert(randomAdd);

    });

});