import React from 'react';
import AdminHeader from "../../layouts/users/AdminHeader";
import Users from "../../layouts/users/Users";


export default function AdminPage() {
    return (
        <div>
            <AdminHeader/>
            <div className='user-container'>
                <Users/>
            </div>
        </div>
    );
}