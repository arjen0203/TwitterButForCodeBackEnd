import React from 'react'
import { useHistory } from 'react-router';

import './comment.scss'

export default function Comment(props) {
    const history = useHistory();

    function displayDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleString('nl-NL');
    }

    return (
        <div className='comment'>
            <div className='author' onClick={() => history.push("/profile/" + props.data.user)}>{props.data.user}</div>
            <div className='content'>{props.data.content}</div>
            <div className='date-button-wrapper'>
                <div className='createdAt'>{displayDate(props.data.createdAt)}</div>
                {props.isFromUser && <button className='deleteButton' onClick={props.deleteFunction}>Delete</button>}
            </div>
        </div>
    );
}
