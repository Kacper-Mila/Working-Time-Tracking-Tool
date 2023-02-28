import {useState} from "react";
import axios from "axios";

export default function EditRequest(props) {
    const [type, setType] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [comment, setComment] = useState('');
    let currentDate = new Date().toISOString().slice(0, 10);

    const onSubmit = async (e) => {
        e.preventDefault()

        if (!type || !startDate || !endDate) {
            alert('Please select all of the options')
            return
        }

        await axios.patch(`http://localhost:8080/api/v1/requests/update?userId=${props.ownerId}&requestId=${props.requestId}`, {
            type: type,
            comment: comment,
            startDate: startDate,
            endDate: endDate
        });

        props.onEdit();
    }

    return (
        <form className='edit-form'>
            <div>
                <select value={type} onChange={(e) => setType(e.target.value)}>
                    <option>-----------</option>
                    <option>HOLIDAY</option>
                    <option>OVERTIME</option>
                    <option>REMOTE</option>
                </select>
            </div>

            <div>
                <label>Start Date: </label>
                <input type='date'
                       min={currentDate}
                       value={startDate}
                       onChange={(e) => setStartDate(e.target.value)}
                />
            </div>

            <div>
                <label>End Date: </label>
                <input type='date'
                       min={currentDate}
                       value={endDate}
                       onChange={(e) => setEndDate(e.target.value)}
                />
            </div>

            <div>
                <label>Comment: </label>
                <input type='textarea'
                       value={comment}
                       onChange={(e) => setComment(e.target.value)}
                />
            </div>
            <div className='d-flex align-items-center justify-content-center mt-2'>
                <button type='submit' className='btn bg-light mr-2' onClick={onSubmit}>Save</button>
                <button type='submit' className='btn bg-light' onClick={props.onEdit}>Cancel</button>
            </div>
        </form>
    )
}