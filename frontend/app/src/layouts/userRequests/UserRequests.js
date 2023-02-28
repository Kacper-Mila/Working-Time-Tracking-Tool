import {useEffect, useState} from "react";
import RequestService from "../../serviceHubs/request-service-hub";
import Request from "../../components/userRequests/Request";

export default function UserRequests() {
    const [requests, setRequests] = useState([]);

    useEffect(() => {
        document.body.style.overflow = "hidden";
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
    }

    // TODO
    const prepareUserHolidayRequests = async () => {
        let data = await RequestService.getRequestsByUserId(localStorage.getItem("userId"));
        setRequests(data);
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