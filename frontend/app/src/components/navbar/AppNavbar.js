import Navbar from 'react-bootstrap/Navbar';
import {Link} from "react-router-dom";
import './NavbarLink.css';
import {Nav} from "react-bootstrap";
import logo from '../../images/working-time-tracking-tool-logo.png';

function AppNavbar() {
    return (
        <>
            <Navbar className='navbar'>
                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/">My Calendar</Nav.Link>
                        <Nav.Link as={Link} to="/requests">My Requests</Nav.Link>
                    </Nav>
            </Navbar>
        </>
    );
}

export default AppNavbar;