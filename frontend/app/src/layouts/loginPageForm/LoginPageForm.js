import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import UserService from "../../serviceHubs/user-service-hub";

export default function LoginPageForm() {
    const navigate = useNavigate();
    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');

    useEffect(() => {
        document.body.style.overflow = "hidden";
    })

    const logIn = (userId) => {
        UserService.getUserByUserId(userId).then(() => {
            localStorage.setItem("userId", userId);
            navigate('/');
        }).catch(err => {
            console.error('error', err);
        })
    }

    const submitUser = () => {
        if (userId !== '' && password !== '') {
            logIn(userId);
        }
    }

    return (
        <section className="vh-100">
            <div className="container py-5 h-100">
                <div className="row d-flex align-items-center justify-content-center h-100">
                    {/*<div className="col-md-8 col-lg-7 col-xl-6">*/}
                    {/*/!*Img here*!/*/}
                    {/*</div>*/}
                    <div className="col-md-7 col-lg-5 col-xl-5 offset-xl-1 text-light">
                        <form>
                            <div className="form-outline mb-4">
                                <input type="text"
                                       className="form-control form-control-lg"
                                       required
                                       onChange={
                                           e => setUserId(e.target.value)
                                       }
                                       value={userId}
                                />
                                <label className="form-label" htmlFor="form1Example13">ID</label>
                            </div>
                            <div className="form-outline mb-4">
                                <input type="password"
                                       className="form-control form-control-lg"
                                       required
                                       onChange={
                                           e => setPassword(e.target.value)
                                       }
                                       value={password}
                                />
                                <label className="form-label" htmlFor="form1Example23">Password</label>
                            </div>
                            <div className="d-flex justify-content-around align-items-center mb-4"></div>
                            <button type="button"
                                    className="sign-in-button btn mr-4 btn-secondary btn-lg btn-block"
                                    onClick={submitUser}
                            >Sign in
                            </button>
                            {/*<div className="divider d-flex align-items-center justify-content-center my-4">*/}
                            {/*    <p className="text-center fw-bold mx-3 mb-0 text-secondary">OR</p>*/}
                            {/*</div>*/}

                            {/*<a className="btn btn-primary btn-lg btn-block"*/}
                            {/*   href=""*/}
                            {/*   role="button">*/}
                            {/*    <i className="google-button fab fa-twitter me-2"></i>Continue with Google</a>*/}
                        </form>
                    </div>
                </div>
            </div>
        </section>
    );
}