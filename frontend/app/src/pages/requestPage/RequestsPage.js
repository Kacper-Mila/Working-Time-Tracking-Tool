import UserRequestsHeader from "../../layouts/userRequestHeader/UserRequestsHeader";
import UserRequests from "../../layouts/userRequests/UserRequests";
import './requestPage.css';

export default function RequestsPage(){

    return(
        <div>
            <UserRequestsHeader />
            <div className='request-container'>
                <div>
                    <UserRequests />
                </div>
            </div>
        </div>
    );
}