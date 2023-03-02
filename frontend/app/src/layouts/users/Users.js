import {useEffect, useState} from "react";
import UserService from "../../serviceHubs/user-service-hub";
import UserForAdminPage from "../../components/UserForAdminPage";


export default function Users({userType}) {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        document.body.style.overflow = "hidden";
        prepareAllUsers()
            .then(() => {
                console.log("users loaded successfully")
            }).catch(err => {
            console.error("error", err);
        });

    }, [userType]);

    const prepareAllUsers = async () => {
        let data = await UserService.getAllUsers();
        if (userType === "ALL") {
            setUsers(data)
        } else {
            setUsers(data.filter((user) => (
                user.userType === userType
            )))
        }
    }

    const deleteUser = async (userId) => {
        await UserService.deleteUser(userId);
        window.location.reload();
    }


    return (
        <>
            {users.map((user) => (
                <UserForAdminPage key={user.userId}
                                  email={user.email}
                                  userId={user.userId}
                                  firstName={user.firstName}
                                  lastName={user.lastName}
                                  password={user.password}
                                  holidays={user.holidays}
                                  userType={user.userType}
                                  managerId={user.managerId}
                                  teamId={user.teamId}
                                  onDelete={deleteUser}
                />
            ))}
        </>
    );
}