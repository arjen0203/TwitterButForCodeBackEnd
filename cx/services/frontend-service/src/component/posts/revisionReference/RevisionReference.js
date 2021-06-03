import React from 'react'
import { useHistory } from 'react-router';
import './revisionReference.scss';

export default function RevisionReference(props) {
    const history = useHistory();

    function displayDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleString('nl-NL');
    }

    return (
        <div className='revision'>
            <div className='author' onClick={() => history.push("/profile/" + props.data.author)}>{props.data.author}</div>
            <div className='title'>{props.data.title}</div>
            <div className='date-button-wrapper'>
                <div className='createdAt'>{displayDate(props.data.createdAt)}</div>
                {<button className='goButton' onClick={() => history.push(`/post/${props.data.id}`)}>Go to post</button>}
            </div>
        </div>
    );
}
