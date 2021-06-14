import React, {Component} from 'react'
import './Home.scss'

export default class Home extends Component {
    render() {
        return (
            <div className='center'>
                <b className='home-title'>Welcome to code-x</b>
                <div className='home-text'>On this website you can share your code snippets with your friends and followers</div>
                <div className='home-text'>DISCLAIMER: this website currently does not support mobile devices</div>
            </div>
        )
    }
}
