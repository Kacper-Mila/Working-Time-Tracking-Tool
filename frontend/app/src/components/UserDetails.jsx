import React from "react";

function UserDetails(props) {

    return (
        (props.userId) &&
        <table className="user-details">
            <thead className="user-name">
            <th colSpan="2">
                {props.firstName.toUpperCase()} {props.lastName.toUpperCase()}
            </th>
            </thead>
            <tbody>

            <tr className="my-1">
                <th className="user-detail">
                    user type:
                </th>
                <td>
                    {props.userType.toLowerCase()}
                </td>
            </tr>
            <tr className="my-1">
                <th className="user-detail">
                    user id:
                </th>
                <td>
                    {props.userId}
                </td>
            </tr>
            <tr className="my-1">
                <th className="user-detail">
                    team id:
                </th>
                <td>
                    {props.teamId}
                </td>
            </tr>
            <tr className="my-1">
                <th className="user-detail">
                    manager id:
                </th>
                <td>
                    {props.managerId}
                </td>
            </tr>
            <tr className="user-detail my-1">
                <th>
                    holidays available:
                </th>
                <td>
                    {props.holidays}
                </td>
            </tr>
            </tbody>


            {/*<tr className="user-detail"><em>user type: </em>{props.userType.toLowerCase()}</tr>*/}
            {/*<p className="user-detail"><em>user id:</em> {props.userId}</p>*/}
            {/*<p className="user-detail"><em>team id: </em>{props.teamId}</p>*/}
            {/*<p className="user-detail"><em>manager id:</em> {props.managerId}</p>*/}
            {/*<p className="user-detail"><em>holidays available: </em>{props.holidays}</p>*/}
        </table>
    )
}

export default UserDetails;