import React from 'react';
import './GlobalStyle.scss';
import Home from './pages/home/Home';
import { BrowserRouter as Router, Switch, Route, NavLink } from 'react-router-dom';
import Login from './pages/login/Login';
import Register from './pages/register/Register';
import Profile from './pages/profile/Profile';
import autobind from 'class-autobind';
import EditProfile from './pages/profile/edit/EditProfile';
import { UserContext } from './contexts/UserContext';
import Fetch from './utils/fetchUtil';
import Storage from './utils/storage';

import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.min.css';


class App extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            user: {id: 0, username: "Guest"}
        }

        autobind(this);
    }

    async componentDidMount() {
        this.onStart();
    }

    async onStart() {
        const res = await Fetch.auth();
        if (res.ok) {
            const me = await Fetch.getMe();
            if (me.ok) {
                this.setState({user: me.data});
            }
        }
    }

    async loginUser() {
        const res = await Fetch.getMe();
        if (res.ok) {
            this.setState({user: res.data});
        } else {
            this.setState({user: {id: 0, username: "Guest"}});
        }
    }

    async logoutUser() {
        const res = await Fetch.logout();
        if (res.ok) {
            this.setState({user: {id: 0, username: "Guest"}});
            Storage.clear();
        }
    }

    render() {
        const userContext = {
            user: this.state.user,
            loginUser: this.loginUser
        }
        
        return (
            <UserContext.Provider value={userContext}>
                <Router className="router">
                    <nav>
                        <ul className="router-list">
                            {this.state.user.id !== 0 ? (<li><NavLink to={'/feed'} activeClassName="activeNav">Feed</NavLink></li>) : <li><NavLink to={'/home'} activeClassName="activeNav">Home</NavLink></li>}
                            {this.state.user.id !== 0 && (<li><NavLink to={'/profile'} activeClassName="activeNav">Profile</NavLink></li>)}
                            <li><NavLink to={'/search'} activeClassName="activeNav">Search</NavLink></li>
                            {this.state.user.id !== 0 && <li><NavLink to={'/post/create'} activeClassName="activeNav">Create post</NavLink></li>}
                            {this.state.user.id !== 0 ? (<li className="login-button"><div className="logout-button" onClick={this.logoutUser}>Logout</div></li>) : <li className="login-button"><NavLink to={'/login'} activeClassName="activeNav">Login</NavLink></li>}
                        </ul>
                    </nav>
                    <Switch>
                        <Route exact path='/login' component={Login}/>
                        <Route exact path='/register' component={Register}/>
                        <Route exact path='/home' component={Home}/>

                        <Route exact path='/profile' component={Profile}/>
                        <Route exact path='/profile/edit' component={EditProfile}/>
                        <Route exact path='/feed' component={Home}/>

                        <Route exact path='/reports' component={Home}/>
                        <Route exact path='/post' component={Home}/>
                        <Route exact path='/post/create' component={Home}/>

                        <Route path='/' component={Home}/>
                    </Switch>
                </Router>
                <ToastContainer />
            </UserContext.Provider>
        )
    }
}

export default App;
