import React, {useState} from 'react';
import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function AddUserForm() {
    const [userType, setUserType] = useState('');
    const [email, setEmail] = useState('');
    const [userId, serUserId] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [password, setPassword] = useState('');
    const [managerId, setManagerId] = useState('');
    const [teamId, setTeamId] = useState('');
    const [holidays, setHolidays] = useState('');
    const navigate = useNavigate();

    const onSubmit = async (e) => {
        e.preventDefault()

        await axios.post('http://localhost:8080/api/v1/users', {
            email: email,
            userId: userId,
            firstName: firstName,
            lastName: lastName,
            password: password,
            holidays: holidays,
            userType: userType,
            managerId: managerId,
            teamId: teamId,
        }).catch(err => {
            let response = JSON.parse(err.request.response);
            alert(response.message);
        });
        navigate('/admin-panel');
    }

    return (
        <div>
            <div className="container py-5 h-100">
                <div className="row d-flex justify-content-center align-items-center h-100">
                    <div className="col-md-7 col-lg-5 col-xl-5 offset-xl-1 text-light">
                        <form className='pb-5' onSubmit={onSubmit}>
                            <div className="form-outline mb-4">
                                <label className="form-label text-light" htmlFor="form1Example13">First
                                    Name </label>
                                <input type="text"
                                       className="form-control form-control-lg"
                                       value={firstName}
                                       onChange={(e) => setFirstName(e.target.value)}
                                       required
                                />
                            </div>
                            <div className="form-outline mb-4">
                                <label className="form-label text-light" htmlFor="form1Example13">Last Name </label>
                                <input type="text"
                                       className="form-control form-control-lg"
                                       value={lastName}
                                       onChange={(e) => setLastName(e.target.value)}
                                       required
                                />
                            </div>
                            <div className="form-outline mb-4">
                                <label className="form-label text-light" htmlFor="form1Example13">Email </label>
                                <input type="email"
                                       className="form-control form-control-lg"
                                       value={email}
                                       onChange={(e) => setEmail(e.target.value)}
                                       required
                                />
                            </div>
                            <div className="form-outline mb-4">
                                <label className="form-label text-light" htmlFor="form1Example13">User ID </label>
                                <input type="text"
                                       className="form-control form-control-lg"
                                       value={userId}
                                       onChange={(e) => serUserId(e.target.value)}
                                       required
                                />
                            </div>
                            <div className="form-outline mb-4">
                                <label className="form-label text-light" htmlFor="form1Example13">Password </label>
                                <input type="password"
                                       className="form-control form-control-lg"
                                       value={password}
                                       onChange={(e) => setPassword(e.target.value)}
                                       required
                                />
                            </div>
                            <div className="form-outline mb-4">
                                <label className="form-label text-light" htmlFor="form1Example13"> User
                                    Type </label>
                                <select className="form-control form-control-lg"
                                        value={userType}
                                        onChange={(e) => setUserType(e.target.value)}
                                        required style={{cursor: "pointer"}}>
                                    <option>SELECT</option>
                                    <option>EMPLOYEE</option>
                                    <option>MANAGER</option>
                                    <option>ADMIN</option>

                                </select>
                            </div>
                            <div className="form-outline mb-4">
                                <label className="form-label text-light" htmlFor="form1Example13">Team ID </label>
                                <input type="text"
                                       className="form-control form-control-lg"
                                       value={teamId}
                                       onChange={(e) => setTeamId(e.target.value)}
                                       required
                                />
                            </div>
                            <div className="form-outline mb-4">
                                <label className="form-label text-light" htmlFor="form1Example13">Manager
                                    ID </label>
                                <input type="text"
                                       className="form-control form-control-lg"
                                       value={managerId}
                                       onChange={(e) => setManagerId(e.target.value)}
                                       required
                                />
                            </div>
                            <div className="form-outline mb-4">
                                <label className="form-label text-light" htmlFor="form1Example13">Holidays </label>
                                <input type="number"
                                       className="form-control form-control-lg"
                                       value={holidays}
                                       onChange={(e) => setHolidays(e.target.value)}
                                       required
                                />
                            </div>

                            <div className="d-flex mb-4"></div>
                            <button type="submit"
                                    className="btn mr-4 btn-secondary btn-lg btn-block"
                            >Add user
                            </button>
                            <button type="reset"
                                    className="btn mr-4 btn-secondary btn-lg btn-block"
                                    onClick={() => {
                                        navigate('/admin-panel')
                                    }}
                            >Cancel
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
}