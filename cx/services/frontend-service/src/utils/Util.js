
class Utility {
    jwtToUser(token) {
        const jwt = token.replace('Bearer ');
        return JSON.parse(atob(jwt.split('.')[1]));
    }
}

const Util = new Utility();
export default Util;