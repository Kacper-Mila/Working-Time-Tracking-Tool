import RequestService from "../../serviceHubs/request-service-hub";
import {useEffect, useState} from "react";
import EmployeeRequest from "../../components/employeeRequests/EmployeeRequest";

export default function EmployeesRequests(){
    const [employeesRequests, setEmployeesRequests] = useState([]);

    useEffect(() => {
        document.body.style.overflow = "hidden";
        prepareEmployeesRequests()
            .then(() => {
                console.log("employees requests loaded successfully")
            }).catch(err => {
            console.error("error", err);
        });

    }, []);

    const prepareEmployeesRequests = async () => {
        let data = await RequestService.getEmployeesRequestsByManagerId(localStorage.getItem("userId"));
        setEmployeesRequests(data);
    }
    return(
        <div>
            {employeesRequests.map((request) => (
                <EmployeeRequest key={request.id}
                         requestId={request.id}
                         requestType={request.type}
                         requestStartDate={request.startDate}
                         requestEndDate={request.endDate}
                         requestRegistrationDate={request.registrationDate}
                         requestComment={request.comment}
                         ownerId={request.ownerId}
                         requestStatus={request.status}
                />
            ))}
        </div>
    )
}