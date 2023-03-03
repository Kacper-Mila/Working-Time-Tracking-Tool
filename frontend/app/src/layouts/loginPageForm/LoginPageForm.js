import UserService from "../../serviceHubs/user-service-hub";
import React, {useEffect, useState, useRef} from "react";
import {useNavigate} from 'react-router-dom';
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import AuthService from "../../serviceHubs/auth-service-hub";
import './loginPageForm.css';

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

    useEffect(() => {
        document.body.style.overflow = "hidden";
    })

    const navigate = useNavigate();

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
        <section className="vh-100">
            <div className="container py-5 h-100">
                <div className="row d-flex align-items-center justify-content-center h-100">
                    <div className="col-md-7 col-lg-5 col-xl-5 offset-xl-1 text-light text-center">
                        <Form onSubmit={handleLogin} ref={form}>

                            <div className="form-outline mb-4">
                                <label className="form-label" htmlFor="username">Username</label>
                                <Input
                                    type="text"
                                    className="form-control form-control-lg"
                                    name="username"
                                    value={userId}
                                    required
                                    onChange={onChangeUserId}
                                    validations={[required]}
                                />
                            </div>

                            <div className="form-outline mb-4">
                                <label className="form-label" htmlFor="password">Password</label>
                                <Input
                                    type="password"
                                    className="form-control form-control-lg"
                                    name="password"
                                    value={password}
                                    required
                                    onChange={onChangePassword}
                                    validations={[required]}
                                />
                            </div>

                            <div className="form-group">
                                <button className="btn btn-dark btn-block" disabled={loading}>
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
                            <CheckButton style={{display: "none"}} ref={checkBtn}/>
                        </Form>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default Login;

