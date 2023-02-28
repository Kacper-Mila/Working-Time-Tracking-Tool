import UserRequestsHeader from "../../layouts/userRequestHeader/UserRequestsHeader";
import UserRequests from "../../layouts/userRequests/UserRequests";
import './requestPage.css';
import {useEffect, useState} from "react";

export default function RequestsPage(){
    const [selectedRequestType, setSelectedRequestType] = useState("ALL");

    // const [requests, setRequests] = useState(requests);
    //
    // const filterRequests = async (filteredRequests) => {
    //     setRequests([requests, filteredRequests])
    // }
    //

    // useEffect(() => {
    //     setSelectedRequestType("ALL")
    // });

    return(
        <div>
            <UserRequestsHeader setSelectedRequestType={setSelectedRequestType} />
            <div className='request-container'>
                <div>
                    <UserRequests requestType={selectedRequestType}/>
                </div>
            </div>
        </div>
    );
}