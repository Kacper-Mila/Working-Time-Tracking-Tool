import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Calendar from "./pages/Calendar";
import Requests from "./pages/Requests";

export default function App() {
  return (
    <div>
        <div className='border' id='navigation'>
            tak
        </div>

        <div className='container row border'>
          <div className='col column col-3 border' id='user-left'>
            tak
          </div>

            <div className='col column col-lg-9 border' id='pages'>
                <BrowserRouter>
                    <Routes>
                        <Route path='/' element={<Calendar/>}></Route>
                        <Route path='/requests' element={<Requests/>}></Route>
                    </Routes>
                </BrowserRouter>
            </div>
        </div>
    </div>
  );
}