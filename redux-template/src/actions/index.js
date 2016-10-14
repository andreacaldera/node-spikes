export const loadExampleData = (fullName, nextPage) => (dispatch, getState) => {
  // const {
  //   nextPageUrl = `repos/${fullName}/stargazers`,
  //   pageCount = 0
  // } = getState().pagination.stargazersByRepo[fullName] || {}
  //
  // if (pageCount > 0 && !nextPage) {
  //   return null
  // }

  // return dispatch(fetchStargazers(fullName, nextPageUrl))
  return dispatch({});
}

export const requestExampleData = () => ({
  type: 'REQUEST_EXAMPLE_DATA',
});


export const exampleDataReceived = (data) => ({
  type: 'EXAMPLE_DATA_RECEIVED',
  payload: data,
});
