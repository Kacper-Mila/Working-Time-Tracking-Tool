import RequestTypeButton from "../components/buttons/RequestTypeButton";
import './UserRequestsHeader.css';

export default function UserRequestsHeader() {
    return (
        <div className="header">
            <RequestTypeButton color={'green'}
                               text={'Add New Request'}
            />
            <RequestTypeButton color={'blue'}
                               text={'Holiday RequestsPage'}
            />
            <RequestTypeButton color={'red'}
                               text={'Overtime RequestsPage'}
            />
            <RequestTypeButton color={'yellow'}
                               text={'Home Office RequestsPage'}
            />
        </div>
    )
}