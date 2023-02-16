import RequestTypeButton from "../components/buttons/RequestTypeButton";
import './UserRequestsHeader.css';

export default function UserRequestsHeader() {
    return (
        <div className="header">
            <RequestTypeButton text={'Add New Request'}/>
            <RequestTypeButton text={'Holiday Requests'}/>
            <RequestTypeButton text={'Overtime Requests'}/>
            <RequestTypeButton text={'Home Office Requests'}/>
        </div>
    )
}