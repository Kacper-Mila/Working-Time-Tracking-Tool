import {FaRegEdit, FaTimes} from "react-icons/fa";
import './request.css';
import {Modal, ModalBody, ModalHeader, ModalTitle} from "react-bootstrap";
import EditRequest from "./EditRequest";
import {useState} from "react";

export default function Request(props) {
    const [show, setShow] = useState(false);

    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    return (
        <div className='request'>
            <span className='text-muted'>
                <i>{props.requestRegistrationDate}</i></span>
            <h3 className=''>
                {props.requestId}: {props.requestType}

            </h3>
            <span><i>Comment:</i></span>
            <p className='comment'>{props.requestComment}</p>
            <span><i>When:</i></span>
            <p>{props.requestStartDate} - {props.requestEndDate}</p>
            <FaTimes
                style={{color: 'red', cursor: 'pointer'}}
                onClick={() => {
                    alert("Do you want to delete this item?")
                    props.onDelete(props.requestId)
                }
                }
            />
            <FaRegEdit
                style={{cursor: 'pointer'}}
                onClick={handleShow}
            />

            <Modal show={show}>
                <ModalHeader closeButton>
                    <ModalTitle>
                        Edit request
                    </ModalTitle>
                </ModalHeader>
                <ModalBody>
                    <EditRequest ownerId={props.ownerId} requestId={props.requestId} onEdit={handleClose}/>
                </ModalBody>
            </Modal>
        </div>
    );
}