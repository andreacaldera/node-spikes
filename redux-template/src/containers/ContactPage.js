import React, { Component } from 'react'
import { connect } from 'react-redux'

class ContactPage extends Component {
  render() {
    return (
      <div>
        <h2>Contact</h2>
      </div>
    )
  }
}

export default connect(null, null)(ContactPage)
