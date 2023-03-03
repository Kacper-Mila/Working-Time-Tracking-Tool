import axios from "axios";
import AuthHeader from "./auth-header";

const API = axios.create({
    baseURL: `http://localhost:8080/api/v1/requests`
})

const getAllRequests = async () => {
    return API.get('', { headers: AuthHeader() }).then(r => r.data)
}

// TODO implement proper usage form
// const addRequest = async (requestJson) => {
//     return api.post('', requestJson, postConf)
// }

const updateRequest = async ({userId, requestId, requestJson}) => {
    return API.patch(`/update?userId=${userId}&requestId=${requestId}`, requestJson, { headers: AuthHeader() })
}

const deleteRequest = async (requestId) => {
    return API.delete(`/delete?requestId=${requestId}`, { headers: AuthHeader() })
}

const getAllRequestsByType = async (requestType) => {
    return API.get(`/type?requestType=${requestType}`, { headers: AuthHeader() }).then(r => r.data)
}

const getRequestById = async (requestId) => {
    return API.get(`/id?requestId=${requestId}`, { headers: AuthHeader() }).then(r => r.data)
}

const getRequestsByUserId = async (userId) => {
    return API.get(`/userId?userId=${userId}`, { headers: AuthHeader() }).then(r => r.data)
}

const getEmployeesRequestsByManagerId = async (managerId) => {
    return API.get(`/managerId?managerId=${managerId}`, { headers: AuthHeader() }).then(r => r.data)
}

const acceptOrRejectEmployeeRequest = async ({requestId, requestStatus}) => {
    return API.patch(`/updateRequestStatus?requestId=${requestId}&requestStatus=${requestStatus}`, null, { headers: AuthHeader() })
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