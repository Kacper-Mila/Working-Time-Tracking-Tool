import {FaRegEdit, FaTrashAlt} from "react-icons/fa";
import './request.css';
import {Modal, ModalBody, ModalHeader, ModalTitle} from "react-bootstrap";
import EditRequest from "../userRequests/EditRequest";
import {useState} from "react";

export default function Request(props) {

    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    return (
        <div className='request'>
            <div className='main-request'>
                <span className='text-muted'>
                <i>{props.requestRegistrationDate}</i></span>
                <h3 className=''>
                    {props.requestId}: {props.requestType}
                </h3>
                <span><i>Status:</i></span>
                <p className='comment'>{props.requestStatus}</p>
                <span><i>Comment:</i></span>
                <p className='comment'>{props.requestComment}</p>
                <span><i>When:</i></span>
                <p>{props.requestStartDate} - {props.requestEndDate}</p>

            </div>
            <div className='request-buttons'>
                <FaRegEdit
                    className="edit-button"
                    size={18}
                    onClick={() => {
                        if (props.requestStatus !== "PENDING") {
                            alert("You cannot edit accepted or declined request.")
                        } else {
                            handleShow();
                        }
                    }}
                />
                <FaTrashAlt
                    className='delete-button'
                    size={15}
                    onClick={() => {
                        if (props.requestStatus === "PENDING") {
                            if (window.confirm("Do you want to delete this request?")) {
                                props.onDelete(props.requestId)
                            }
                        } else {
                            alert("You cannot delete accepted or declined request.")
                        }
                    }
                    }
                />
                <Modal show={show}>
                    <ModalHeader>
                        <ModalTitle>
                            Edit request
                        </ModalTitle>
                    </ModalHeader>
                    <ModalBody>
                        <EditRequest ownerId={props.ownerId} requestId={props.requestId} onCancel={handleClose}/>
                    </ModalBody>
                </Modal>
            </div>
        </div>
    );
}