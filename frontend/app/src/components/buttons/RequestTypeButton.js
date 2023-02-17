import PropTypes from "prop-types";
import '../user_requests/request.css';

export default function RequestTypeButton ({text}) {
    return (
        <button
            style={{backgroundColor:"whitesmoke"}}
            className='btn mr-4 request-button'>
            {text}
        </button>
    );
}

RequestTypeButton.propTypes = {
    text: PropTypes.string,
    onClick: PropTypes.func
}