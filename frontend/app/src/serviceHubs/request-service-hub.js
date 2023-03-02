import axios from "axios";

const api = axios.create({
    baseURL: `http://localhost:8080/api/v1/requests`
})

let postConf = {
    headers: {
        'Content-Type': 'application/json'
    }
};

const getAllRequests = async () => {
    return api.get('').then(r => r.data)
}

// TODO implement proper usage form
// const addRequest = async (requestJson) => {
//     return api.post('', requestJson, postConf)
// }

const updateRequest = async ({userId, requestId, requestJson}) => {
    return api.patch(`/update?userId=${userId}&requestId=${requestId}`, requestJson, postConf)
}

const deleteRequest = async (requestId) => {
    return api.delete(`/delete?requestId=${requestId}`)
}

const getAllRequestsByType = async (requestType) => {
    return api.get(`/type?requestType=${requestType}`).then(r => r.data)
}

const getRequestById = async (requestId) => {
    return api.get(`/id?requestId=${requestId}`).then(r => r.data)
}

const getRequestsByUserId = async (userId) => {
    return api.get(`/userId?userId=${userId}`).then(r => r.data)
}

const getEmployeesRequestsByManagerId = async (managerId) => {
    return api.get(`/managerId?managerId=${managerId}`).then(r => r.data)
}

const acceptOrRejectEmployeeRequest = async ({requestId, requestStatus}) => {
    return api.patch(`/updateRequestStatus?requestId=${requestId}&requestStatus=${requestStatus}`)
}

const RequestService = {
    getAllRequests,
    // addRequest, // @RequestBody Request request
    updateRequest, // /update @RequestParam String userId, @RequestParam Long requestId, @RequestBody RequestDto requestDto
    deleteRequest, // /delete @RequestParam Long requestId
    getAllRequestsByType, // /type @RequestParam RequestType requestType
    getRequestById, // /id @RequestParam Long requestId
    getRequestsByUserId, // /userId @RequestParam String userId
    getEmployeesRequestsByManagerId, // /managerId @RequestParam String managerId
    acceptOrRejectEmployeeRequest
}

export default RequestService