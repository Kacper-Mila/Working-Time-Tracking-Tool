import './App.css';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/MainPage";

export default function App() {

    return (
        <div className="App">
            <Router>
                <Routes>
                    <Route exact path="/" element={<MainPage/>}/>
                    <Route exact path="/login" element={<LoginPage/>}/>
                </Routes>
            </Router>
        </div>
    );
}