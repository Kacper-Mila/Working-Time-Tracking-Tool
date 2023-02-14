import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Calendar from "./pages/Calendar";
import Requests from "./pages/Requests";
import Navbar from "./components/navbar/AppNavbar";

export default function App() {
  return (
    <div>
            <Navbar />
        <div className='container-fluid'>
            <div className='row'>
                <div className='col-lg-3 col-md-3 col-sm-12 border' id='user-left'>
                    Tu maniane odjebie Ania
                </div>

                <div className='col-lg-9 col-md-9 col-sm-12 border' id='pages'>
                    <BrowserRouter>
                        <Routes>
                            <Route path='/' element={<Calendar/>}></Route>
                            <Route path='/requests' element={<Requests/>}></Route>
                        </Routes>
                    </BrowserRouter>
                </div>
            </div>
        </div>
    </div>
  );
}