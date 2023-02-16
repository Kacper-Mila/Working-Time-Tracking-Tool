import {FaTimes} from "react-icons/fa";

export default function Request(props) {
    return(
        <div className='requests'>
            <h3>
                {props.requestType}
                {/*<FaTimes*/}
                {/*    style={{color: 'green', cursor: 'pointer'}}*/}
                {/*    onClick={() => props.onEdit(props.id)}*/}
                {/*/>*/}
                <FaTimes
                    style={{color: 'red', cursor: 'pointer'}}
                    onClick={() => props.onDelete(props.requestId)}
                />
            </h3>
            <p>{props.requestStartDate} - {props.requestEndDate}</p>
        </div>
    );
}