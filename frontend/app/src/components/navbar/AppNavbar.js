import Navbar from 'react-bootstrap/Navbar';
import {Link, Route, Routes} from "react-router-dom";
import './NavbarLink.css';
import {Nav} from "react-bootstrap";
import RequestTypeButton from "../buttons/RequestTypeButton";
import UserRequestsHeader from "../../pages/UserRequestsHeader";

export default function AppNavbar() {
    return (
        <>
            <Navbar className='navbar'>
                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/">My Calendar</Nav.Link>
                        <Nav.Link as={Link} to="/requests">My Requests</Nav.Link>
                    </Nav>
                {/*<div>*/}
                {/*    <Routes>*/}
                {/*        <Route path='/requests' element={<UserRequestsHeader/>}></Route>*/}
                {/*    </Routes>*/}
                {/*</div>*/}
            </Navbar>
        </>
    );
}