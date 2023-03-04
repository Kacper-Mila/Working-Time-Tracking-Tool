import './App.css';
import {Route, Routes} from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/mainPage/MainPage";
import AuthRoute from "./serviceHubs/auth-route";

export default function App() {

    return (
        <div className="App">
                <Routes>
                    <Route path="/login" exact element={<LoginPage/>}/>
                    {/*<Route exact path="/logout" element={<LogoutPage/>}/>*/}
                    <Route path="*" exact element={<MainPage/>}/>
                </Routes>
        </div>
    );
}