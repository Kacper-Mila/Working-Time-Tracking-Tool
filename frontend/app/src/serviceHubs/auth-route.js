import React, { Component } from 'react'
import {Route, Navigate} from 'react-router-dom'
import AuthServiceHub from "./auth-service-hub";

class AuthRoute extends Component {
    render() {
        if (AuthServiceHub.isUserLoggedIn()) {
            return <Route {...this.props} />
        } else {
            return <Navigate to="/login" />
        }
    }
}

export default AuthRoute

const Private = (Component) => {
    const auth = false; //your logic

    return auth ? <Component /> : <Navigate to="/login" />
}

//TODO needs total makeover