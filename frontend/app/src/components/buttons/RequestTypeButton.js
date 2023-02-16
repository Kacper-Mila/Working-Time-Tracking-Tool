import PropTypes from "prop-types";

export default function RequestTypeButton ({color, text}) {
    return (
        <button
            style={{backgroundColor: color}}
            className={'btn m-2'}>
            {text}
        </button>
    );
}

RequestTypeButton.defaultProps = {
    color: 'blue'
}

RequestTypeButton.propTypes = {
    text: PropTypes.string,
    color: PropTypes.string,
    onClick: PropTypes.func
}