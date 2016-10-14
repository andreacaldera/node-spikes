import React, { Component, PropTypes } from 'react'
import { connect } from 'react-redux'
import { requestExampleData } from '../actions';

class ExamplePage extends Component {
  static propTypes = {
     exampleData: PropTypes.object,
     requestExampleData: PropTypes.func.isRequired,
  }

  render() {
    const exampleData = this.props.exampleData ? (<div>Got data: {this.props.exampleData.someKey}</div>) : null;
    return (
      <div>
        <h2>Example</h2>
        <button onClick={this.props.requestExampleData}>Get example data</button>
        {exampleData}
      </div>
    )
  }
}

const mapStateToProps = (state) => ({
  exampleData: state.exampleData,
});


const mapDispatchToProps = (dispatch) => ({
  requestExampleData(e) {
     e.preventDefault();
     dispatch(requestExampleData())
  },
});

export default connect(mapStateToProps, mapDispatchToProps)(ExamplePage)
