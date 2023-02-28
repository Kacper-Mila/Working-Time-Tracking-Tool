import PropTypes from "prop-types";
import '../userRequests/request.css';
import './requestTypeButton.css';

export default function RequestTypeButton ({text}) {
    return (
        <button
            className='btn mr-4 request-button'>
            {text}
        </button>
    );
}

RequestTypeButton.propTypes = {
    text: PropTypes.string,
    onClick: PropTypes.func
}