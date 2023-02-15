import React from "react";

function UserDetails(props) {

    return (
        (props.userId) &&
        <div className="user-details">
            <p className="user-detail user-name">{props.firstName.toUpperCase()} {props.lastName.toUpperCase()}</p>
            <p className="user-detail"><em>user type: </em>{props.userType.toLowerCase()}</p>
            <p className="user-detail"><em>user id:</em> {props.userId}</p>
            <p className="user-detail"><em>team id: </em>{props.teamId}</p>
            <p className="user-detail"><em>manager id:</em> {props.managerId}</p>
            <p className="user-detail"><em>holidays available: </em>{props.holidays}</p>

        </div>
    )
}

export default UserDetails;