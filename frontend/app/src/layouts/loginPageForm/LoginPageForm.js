// import {useEffect, useState} from "react";
// import {useNavigate} from "react-router-dom";
// import UserService from "../../serviceHubs/user-service-hub";
// import './loginPageForm.css';
//
// export default function LoginPageForm() {
//     const navigate = useNavigate();
//     const [userId, setUserId] = useState('');
//     const [password, setPassword] = useState('');
//
//     useEffect(() => {
//         document.body.style.overflow = "hidden";
//     })
//
//     const logIn = (userId) => {
//         UserService.getUserByUserId(userId).then(() => {
//             localStorage.setItem("userId", userId);
//             navigate('/');
//         }).catch(err => {
//             console.error('error', err);
//         })
//     }
//
//     const submitUser = () => {
//         if (userId !== '' && password !== '') {
//             logIn(userId);
//         }
//     }
//
//     return (
//         <section className="vh-100">
//             <div className="container py-5 h-100">
//                 <div className="row d-flex align-items-center justify-content-center h-100">
//                     {/*<div className="col-md-8 col-lg-7 col-xl-6">*/}
//                     {/*/!*Img here*!/*/}
//                     {/*</div>*/}
//                     <div className="col-md-7 col-lg-5 col-xl-5 offset-xl-1 text-light">
//                         <form>
//                             <div className="form-outline mb-4">
//                                 <input type="text"
//                                        className="form-control form-control-lg"
//                                        required
//                                        onChange={
//                                            e => setUserId(e.target.value)
//                                        }
//                                        value={userId}
//                                 />
//                                 <label className="form-label" htmlFor="form1Example13">ID</label>
//                             </div>
//                             <div className="form-outline mb-4">
//                                 <input type="password"
//                                        className="form-control form-control-lg"
//                                        required
//                                        onChange={
//                                            e => setPassword(e.target.value)
//                                        }
//                                        value={password}
//                                 />
//                                 <label className="form-label" htmlFor="form1Example23">Password</label>
//                             </div>
//                             <div className="d-flex justify-content-around align-items-center mb-4"></div>
//                             <button type="button"
//                                     className="sign-in-button btn mr-4 btn-secondary btn-lg btn-block"
//                                     onClick={submitUser}
//                             >Sign in
//                             </button>
//                             {/*<div className="divider d-flex align-items-center justify-content-center my-4">*/}
//                             {/*    <p className="text-center fw-bold mx-3 mb-0 text-secondary">OR</p>*/}
//                             {/*</div>*/}
//
//                             {/*<a className="btn btn-primary btn-lg btn-block"*/}
//                             {/*   href=""*/}
//                             {/*   role="button">*/}
//                             {/*    <i className="google-button fab fa-twitter me-2"></i>Continue with Google</a>*/}
//                         </form>
//                     </div>
//                 </div>
//             </div>
//         </section>
//     );
// }

// import {Component} from "react";
// import AuthServiceHub from "../../serviceHubs/auth-service-hub";
//
// class LoginPageForm extends Component {
//
//     constructor(props) {
//         super(props)
//
//         this.state = {
//             userId: '',
//             password: '',
//             hasLoginFailed: false,
//             showSuccessMessage: false
//         }
//
//         this.handleChange = this.handleChange.bind(this)
//         this.loginClicked = this.loginClicked.bind(this)
//     }
//
//     handleChange(event) {
//         this.setState(
//             {
//                 [event.target.name]: event.target.value
//             }
//         )
//     }
//
//     loginClicked() {
//         AuthServiceHub
//             .executeJwtAuthenticationService(this.state.userId, this.state.password)
//             .then((response) => {
//                 AuthServiceHub.registerSuccessfulLoginForJwt(this.state.userId, response.data.token)
//                 this.props.history.push(`/`)
//             }).catch(() => {
//             this.setState({showSuccessMessage: false})
//             this.setState({hasLoginFailed: true})
//         })
//     }
//
//     render() {
//         return (
//             <div>
//                 <h1>Login</h1>
//                 <div className="container">
//                     {this.state.hasLoginFailed && <div className="alert alert-warning">Invalid Credentials</div>}
//                     {this.state.showSuccessMessage && <div>Login Successful</div>}
//                     User Name: <input type="text" name="userId" value={this.state.userId}
//                                       onChange={this.handleChange}/>
//                     Password: <input type="password" name="password" value={this.state.password}
//                                      onChange={this.handleChange}/>
//                     <button className="btn btn-success" onClick={this.loginClicked}>Login</button>
//                 </div>
//             </div>
//         )
//     }
// }
//
// export default LoginPageForm

import React, { useState, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import AuthService from "../../serviceHubs/auth-service-hub";

const required = (value) => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

const Login = () => {
    let navigate = useNavigate();

    const form = useRef();
    const checkBtn = useRef();

    const [userId, setUserId] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");

    const onChangeUserId = (e) => {
        const userId = e.target.value;
        setUserId(userId);
    };

    const onChangePassword = (e) => {
        const password = e.target.value;
        setPassword(password);
    };

    const handleLogin = (e) => {
        e.preventDefault();

        setMessage("");
        setLoading(true);

        // form.current.validateAll();

        if (checkBtn.current.context._errors.length === 0) {
            AuthService.login(userId, password).then(
                () => {
                    localStorage.setItem("userId", userId);
                    navigate("/");
                    // window.location.reload();
                },
                (error) => {
                    const resMessage =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();

                    setLoading(false);
                    setMessage(resMessage);
                }
            );
        } else {
            setLoading(false);
        }
    };

    return (
        <div className="col-md-12">
            <div className="card card-container">
                <Form onSubmit={handleLogin} ref={form}>
                    <div className="form-group">
                        <label htmlFor="username">Username</label>
                        <Input
                            type="text"
                            className="form-control"
                            name="username"
                            value={userId}
                            onChange={onChangeUserId}
                            validations={[required]}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <Input
                            type="password"
                            className="form-control"
                            name="password"
                            value={password}
                            onChange={onChangePassword}
                            validations={[required]}
                        />
                    </div>

                    <div className="form-group">
                        <button className="btn btn-primary btn-block" disabled={loading}>
                            {loading && (
                                <span className="spinner-border spinner-border-sm"></span>
                            )}
                            <span>Login</span>
                        </button>
                    </div>

                    {message && (
                        <div className="form-group">
                            <div className="alert alert-danger" role="alert">
                                {message}
                            </div>
                        </div>
                    )}
                    <CheckButton style={{ display: "none" }} ref={checkBtn} />
                </Form>
            </div>
        </div>
    );
};

export default Login;

