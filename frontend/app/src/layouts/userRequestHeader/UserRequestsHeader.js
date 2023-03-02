import './UserRequestsHeader.css';
import {useState} from "react";
import {Modal, ModalBody, ModalHeader, ModalTitle} from "react-bootstrap";
import AddRequest from "../../components/userRequests/AddRequest";

export default function UserRequestsHeader({setSelectedRequestType}) {
    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    return (
        <div>
            <div className="header">
                <button className='btn mr-4 request-button' onClick={handleShow}>Add New Request
                </button>
                <button className='btn mr-4 request-button' onClick={() => setSelectedRequestType('HOLIDAY')}>Holiday
                    Requests
                </button>
                <button className='btn mr-4 request-button' onClick={() => setSelectedRequestType('OVERTIME')}>Overtime
                    Requests
                </button>
                <button className='btn mr-4 request-button' onClick={() => setSelectedRequestType('REMOTE')}>Home Office
                    Requests
                </button>
                <button className='btn mr-4 request-button' onClick={() => setSelectedRequestType('ALL')}>All Requests
                </button>
            </div>

            <Modal show={show}>
                <ModalHeader>
                    <ModalTitle>
                        Add new request
                    </ModalTitle>
                </ModalHeader>
                <ModalBody>
                    <AddRequest onCancel={handleClose}/>
                </ModalBody>
            </Modal>
        </div>
    )
}