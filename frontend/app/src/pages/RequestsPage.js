import UserRequestsHeader from "./UserRequestsHeader";
import UserRequests from "../components/user_requests/UserRequests";
import '../components/user_requests/request.css'

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