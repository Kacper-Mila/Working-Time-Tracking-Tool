import {useEffect, useState} from "react";
import RequestService from "../../serviceHubs/request-service-hub";
import Request from "../../components/userRequests/Request";
import AddRequest from "../../components/userRequests/AddRequest";

export default function UserRequests () {
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
        setRequests();
    }

    const deleteRequest = async (id) => {
        await RequestService.deleteRequest(id);
        await prepareUserRequests();
    }

    // const addRequest = async (request) => {
    //
    //     setRequests([...requests, request]);
    // }

    // TODO clean using addRequest routing from request-service-hub
    const addRequest = async (request) => {
        const res = await fetch('http://localhost:8080/api/v1/requests', {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(request)
        })

        const data = await res.json()

        setRequests([...requests, data])
    }

    return (
        <>
            {requests.map((request) =>(
                <Request key={request.id}
                         requestId={request.id}
                         requestType = {request.type}
                         requestStartDate = {request.startDate}
                         requestEndDate = {request.endDate}
                         requestRegistrationDate ={request.registrationDate}
                         requestComment = {request.comment}
                         // onEdit={RequestService.updateRequest(request.id)}
                         onDelete={deleteRequest}
                         ownerId = {request.ownerId}
                />
            ))}
        </>
    );
}