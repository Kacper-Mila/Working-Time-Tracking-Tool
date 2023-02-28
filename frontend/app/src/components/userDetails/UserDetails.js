import React from "react";
import './userDetail.css';

export default function UserDetails(props) {

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
        </table>
    )
}