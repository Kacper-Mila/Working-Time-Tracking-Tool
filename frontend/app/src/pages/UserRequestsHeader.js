import RequestTypeButton from "../components/buttons/RequestTypeButton";
import './UserRequestsHeader.css';

export default function UserRequestsHeader() {
    return (
        <div className="header">
            <RequestTypeButton color={'rgb(22 163 74)'}
                               text={'Add New Request'}
            />
            <RequestTypeButton color={'rgb(8 145 178)'}
                               text={'Holiday Requests'}
            />
            <RequestTypeButton color={'rgb(244 63 94)'}
                               text={'Overtime Requests'}
            />
            <RequestTypeButton color={'rgb(250 204 21)'}
                               text={'Home Office Requests'}
            />
        </div>
    )
}