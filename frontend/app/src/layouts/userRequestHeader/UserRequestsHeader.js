import RequestTypeButton from "../../components/buttons/RequestTypeButton";
import './UserRequestsHeader.css';
import {Link} from "react-router-dom";

export default function UserRequestsHeader() {
    return (
        <div className="header">
            <Link to="/add-request">
            <RequestTypeButton text={'Add New Request'}/>
            </Link>
            <RequestTypeButton text={'Holiday Requests'}/>
            <RequestTypeButton text={'Overtime Requests'}/>
            <RequestTypeButton text={'Home Office Requests'}/>
        </div>
    )
}