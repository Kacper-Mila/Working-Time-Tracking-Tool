import UserRequestsHeader from "../../layouts/userRequestHeader/UserRequestsHeader";
import UserRequests from "../../layouts/userRequests/UserRequests";
import './requestPage.css';

export default function RequestsPage(){

    return(
        <div className='container wrapper'>
            <UserRequestsHeader />
            <div className='container request-container' style={{backgroundColor:"whitesmoke"}}>
                <div className='p-5'>
                    <UserRequests />
                </div>
            </div>
        </div>
    );
}