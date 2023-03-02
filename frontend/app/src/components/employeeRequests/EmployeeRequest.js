import RequestService from "../../serviceHubs/request-service-hub";
import {FaCheck, FaTimes} from "react-icons/fa";

export default function EmployeeRequest(props) {

    const acceptOrRejectRequest = async (requestId, requestStatus) => {
        await RequestService.acceptOrRejectEmployeeRequest({requestId, requestStatus})
        window.location.reload()
    }
    return (
        <div className='request'>
            <div className='main-request'>
                <span className='text-muted'>
                <i>{props.requestRegistrationDate}</i></span>
                <h3 className=''>
                    {props.requestId}: {props.requestType}
                </h3>
                <span><i>Status:</i></span>
                <p className='comment'>{props.requestStatus}</p>
                <span><i>Owner:</i></span>
                <p className='comment'>{props.ownerId}</p>
                <span><i>Comment:</i></span>
                <p className='comment'>{props.requestComment}</p>
                <span><i>When:</i></span>
                <p>{props.requestStartDate} - {props.requestEndDate}</p>

            </div>
            <div className='request-buttons'>
                <FaCheck
                    className='accept-button'
                    size={18}
                    onClick={() => {
                        acceptOrRejectRequest(props.requestId, "ACCEPTED")
                            .then()
                    }}
                />

                <FaTimes
                    className='reject-button'
                    size={15}
                    onClick={() => {
                        acceptOrRejectRequest(props.requestId, "DECLINED").then()
                    }}
                />
            </div>
        </div>
    );
}