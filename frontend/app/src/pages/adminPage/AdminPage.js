import React, {useState} from 'react';
import AdminHeader from "../../layouts/users/AdminHeader";
import Users from "../../layouts/users/Users";


export default function AdminPage() {
    const [selectedUserType, setSelectedUserType] = useState("ALL");
    return (
        <div>
            <AdminHeader setSelectedUserType={setSelectedUserType}/>
            <div className='user-container'>
                <Users userType={selectedUserType}/>
            </div>
        </div>
    );
}