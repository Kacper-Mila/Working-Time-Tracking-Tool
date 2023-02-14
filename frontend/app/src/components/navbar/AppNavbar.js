import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';

function AppNavbar() {
    return (
        <Navbar className='bg-white' expand="lg">
                <Navbar.Brand href="#home" className='bg-light'>Working Time Tracking Tool</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav" className="d-flex bd-highlight">
                        <Nav.Link href="/" style={{color: 'black'}} className='border bg-light flex-grow-1 bd-highlight'>My Calendar</Nav.Link>
                        <Nav.Link href="/requests" style={{color: 'black'}} className='border bg-light flex-grow-1 bd-highlight'>My Requests</Nav.Link>
                        <NavDropdown title="My Account" style={{color: 'black'}} id="basic-nav-dropdown" className='border bg-light ml-auto flex-grow-1 bd-highlight'>
                            <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
                            <NavDropdown.Item href="#action/3.2">
                                Another action
                            </NavDropdown.Item>
                            <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="#action/3.4">
                                Separated link
                            </NavDropdown.Item>
                        </NavDropdown>
                </Navbar.Collapse>
        </Navbar>
    );
}

export default AppNavbar;