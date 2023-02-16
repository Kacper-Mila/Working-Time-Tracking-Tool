import './App.css';
import {Route, Routes} from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/MainPage";

export default function App() {

    return (
        <div className="App">
                <Routes>
                    <Route exact path="/" element={<MainPage/>}/>
                    <Route exact path="/login" element={<LoginPage/>}/>
                </Routes>
        </div>
    );
}
//
// return (
//     <div>
//         <Navbar />
//         <div className='container-fluid'>
//             <div className='row'>
//                 <div className='col-lg-3 col-md-3 col-sm-12 border' id='user-left'>
//                     User Details
//                 </div>
//
//                 <div className='col-lg-9 col-md-9 col-sm-12 border' id='pages'>
//                     <Routes>
//                         <Route path='/' element={<Calendar/>}></Route>
//                         <Route path='/requests' element={<Requests/>}></Route>
//                     </Routes>
//                 </div>
//             </div>
//         </div>
//     </div>
// );