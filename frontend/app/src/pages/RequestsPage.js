import UserRequestsHeader from "./UserRequestsHeader";
import UserRequests from "../components/user_requests/UserRequests";

export default function RequestsPage(){

    return(
        <div>
            <UserRequestsHeader />
            <UserRequests />
        </div>
    );
}