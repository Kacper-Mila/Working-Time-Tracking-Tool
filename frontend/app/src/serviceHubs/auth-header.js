export default function AuthHeader() {
    const user = JSON.parse(localStorage.getItem('user'));
    console.log(user)
    if (user && user.token) {
        return { Authorization: 'Bearer ' + user.token };
    } else {
        return {};
    }
}