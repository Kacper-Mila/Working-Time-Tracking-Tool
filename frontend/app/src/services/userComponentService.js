import axios from "axios";

const API_URL = "http://localhost:8080/api/v1/users";


const getAllUsers = async () => {
    return axios.get(API_URL).then(r => r.data);
}

const getUserById = async () => {
    return axios.get(API_URL + `/userid?userId=user4id`).then(r => r.data);
}

const UserService = {
    getUserById,
    getAllUsers
}

export default UserService;