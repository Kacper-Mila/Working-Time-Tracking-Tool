import RequestTypeButton from "../components/buttons/RequestTypeButton";
import './UserRequestsHeader.css';
import {Link} from "react-router-dom";

export default function UserRequestsHeader() {
    return (
        <div className="header">
            <Link to="/add-request">
                <RequestTypeButton color={'green'}
                                   text={'Add New Request'}
                />
            </Link>
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