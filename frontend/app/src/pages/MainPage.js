import User from "../components/User";
import {Route, Routes} from "react-router-dom";
import AppNavbar from "../components/navbar/AppNavbar";
import Calendar from "../components/Calendar";
import RequestsPage from "./RequestsPage";

export default function MainPage() {
    return (
        <div>
            <div className='container-fluid'>
                <div className='row'>
                    <div className='' id='user-left'>
                        <User/>
                    </div>
                    <div className="col">
                        <AppNavbar/>
                        <div className='' id='pages'>
                            <Routes>
                                <Route path='/' element={<Calendar/>}></Route>
                                <Route path='/requests' element={<RequestsPage/>}></Route>
                            </Routes>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}