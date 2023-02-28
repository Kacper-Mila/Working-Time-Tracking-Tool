import axios from "axios";

const api = axios.create({
    baseURL: `http://localhost:8080/api/v1/users`
})

let postConf = {
    headers: {
        'Content-Type': 'application/json'
    }
}

const getAllUsers = async () => {
    return api.get('').then(r => r.data)
}

const addUser = async (userJson) => {
    return api.post('', userJson, postConf)
}

const deleteUser = async (userId) => {
    return api.delete(`/delete?userId=${userId}`)
}

const getAllUsersByManager = async (managerId) => {
    return api.get(`/manager?managerId=${managerId}`).then(r => r.data)
}

const getUserByUserId = async (userId) => {
    return api.get(`/userId?userId=${userId}`).then(r => r.data)
}

const UserService = {
    getAllUsers,
    addUser, // @RequestBody User user
    deleteUser, // /delete @RequestParam String userId
    getAllUsersByManager, // /manager @RequestParam String managerId
    getUserByUserId // /userId @RequestParam String userId
}

export default UserService