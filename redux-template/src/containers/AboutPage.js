import React, { Component } from 'react'
import { connect } from 'react-redux'

class AboutPage extends Component {
  render() {
    return (
      <div>
        <h2>About</h2>
      </div>
    )
  }
}

export default connect(null, null)(AboutPage)
