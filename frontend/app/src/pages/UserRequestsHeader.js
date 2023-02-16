import RequestTypeButton from "../components/buttons/RequestTypeButton";
import './UserRequestsHeader.css';

export default function UserRequestsHeader() {
    return (
        <div className="header">
            <RequestTypeButton color={'green'}
                               text={'Add New Request'}
            />
            <RequestTypeButton color={'blue'}
                               text={'Holiday Requests'}
            />
            <RequestTypeButton color={'red'}
                               text={'Overtime Requests'}
            />
            <RequestTypeButton color={'yellow'}
                               text={'Home Office Requests'}
            />
        </div>
    )
}