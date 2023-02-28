import RequestTypeButton from "../../components/buttons/RequestTypeButton";
import './UserRequestsHeader.css';
import {Link} from "react-router-dom";
import {Button} from "react-bootstrap";

export default function UserRequestsHeader({setSelectedRequestType}) {
    return (
        <div className="header">
            <Link to="/add-request">
            <RequestTypeButton text={'Add New Request'}/>
            </Link>
                <button className='btn mr-4 request-button' onClick={() => setSelectedRequestType('HOLIDAY')}>Holiday Requests</button>
                <button className='btn mr-4 request-button' onClick={() => setSelectedRequestType('OVERTIME')}>Overtime Requests</button>
                <button className='btn mr-4 request-button' onClick={() => setSelectedRequestType('REMOTE')}>Home Office Requests</button>
                <button className='btn mr-4 request-button' onClick={() => setSelectedRequestType('ALL')}>All Requests</button>
        </div>
    )
}