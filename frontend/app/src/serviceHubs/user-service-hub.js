import axios from "axios";
import AuthHeader from "./auth-header";

const API = axios.create({
    baseURL: `http://localhost:8080/api/v1/users`
})

const getAllUsers = async () => {
    return API.get('', { headers: AuthHeader() }).then(r => r.data)
}

const addUser = async (userJson) => {
    return API.post('', userJson, { headers: AuthHeader() })
}

const deleteUser = async (userId) => {
    return API.delete(`/delete?userId=${userId}`, { headers: AuthHeader() })
}

const getAllUsersByManager = async (managerId) => {
    return API.get(`/manager?managerId=${managerId}`, { headers: AuthHeader() }).then(r => r.data)
}

const getUserByUserId = async (userId) => {
    return API.get(`/userId?userId=${userId}`, { headers: AuthHeader() }).then(r => r.data)
}

const UserService = {
    getAllUsers,
    addUser, // @RequestBody User user
    deleteUser, // /delete @RequestParam String userId
    getAllUsersByManager, // /manager @RequestParam String managerId
    getUserByUserId // /userId @RequestParam String userId
}

export default UserService