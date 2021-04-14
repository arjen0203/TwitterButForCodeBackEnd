import React from 'react';
import './GlobalStyle.scss';
import Home from './pages/home/Home';
import { BrowserRouter as Router, Switch, Route, NavLink } from 'react-router-dom';
import Login from './pages/login/Login';
import Register from './pages/register/Register';
import Profile from './pages/profile/Profile';
import autobind from 'class-autobind';
import EditProfile from './pages/profile/edit/EditProfile';

class App extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            user: {userId: 1, username: "Guest", getToken: function () {return null;}}
        }

        autobind(this);
      }


    render() {
        return (
              <Router className="router">
                  <nav>
                      <ul className="router-list">
                          {this.state.user.userId !== 0 ? (<li><NavLink to={'/feed'} activeClassName="activeNav">Feed</NavLink></li>) : <li><NavLink to={'/home'} activeClassName="activeNav">Home</NavLink></li>}
                          {this.state.user.userId !== 0 && (<li><NavLink to={'/profile'} activeClassName="activeNav">Profile</NavLink></li>)}
                          <li><NavLink to={'/search'} activeClassName="activeNav">Search</NavLink></li>
                          {this.state.user.userId !== 0 && <li><NavLink to={'/post/create'} activeClassName="activeNav">Create post</NavLink></li>}
                          {this.state.user.userId !== 0 ? (<li className="login-button"><div className="logout-button">Logout</div></li>) : <li className="login-button"><NavLink to={'/login'} activeClassName="activeNav">Login</NavLink></li>}
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
        )
    }
}

export default App;
