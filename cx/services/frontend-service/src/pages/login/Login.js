import React, {Component} from 'react';
import './Login.scss';

class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: "",
        }

        this.handleNameChange = this.handleNameChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.toRegiter = this.toRegiter.bind(this);
    }

    handleNameChange(event){
        this.setState({username: event.target.value});
    }

    handlePasswordChange(event){
        this.setState({password: event.target.value});
    }

    login() {
    }

    toRegiter(){
        this.props.history.push("/register");
    }

    render() {
        return (
            <div className="center">
                <div className="login-fields">
                    <b className="login-title">Login</b>

                    <label htmlFor="username">Username:</label>
                    <input id="username" className="login-username-input" type="text" placeholder="Username" value={this.state.username} onChange={this.handleNameChange}></input>

                    <label htmlFor="password">Password:</label>
                    <input id="password" className="login-username-input" type="password" placeholder="Password" value={this.state.password} onChange={this.handlePasswordChange}></input>

                    <button className="login-submit-button" onClick={() => this.login()}>Login</button>
                    <b className="login-error">{this.state.loginError}</b>

                    <div className="to-register-link" onClick={this.toRegiter}>Don't have an account yet? Register here.</div>
                </div>
            </div>                            
        );
    }
}

export default Login;