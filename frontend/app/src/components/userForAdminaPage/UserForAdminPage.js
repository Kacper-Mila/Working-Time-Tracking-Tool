import {FaRegEdit, FaTrashAlt} from "react-icons/fa";
import EditUser from "../../layouts/users/EditUser";
import {Modal, ModalBody, ModalHeader, ModalTitle} from "react-bootstrap";
import {useState} from "react";

export default function UserForAdminPage(props) {
    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);
    return (
        <div className='request'>
            <div className='main-request'>
                <h3 className=''>
                    {props.firstName} {props.lastName}
                </h3>
                <span><i>Email:</i></span>
                <p className='comment'>{props.email}</p>
                <span><i>User ID:</i></span>
                <p className='comment'>{props.userId}</p>
                <span><i>User Type:</i></span>
                <p className='comment'>{props.userType}</p>
                <span><i>Manager ID:</i></span>
                <p className='comment'>{props.managerId}</p>
                <span><i>Team ID:</i></span>
                <p className='comment'>{props.teamId}</p>
            </div>

            <div className='request-buttons'>
                <FaRegEdit
                    className="edit-button"
                    size={18}
                    onClick={handleShow}
                />
                <FaTrashAlt
                    className='delete-button'
                    size={15}
                    onClick={() => {
                        if (window.confirm("Do you want to delete this user?")) {
                            props.onDelete(props.userId)
                        }
                    }
                    }
                />
            </div>

            <Modal show={show} size='xl'>
                <ModalHeader>
                    <ModalTitle>
                        Edit user
                    </ModalTitle>
                </ModalHeader>
                <ModalBody>
                    <EditUser userId={props.userId} onCancel={handleClose}/>
                </ModalBody>
            </Modal>
        </div>
    )
}