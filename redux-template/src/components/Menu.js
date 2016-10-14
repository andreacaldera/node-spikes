import React, { Component } from 'react'
import { browserHistory } from 'react-router'

export default class Menu extends Component {
  routeChange = (e) => {
    e.preventDefault();
    const pathParts = e.target.href.split('/');
    const path = pathParts[pathParts.length - 1];
    browserHistory.push(`${path}`);
  }

  render() {
    return (
      <div>
        <a href="/" onClick={(e) => this.routeChange(e, '/')}>Home</a>
        <a href="/about" onClick={this.routeChange}>About</a>
        <a href="/contact" onClick={this.routeChange}>Contact</a>
        <a href="/example" onClick={this.routeChange}>Example</a>
      </div>
    )
  }
}
