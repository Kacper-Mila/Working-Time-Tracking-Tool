import axios from "axios";
import authHeader from "./auth-header";

const API = axios.create({
    baseURL: `http://localhost:8080/api/v1/users`
})

let postConf = {
    headers: {
        'Content-Type': 'application/json',
        'Authorization': authHeader()
    },
}

let authConf = {
    headers: authHeader()
}

const getAllUsers = async () => {
    return API.get('', authConf).then(r => r.data)
}

const addUser = async (userJson) => {
    return API.post('', userJson, postConf)
}

const deleteUser = async (userId) => {
    return API.delete(`/delete?userId=${userId}`,)
}

const getAllUsersByManager = async (managerId) => {
    return API.get(`/manager?managerId=${managerId}`).then(r => r.data)
}

const getUserByUserId = async (userId) => {
    return API.get(`/userId?userId=${userId}`).then(r => r.data)
}

const UserService = {
    getAllUsers,
    addUser, // @RequestBody User user
    deleteUser, // /delete @RequestParam String userId
    getAllUsersByManager, // /manager @RequestParam String managerId
    getUserByUserId // /userId @RequestParam String userId
}

export default UserService