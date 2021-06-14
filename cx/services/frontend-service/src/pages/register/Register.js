import React, {Component} from 'react';
import './Register.scss';
import autobind from 'class-autobind';
import Fetch from '../../utils/fetchUtil';
import {toast} from 'react-toastify';
import {UserContext} from '../../contexts/UserContext';

class Register extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            email: "",
            password: "",
            passwordRepeat: ""
        }

        autobind(this);
    }

    async tryRegistrating(){
        if (!this.legalInput()) {
            this.setState({password: "", passwordRepeat: ""});
            return;
        }

        const data = {username: this.state.username, email: this.state.email, password: this.state.password};
        const result = await Fetch.post('auth/register', data, false);
        if (result.ok) {
            toast.success(`Successfully created your account! You can now login.`);
            this.props.history.push('/login');
        }
        else {
            this.setState({password: "", passwordRepeat: ""});
        }
    }

    legalInput(){
        if (this.state.username.length === "" || this.state.password === "" || this.state.passwordRepeat === "" || this.state.email === "") {
            toast.warn("please fill in all fields");
            return false;
        }

        if (!this.state.email.includes('@') || this.state.email.length < 3) {
            toast.warn("Email filled in does not follow a valid pattern");
            return false;
        }

        if (this.state.username.length < 4 || this.state.username.length > 32) {
            toast.warn("Username should be between 4 and 32 charachters long");
            return false;
        }

        if (this.state.password.length < 8 || this.state.password.length > 64) {
            toast.warn("Password should be between 8 and 64 charachters long");
            return false;
        }

        if (this.state.password !== this.state.passwordRepeat) {
            toast.warn("Passwords do match");
            return false;
        }
        return true;
    }

    handleEmailChange(event){
        this.setState({email: event.target.value});
    }

    handleNameChange(event){
        this.setState({username: event.target.value});
    }

    handlePasswordChange(event){
        this.setState({password: event.target.value});
    }

    handlePasswordRepChange(event){
        this.setState({passwordRepeat: event.target.value});
    }

    goToLogin(){
        this.props.history.push("/login")
    }

    render() {
        return (
            <div className="center">
                <div className="register-fields">
                    <b className="register-title">Register:</b>
                    <label htmlFor="email">Email:</label>
                    <input id="email" className="register-email-input" type="text" placeholder="Email" value={this.state.email} onChange={this.handleEmailChange}></input>
                    <label htmlFor="username">Username:</label>
                    <input id="username" className="register-username-input" type="text" placeholder="Username" value={this.state.username} onChange={this.handleNameChange}></input>
                    <label htmlFor="password">Password:</label>
                    <input id="password" className="register-password-input" type="password" placeholder="Password" value={this.state.password} onChange={this.handlePasswordChange}></input>
                    <label htmlFor="passwordRep">Password repeat:</label>
                    <input id="passwordRep" className="register-password-input" type="password" placeholder="Password repeat" value={this.state.passwordRepeat} onChange={this.handlePasswordRepChange}></input>
                    <button className="register-submit-button" onClick={this.tryRegistrating}>Register</button>
                    <b className="register-error">{this.state.registerError}</b>

                    <div onClick={this.goToLogin} className="to-login-link">Already have an account? Login here.</div>
                </div>

                <UserContext>
                    {userContext => {if(userContext.user.id !== 0) this.props.history.push("/");}}
                </UserContext>
            </div>
        );
    }
}

export default Register;