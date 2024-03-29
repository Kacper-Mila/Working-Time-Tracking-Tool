import React, {useEffect, useState} from 'react';
import UserDetails from "../../components/userDetails/UserDetails";
import image from "../../images/em.png"
import UserService from "../../serviceHubs/user-service-hub";
import {Link, useNavigate} from "react-router-dom";
import './user.css';

function User() {
    const navigate = useNavigate();
    const [userDetails, setUserDetails] = useState([]);

    const logOut = () => {
        localStorage.clear();
        navigate('/login');
    }

    useEffect(() => {
        prepareUserDetails()
            .then(() => {
                console.log("user details taken successfully")
            }).catch(err => {
            console.error("error", err);
        });

    }, []);

    const prepareUserDetails = async () => {
        let data = await UserService.getUserByUserId(localStorage.getItem("userId"));
        let result = [];
        result.push(data);
        setUserDetails(result);
    }

    return (
        <div className="user-details-container">
            <div className="user-details-container-wrapper">
                <div className="user-details-inner-container">
                    <Link className='logo-span' to="/">
                        WTTT
                    </Link>
                    <div className="details">
                        <img className="user-image" src={image} alt="user-img"/>
                        {userDetails.map(user => {
                            // TODO move element to component?
                            return <UserDetails key={user.userId}
                                                userId={user.userId}
                                                firstName={user.firstName}
                                                lastName={user.lastName}
                                                teamId={user.teamId}
                                                managerId={user.managerId}
                                                holidays={user.holidays}
                                                userType={user.userType}
                            />
                        })}
                    </div>
                    <div className="sign-out-container">
                        <div className="sign-out-items" onClick={logOut}>
                            <svg xmlns="http://www.w3.org/2000/svg"
                                 className="sign-out-icon bi bi-box-arrow-right" width="20" height="25"
                                 fill="currentColor"
                                 viewBox="0 0 16 16">
                                <path
                                    d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0v2z"/>
                                <path
                                    d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"/>
                            </svg>
                            <p>Sign Out</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default User;