import React, { Component } from 'react'
import { browserHistory } from 'react-router'

export default class Menu extends Component {
  routeChange = (e, path) => {
    e.preventDefault();
    browserHistory.push(`${path}`);
  }

  render() {
    return (
      <div>
        <a href="/" onClick={(e) => this.routeChange(e, '/')}>Home</a>
        <a href="/about" onClick={(e) => this.routeChange(e, 'about')}>About</a>
        <a href="/contact" onClick={(e) => this.routeChange(e, 'contact')}>Contact</a>
      </div>
    )
  }
}
