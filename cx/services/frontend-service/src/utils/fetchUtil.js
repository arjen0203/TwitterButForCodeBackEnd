import {toast} from 'react-toastify';

//const LOCALURL = "http://localhost:8080/api";
const KUBEURL = "http://codex.kube/api/"


class Fetch {
    constructor() {
        this.unknown_error = 'Oops! Something went terribly wrong.';
        this.default_errors = new Map([
            [ 503, 'Service temporarily unavailable'],
            [ 404, 'Service could not be found'],
            [ 500, 'Internal server error'],
            [ 401, 'Unauthorized' ]
        ]);
    }

    auth() {
        return this.internalFetch('auth/', 'GET', null, false, true);
    }

    getMe() {
        return this.internalFetch('users/me', 'GET', null, false);
    }

    logout() {
        return this.internalFetch('auth/logout', 'POST', null, false, true);
    }
    
    get(path) {
        return this.internalFetch(path, 'GET', null);
    }

    put(path, body) {
        return this.internalFetch(path, 'PUT', body, (body instanceof FormData));
    }

    post(path, body) {
        return this.internalFetch(path, 'POST', body, (body instanceof FormData));
    }

    delete(path) {
        return this.internalFetch(path, 'DELETE', null);
    }

    printError(ret, ignoreErrors) {
        if (!ret.ok && !ignoreErrors) {
            if (ret.data && ret.data.message) {
                toast.error(ret.data.message);
                return;
            }
            if (ret.status && this.default_errors.has(ret.status)) {
                toast.error(this.default_errors.get(ret.status));
                return;
            }

            toast.error(this.unknown_error);
        }
    }

    async getData(result) {
        const type = result.headers.has("Content-Type") ? result.headers.get('Content-Type') : 'text';
        return type.includes('json') ? result.json() : result.text();
    }

    async internalFetch(path, method, body, isFormdata = false, ignoreErrors = false) {
        const headers = {};
        if ((method === 'PUT' || method === 'POST') && !isFormdata) {
            headers['content-type'] = 'application/json';
        }

        const init = {
            method,
            headers
        };

        if (body) {
            init.body = (isFormdata ? body : JSON.stringify(body));
        }

        try {
            const result = await fetch(KUBEURL + path, init);

            const ret = { 
                ok: result.ok, 
                status: result.status, 
                data: (await this.getData(result)) 
            };

            this.printError(ret, ignoreErrors);
            return ret;
        } catch (err) {
            this.printError(err, ignoreErrors);
            return { ok: false, data: null };
        }
    }
}

const fetchUtil = new Fetch();
export default fetchUtil;