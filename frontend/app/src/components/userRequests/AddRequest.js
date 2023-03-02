import {useState} from "react";
import axios from 'axios';
import './addRequest.css'

export default function AddRequest(props) {
    const [type, setType] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [comment, setComment] = useState('');
    const [ownerId] = useState(localStorage.getItem("userId"));
    let currentDate = new Date().toISOString().slice(0, 10);

    const onSubmit = async (e) => {
        e.preventDefault()

        await axios.post('http://localhost:8080/api/v1/requests', {
            ownerId: ownerId,
            type: type,
            comment: comment,
            startDate: startDate,
            endDate: endDate,
            approvalDate: "2023-05-01",
        }).catch(err => {
            let response = JSON.parse(err.request.response);
            alert(response.message);
        });

        props.onCancel();
        window.location.reload();
    }

    return (
        <form className='add-form' onSubmit={onSubmit}>
            <div className='form-control'>
                <label className="description">Request Type: </label>
                <select value={type} onChange={(e) => setType(e.target.value)} required style={{cursor: "pointer"}}>
                    <option value='' selected>SELECT</option>
                    <option>HOLIDAY</option>
                    <option>OVERTIME</option>
                    <option>REMOTE</option>
                </select>

            </div>
            <div className='form-control'>
                <label className="description">Start Date: </label>
                <input type='date'
                       min={currentDate}
                       value={startDate}
                       onChange={(e) => setStartDate(e.target.value)}
                       required
                />
            </div>

            <div className='form-control'>
                <label className="description">End Date: </label>
                <input type='date'
                       min={currentDate}
                       value={endDate}
                       onChange={(e) => setEndDate(e.target.value)}
                       required
                />
            </div>

            <div className='form-control'>
                <label className="description">Comment: </label>
                <input type='textarea'
                       className='text-area'
                       placeholder="comment"
                       value={comment}
                       onChange={(e) => setComment(e.target.value)}
                />
            </div>
            <div className='d-flex align-items-center justify-content-center mt-2'>
                <button type='submit' className='btn btn-outline-success mx-2'>Add request</button>
                <button type='reset' className='btn btn-outline-danger mx-2' onClick={props.onCancel}>Cancel</button>
            </div>
        </form>
    )
}