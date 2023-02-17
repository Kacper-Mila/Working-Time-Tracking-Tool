import {FaTimes} from "react-icons/fa";
import './request.css';

export default function Request(props) {
    return(
        <div className='request'>
            <span className='text-muted'>
                <i>{props.requestRegistrationDate}</i></span>
            <h3 className=''>
                {props.requestId}: {props.requestType}
                {/*<FaTimes*/}
                {/*    style={{color: 'green', cursor: 'pointer'}}*/}
                {/*    onClick={() => props.onEdit(props.id)}*/}
                {/*/>*/}

            </h3>
            <span><i>Comment:</i></span>
            <p className='comment'>{props.requestComment}</p>
            <span><i>When:</i></span>
            <p>{props.requestStartDate} - {props.requestEndDate}</p>
            <FaTimes
                style={{color: 'red', cursor: 'pointer'}}
                onClick={() => {
                    alert("Do you want to delete this item?")
                    props.onDelete(props.requestId)}
                }
            />
        </div>
    );
}