import {Modal, ModalBody, ModalHeader, ModalTitle} from "react-bootstrap";
import {useState} from "react";
import AddUserForm from "./AddUserForm";

export default function AdminHeader({setSelectedUserType}){
    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);


    return(
        <div>
            <div className="header">
                <button className='btn mr-4 request-button' onClick={handleShow}>Add new user
                </button>
                <button className='btn mr-4 request-button' onClick={() => setSelectedUserType('ALL')}> All Users
                </button>
                <button className='btn mr-4 request-button' onClick={() => setSelectedUserType('EMPLOYEE')}> Employees
                </button>
                <button className='btn mr-4 request-button' onClick={() => setSelectedUserType('MANAGER')}> Managers
                </button>
                <button className='btn mr-4 request-button' onClick={() => setSelectedUserType('ADMIN')}> Admins
                </button>
            </div>

            <Modal show={show}>
                <ModalHeader>
                    <ModalTitle>
                        Add new user
                    </ModalTitle>
                </ModalHeader>
                <ModalBody>
                    <AddUserForm onCancel={handleClose}/>
                </ModalBody>
            </Modal>
        </div>
    )

}