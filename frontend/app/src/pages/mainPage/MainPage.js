import User from "../../layouts/user/User";
import {Route, Routes} from "react-router-dom";
import AppNavbar from "../../layouts/navbar/AppNavbar";
import Calendar from "../../components/calendar/Calendar";
import RequestsPage from "../requestPage/RequestsPage";
import AddRequest from "../../components/userRequests/AddRequest";
import './mainPage.css';
import EmployeesRequestsPage from "../employeesRequestPage/EmployeesRequestsPage";



export default function MainPage() {
    return (
        <div>
            <div className='container-fluid'>
                <div className='row'>
                    <div className='' id='user-left'>
                        <User/>
                    </div>
                    <div className="main-container">
                        <div className='wrapper'>
                            <div>
                                <AppNavbar/>
                            </div>
                            <div className='page-container'> {/*id='pages'>*/}
                                <Routes>
                                    <Route path='/' element={<Calendar/>}></Route>
                                    <Route path='/requests' element={<RequestsPage/>}></Route>
                                    <Route path='/add-request' element={<AddRequest />}></Route>
                                    <Route path='/employees-requests' element={<EmployeesRequestsPage/>}></Route>
                                </Routes>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}