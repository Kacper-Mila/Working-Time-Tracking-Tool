import Navbar from 'react-bootstrap/Navbar';
import {Link} from "react-router-dom";
import './NavbarLink.css';
import {Nav} from "react-bootstrap";

function AppNavbar() {
    return (
        <>
            <Navbar bg="dark" variant="dark">
                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/">My Calendar</Nav.Link>
                        <Nav.Link as={Link} to="/requests">My Requests</Nav.Link>
                    </Nav>
            </Navbar>
        </>
    );
}

export default AppNavbar;