import axios from "axios";

// export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'

// const API = axios.create({
//     baseURL: `http://localhost:8080/api/v1/auth`
// })

// class AuthServiceHub {
//
//     executeJwtAuthenticationService(userId, password) {
//         return axios.post(`http://localhost:8080/api/v1/auth/authenticate`, {
//             userId,
//             password
//         })
//     }
//
//     registerSuccessfulLoginForJwt(userId, token) {
//         localStorage.setItem("userId", userId)
//         let builtToken = this.createJWTToken(token)
//         this.setupAxiosInterceptors(builtToken)
//     }
//
//     setupAxiosInterceptors(token) {
//         axios.interceptors.request.use(
//             (config) => {
//                 if (!this.isUserLoggedIn()) {
//                     config.headers.authorization = token
//                     return config
//                 }
//             }, error => {
//                 return Promise.reject(error)
//             }
//         )
//     }
//
//     isUserLoggedIn() {
//         let user = sessionStorage.getItem("userId")
//         return user !== null;
//     }
//
//     createJWTToken(token) {
//         return 'Bearer ' + token
//     }
//
//     logout() {
//         localStorage.removeItem("userId");
//     }
//
//     getLoggedInUserName() {
//         let user = localStorage.getItem("userId")
//         if (user === null) return ''
//         return user
//     }
// }
//
// export default new AuthServiceHub()


const API = "http://localhost:8080/api/v1/auth";

const register = (userId, password) => {
    return axios.post(API + "/register", {
        userId,
        password,
    });
};

const login = (userId, password) => {
    return axios
        .post(API + "/authenticate", {
            userId,
            password,
        })
        .then((response) => {
            if (response.data.token) {
                localStorage.setItem("user", JSON.stringify(response.data));
            }
            return response.data;
        });
};

const logout = () => {
    localStorage.removeItem("user");
};

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
    register,
    login,
    logout,
    getCurrentUser,
};

export default AuthService;