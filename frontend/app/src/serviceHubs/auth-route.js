import React, { Component } from 'react'
import { Route, Redirect } from 'react-router-dom'
import AuthServiceHub from "./auth-service-hub";
class AuthRoute extends Component {
    render() {
        if (AuthServiceHub.isUserLoggedIn()) {
            return <Route {...this.props} />
        } else {
            return <Redirect to="/login" />
        }

    }
}

export default AuthRoute