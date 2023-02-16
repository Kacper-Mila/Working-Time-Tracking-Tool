import User from "../components/User";
import {Route, Routes} from "react-router-dom";
import AppNavbar from "../components/navbar/AppNavbar";
import Calendar from "../components/Calendar";
import Requests from "./Requests";

export default function MainPage() {
    return (
        <div>
            <AppNavbar/>
            <div className='container-fluid'>
                <div className='row'>
                    <div className='col-lg-3 col-md-3 col-sm-12 border' id='user-left'>
                        <User/>
                    </div>

                    <div className='col-lg-9 col-md-9 col-sm-12 border' id='pages'>
                        <Routes>
                            <Route path='/' element={<Calendar/>}></Route>
                            <Route path='/requests' element={<Requests/>}></Route>
                        </Routes>
                    </div>
                </div>
            </div>
        </div>
    )
}