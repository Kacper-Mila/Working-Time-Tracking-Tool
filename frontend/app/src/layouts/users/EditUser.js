import React, {useState} from "react";
import axios from "axios";
import {Col, Container, Row} from "react-bootstrap";

export default function EditUser(props) {
    const [userType, setUserType] = useState('');
    const [email, setEmail] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [password, setPassword] = useState('');
    const [managerId, setManagerId] = useState('');
    const [teamId, setTeamId] = useState('');
    const [holidays, setHolidays] = useState('');


    const onSubmit = async (e) => {
        e.preventDefault()
        console.log(props.userId);

        await axios.patch(`http://localhost:8080/api/v1/users/update?userId=${props.userId}`, {
            email: email,
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
        props.onCancel();
        window.location.reload();
    }

    return (
        <div>
            <form onSubmit={onSubmit}>
                <Container>
                    <Row>
                        <Col>
                            <label className="form-label" htmlFor="form1Example13">First
                                Name </label>
                            <input type="text"
                                   className="form-control form-control-lg"
                                   value={firstName}
                                   onChange={(e) => setFirstName(e.target.value)}
                                   required
                            />
                        </Col>
                        <Col>
                            <label className="form-label" htmlFor="form1Example13">Last Name </label>
                            <input type="text"
                                   className="form-control form-control-lg"
                                   value={lastName}
                                   onChange={(e) => setLastName(e.target.value)}
                                   required
                            />
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <label className="form-label" htmlFor="form1Example13">Email </label>
                            <input type="email"
                                   className="form-control form-control-lg"
                                   value={email}
                                   onChange={(e) => setEmail(e.target.value)}
                                   required
                            />
                        </Col>
                        <Col>
                            <label className="form-label" htmlFor="form1Example13">Password </label>
                            <input type="password"
                                   className="form-control form-control-lg"
                                   value={password}
                                   onChange={(e) => setPassword(e.target.value)}
                                   required
                            />
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <label className="form-label" htmlFor="form1Example13"> User
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
                        </Col>
                        <Col>
                            <label className="form-label" htmlFor="form1Example13">Team ID </label>
                            <input type="text"
                                   className="form-control form-control-lg"
                                   value={teamId}
                                   onChange={(e) => setTeamId(e.target.value)}
                                   required
                            />
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <label className="form-label" htmlFor="form1Example13">Manager
                                ID </label>
                            <input type="text"
                                   className="form-control form-control-lg"
                                   value={managerId}
                                   onChange={(e) => setManagerId(e.target.value)}
                                   required
                            />
                        </Col>
                        <Col>
                            <label className="form-label" htmlFor="form1Example13">Holidays </label>
                            <input type="number"
                                   className="form-control form-control-lg"
                                   value={holidays}
                                   onChange={(e) => setHolidays(e.target.value)}
                                   required
                            />
                        </Col>
                    </Row>
                    <Row className='mt-4'>
                        <Col>
                            <button type="submit"
                                    className="btn mr-4 btn-outline-success btn-lg btn-block"
                            >Save
                            </button>
                        </Col>
                        <Col>
                            <button type="reset"
                                    className="btn mr-4 btn-outline-danger btn-lg btn-block"
                                    onClick={props.onCancel}
                            >Cancel
                            </button>
                        </Col>
                    </Row>
                </Container>
            </form>

        </div>
    );
}