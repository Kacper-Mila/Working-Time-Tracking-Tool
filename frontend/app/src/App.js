import './App.css';
import {Route, Routes} from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/mainPage/MainPage";

export default function App() {

    return (
        <div className="App">
                <Routes>
                    <Route exact path="/login" element={<LoginPage/>}/>
                    {/*<Route exact path="/logout" element={<LogoutPage/>}/>*/}
                    <Route exact path="*" element={<MainPage/>}/>
                </Routes>
        </div>
    );
}