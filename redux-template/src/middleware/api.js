// const API_ROOT = 'http://localhost:3000/api/example';

import { exampleDataReceived } from '../actions';

export const CALL_API = Symbol('Call API')

export default store => next => action => {
  if (action.type === 'REQUEST_EXAMPLE_DATA') {
    return  Promise.resolve()
      .then(() => next(action))
      .then(() => store.dispatch(exampleDataReceived({ someKey: 'someData' })))
  }

  return next(action);
}
