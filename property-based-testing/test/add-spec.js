var expect = require("chai").expect;
var add = require('../add').add;

describe("Add", function() {
	it("should add two numbers", function() {
		expect(add(1, 1)).to.equal(2);		
    });

});