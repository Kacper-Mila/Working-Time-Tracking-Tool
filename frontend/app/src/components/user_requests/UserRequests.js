import {useEffect, useState} from "react";
import RequestService from "../../serviceHubs/RequestServiceHub";
import Request from "./Request";

export default function UserRequests() {
    const [requests, setRequests] = useState([]);

    useEffect(() => {
        prepareUserRequests()
            .then(() => {
                console.log("user requests loaded successfully")
            }).catch(err => {
            console.error("error", err);
        });

    }, []);

    const prepareUserRequests = async () => {
        let data = await RequestService.getRequestsByUserId(localStorage.getItem("userId"));
        setRequests(data);
        console.log(requests);
    }

    const deleteRequest = async (id) => {
        await RequestService.deleteRequest(id);
        await prepareUserRequests();
    }

    return (
        <>
            {requests.map((request) => (
                <Request key={request.id}
                         requestId={request.id}
                         requestType={request.type}
                         requestStartDate={request.startDate}
                         requestEndDate={request.endDate}
                         requestRegistrationDate={request.registrationDate}
                         requestComment={request.comment}
                         onDelete={deleteRequest}
                         ownerId={request.ownerId}
                />
            ))}

        </>
    );
}