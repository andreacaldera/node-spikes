import React, { Component } from 'react'
import { connect } from 'react-redux'
import Menu from '../components/Menu'

class App extends Component {
  static propTypes = {
    // Injected by React Redux
    // errorMessage: PropTypes.string,
    // Injected by React Router
    // children: PropTypes.node
  }

  handleDismissClick = e => {
    this.props.resetErrorMessage()
    e.preventDefault()
  }

  renderErrorMessage() {
    const { errorMessage } = this.props
    if (!errorMessage) {
      return null
    }

    return (
      <p style={{ backgroundColor: '#e99', padding: 10 }}>
        <b>{errorMessage}</b>
        {' '}
        (<a href="#"
            onClick={this.handleDismissClick}>
          Dismiss
        </a>)
      </p>
    )
  }

  render() {
    const { children } = this.props
    return (
      <div>
        <Menu/>
        <hr />
        <h1>Redux</h1>
        {children}
      </div>
    )
  }
}

const mapStateToProps = (state, ownProps) => ({
  // errorMessage: state.errorMessage,
})

export default connect(mapStateToProps, null)(App)
