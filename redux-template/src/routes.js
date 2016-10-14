import React from 'react'
import { Route } from 'react-router'
import App from './containers/App'
import AboutPage from './containers/AboutPage'
import ContactPage from './containers/ContactPage'
import ExamplePage from './containers/ExamplePage'

export default
  <Route path="/" component={App}>
    <Route path="/about" component={AboutPage} />
    <Route path="/contact" component={ContactPage} />
    <Route path="/example" component={ExamplePage} />
  </Route>
