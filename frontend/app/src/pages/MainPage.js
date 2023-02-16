import User from "../components/User";
import AppNavbar from "../components/navbar/AppNavbar";
import {Route, Routes} from "react-router-dom";
import RequestsPage from "./RequestsPage";
import Calendar from "./Calendar";
import AddRequest from "../components/user_requests/AddRequest";

export default function MainPage() {
    return (
    <div>
        <AppNavbar />
        <div className='container-fluid'>
            <div className='row'>
                <div className='col-lg-3 col-md-3 col-sm-12 border' id='user-left'>
                    <User />
                </div>

                <div className='col-lg-9 col-md-9 col-sm-12 border' id='pages'>
                    <Routes>
                        <Route path='/' element={<Calendar/>}></Route>
                        <Route path='/requests' element={<RequestsPage/>}></Route>
                        <Route path='/add-request' element={<AddRequest />}></Route>
                    </Routes>
                </div>
            </div>
        </div>
    </div>
    )
}