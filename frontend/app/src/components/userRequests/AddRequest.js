import {useState} from "react";
import axios from 'axios';
import {useNavigate} from "react-router-dom";

export default function AddRequest() {
    const [type, setType] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [comment, setComment] = useState('');
    const [ownerId, setOwnerId] = useState(localStorage.getItem("userId"));
    const navigate = useNavigate();

    const onSubmit = async (e) => {
        e.preventDefault()

        if (!type || !startDate || !endDate) {
            alert('Please select all of the options')
            return
        }

        await axios.post('http://localhost:8080/api/v1/requests', {
            ownerId: ownerId,
            type: type,
            comment: comment,
            registrationDate: "2023-02-17",
            startDate: startDate,
            endDate: endDate,
            approvalDate: "2023-05-01",
            status: "PENDING"
        });

        navigate('/requests');

        // props.onAdd({ownerId, type, startDate, endDate})

        // RequestService.addRequest(JSON.stringify({ownerId: ownerId, type: type, comment: 'comment', registrationDate: startDate, startDate: startDate, endDate: endDate, approvalDate: endDate, status: "PENDING"}))
        //     .then(r => r.data)
        //     .then(r => {console.log("Request added successfully", r)}).catch((error) => {
        //     console.log("Adding request failed!", error);
        // });
    }

    return (
        <form className='add-form' onSubmit={onSubmit}>
            <div className='form-controdel'>
                <label className='text-light'>Request Type: </label>
                <select value={type} onChange={(e) => setType(e.target.value)}>
                    <option>-----------</option>
                    <option>HOLIDAY</option>
                    <option>OVERTIME</option>
                    <option>REMOTE</option>
                </select>

            </div>
            <div className='form-control'>
                <label>Start Date: </label>
                <input type='date'
                       value={startDate}
                       onChange={(e) => setStartDate(e.target.value)}
                />
            </div>

            <div className='form-control'>
                <label>End Date: </label>
                <input type='date'
                       value={endDate}
                       onChange={(e) => setEndDate(e.target.value)}
                />
            </div>

            <div className='form-control'>
                <label>Comment: </label>
                <input type='textarea'
                       value={comment}
                       onChange={(e) => setComment(e.target.value)}
                />
                {/*<textarea className='form-control'*/}
                {/*    cols="30"*/}
                {/*          rows="3"*/}
                {/*          placeholder='comment'*/}
                {/*          value={comment}*/}
                {/*          onChange={(e) => setComment(e.target.value)}>*/}
                {/*</textarea>*/}
            </div>
            <div className='d-flex align-items-center justify-content-center mt-2'>
                <button type='submit' className='btn bg-light'>Add request</button>
            </div>
        </form>
    )
}