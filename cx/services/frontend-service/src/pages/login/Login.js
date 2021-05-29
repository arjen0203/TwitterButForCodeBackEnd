import React, {Component} from 'react';
import './Login.scss';
import { UserContext } from '../../contexts/UserContext'
import Fetch from '../../utils/fetchUtil';
import Storage from '../../utils/storage';
import { toast } from 'react-toastify';
import autobind from 'class-autobind';

class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            email: "",
            password: "",
            rememberMe: false
        }

        autobind(this);
    }

    handleNameChange(event){
        this.setState({email: event.target.value});
    }

    handlePasswordChange(event){
        this.setState({password: event.target.value});
    }
    handleRememberChange(){
        this.setState({rememberMe: !this.state.rememberMe});
    }

    async login(loginUser) {
        if (!this.validLogin()) return;

        var data = {email: this.state.email, password: this.state.password, rememberMe: this.state.rememberMe}

        const result = await Fetch.post('auth/login', data, false);
        if (result.ok) {
            Storage.setRememberMe(this.state.rememberMe);
            toast.success('Login Successful');
            this.props.history.push("/");
            loginUser();
        }

    }

    validLogin() {
        if (this.state.email === "" || this.state.password === "") {
            toast.warn("Please fill in all fields");
            return false;
        }
        if (!this.state.email.includes('@') || this.state.email.length < 3) {
            toast.warn("Email filled in does not follow a valid pattern");
            return false;
        }
        if (this.state.password.length < 8 || this.state.password.length > 64) {
            toast.warn("Password must be between 8 and 64 charachters long");
            return false;
        }
        return true;
    }

    toRegister(){
        this.props.history.push("/register");
    }

    render() {
        return (
            <UserContext.Consumer>
                {userContext => {
                    if (userContext.user.id !== 0) this.props.history.push("/");
                    return (
                    <div className="center">
                        <div className="login-fields">
                            <b className="login-title">Login</b>

                            <label htmlFor="email">email:</label>
                            <input id="email" className="login-email-input" type="email" placeholder="Email" value={this.state.email} onChange={this.handleNameChange}></input>

                            <label htmlFor="password">Password:</label>
                            <input id="password" className="login-email-input" type="password" placeholder="Password" value={this.state.password} onChange={this.handlePasswordChange}></input>

                            <div className="login-remember-div">
                                <label htmlFor="remember">Remember me:</label>
                                <input id="remember" type="checkbox" value={this.state.rememberMe} onClick={this.handleRememberChange}></input>
                            </div>
                            <button className="login-submit-button" onClick={() => this.login(userContext.loginUser)}>Login</button>

                            <div className="to-register-link" onClick={this.toRegister}>Don't have an account yet? Register here.</div>
                        </div>
                    </div>     
                )}}
            </UserContext.Consumer>                       
        );
    }
}

export default Login;