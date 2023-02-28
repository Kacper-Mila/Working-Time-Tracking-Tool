import {useEffect, useState} from "react";
import RequestService from "../../serviceHubs/request-service-hub";
import Request from "../../components/userRequests/Request";
import {map} from "react-bootstrap/ElementChildren";

export default function UserRequests({requestType}) {
    const [requests, setRequests] = useState([]);

    useEffect(() => {
        document.body.style.overflow = "hidden";
        prepareUserRequests()
            .then(() => {
                console.log("user requests loaded successfully")
            }).catch(err => {
            console.error("error", err);
        });

    }, [requestType]);

    const prepareUserRequests = async () => {
        let data = await RequestService.getRequestsByUserId(localStorage.getItem("userId"));
        if (requestType === "ALL") {
            setRequests(data)
        } else {
            setRequests(data.filter((request) => (
                request.type === requestType
            )))
        }
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