class Storage {
    constructor() {
        this.rememberMe = false;
        this.init();
    }

    /**
     * Checks if the user's information is still active in localStorage.
    */
    init() {
        const rememberMe = localStorage.getItem('rememberMe');
        this.rememberMe = rememberMe;

        if (!this.rememberMe) {
            localStorage.clear();
        }
    }

    /**
     * Clears the localStorage if the rememberMe options is on.
    */
    clear() {
        if (this.rememberMe) {
            localStorage.clear();
        }
    }

    /**
     * Sets the user's choice for remembering their information (in local or session storage).
     * 
     * @param  {boolean} r the value that rememberMe should be set too
    */
    setRememberMe(r) {
        this.rememberMe = r;

        if (this.rememberMe) localStorage.setItem('rememberMe', true);
        else localStorage.removeItem('rememberMe');
    }

    /**
     * Checks if an item / string exists in the user's chosen storage type.
     * 
     * @param  {string} key the key that should be used to check
    */
    has(key) {
        if (this.rememberMe) return localStorage.getItem(key) !== null;
        return sessionStorage.getItem(key) !== null;
    }

    /**
     * Store an item in the user's chosen storage type.
     * 
     * @param  {string} key the key that should be used to store the object
     * @param  {object} obj the object that should be stored with the specified key
    */
    store(key, obj) {
        const storeObj = typeof obj === 'string' ? obj : JSON.stringify(obj);

        if (this.rememberMe) localStorage.setItem(key, storeObj);
        else sessionStorage.setItem(key, storeObj);
    }
    /**
     * Remove an item/string in the user's chosen storage type.
     * 
     * @param  {string} key the key of the string/object that should be removed from storage
    */
    remove(key) {
        if (this.rememberMe) localStorage.removeItem(key);
        else sessionStorage.removeItem(key);
    }

    /**
     * Retrieve the object associated with the provided key.
     * 
     * @param  {string} key the key that should be used to retrieve the object
    */
    getItem(key) {
        const itemStr = this.rememberMe
            ? localStorage.getItem(key)
            : sessionStorage.getItem(key);
        if (!itemStr) return null;

        return JSON.parse(itemStr);
    }

    /**
     * Retrieve the string associated with the provided key.
     * 
     * @param  {string} key the key that should be used to retrieve the objecty
    */
    getString(key) {
        if (this.rememberMe) return localStorage.getItem(key);
        else return sessionStorage.getItem(key);
    }
}

const storage = new Storage();
export default storage;
