import {useEffect, useState} from "react";
import UserService from "../../serviceHubs/user-service-hub";
import UserForAdminPage from "../../components/UserForAdminPage";


export default function Users() {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        document.body.style.overflow = "hidden";
        prepareAllUsers()
            .then(() => {
                console.log("users loaded successfully")
            }).catch(err => {
            console.error("error", err);
        });

    }, []);

    const prepareAllUsers = async () => {
        let data = await UserService.getAllUsers();
        setUsers(data);
        console.log(data);
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