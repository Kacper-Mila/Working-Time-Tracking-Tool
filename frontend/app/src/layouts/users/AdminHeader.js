import {useNavigate} from "react-router-dom";

export default function AdminHeader(){

    const navigate = useNavigate();

    return(
        <div>
            <div className="header">
                <button className='btn mr-4 request-button' onClick={() => navigate('/add-user')}>Add New User
                </button>
                <button className='btn mr-4 request-button'> All Users
                </button>
                <button className='btn mr-4 request-button'> Employees
                </button>
                <button className='btn mr-4 request-button'> Managers
                </button>
                <button className='btn mr-4 request-button'> Admins
                </button>
            </div>
        </div>
    )

}