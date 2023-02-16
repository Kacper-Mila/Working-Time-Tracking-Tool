import PropTypes from "prop-types";
import '../user_requests/request.css';

export default function RequestTypeButton ({text}) {
    return (
        <button
            style={{backgroundColor:"#DBDFFE"}}
            className='req-button btn mr-4 btn-secondary text-dark'>
            {text}
        </button>
    );
}

RequestTypeButton.propTypes = {
    text: PropTypes.string,
    onClick: PropTypes.func
}