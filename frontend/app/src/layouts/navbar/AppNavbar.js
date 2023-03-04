import Navbar from 'react-bootstrap/Navbar';
import {Link} from "react-router-dom";
import './AppNavbar.css';
import {Nav} from "react-bootstrap";

export default function AppNavbar() {
    return (
            <Navbar className='navbar'>
                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/" className='navbar-link'>My Calendar</Nav.Link>
                        <Nav.Link as={Link} to="/requests" className='navbar-link'>My Requests</Nav.Link>
                        <Nav.Link as={Link} to="/employees-requests" className='navbar-link'>My Employees Requests</Nav.Link>
                        <Nav.Link as={Link} to="/admin-panel" className='navbar-link'>Admin Panel</Nav.Link>
                    </Nav>
            </Navbar>
    );
}