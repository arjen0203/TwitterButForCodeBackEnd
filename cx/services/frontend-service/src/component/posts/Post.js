import React, { useState } from 'react';
import './post.scss'

export default function Post(props) {
    const [liked, setLiked] = useState(props.liked);
    const [disliked, setDisliked] = useState(props.disliked);

    function makePostBody(input) {
        var output = [];
        for (let i = 0; i < input.length; i++) {
            output.push(
                <div className='block' key={i +'cdb' + props.pi}>
                    <div className='sub-title'>{input[i].subTitle}</div>
                    <div className='description'>{input[i].desc}</div>
                    <div className='file-name'>{input[i].fileName}</div>
                    <div className='code'>{input[i].code}</div>
                </div>);
        }
        return output;
    }

    function likePost() {
        setLiked(!liked);
        if (disliked) setDisliked(false);
    }

    function dislikePost() {
        setDisliked(!disliked);
        if (liked) setLiked(false);
    }

    return (
        <div className='post'>
            <div className='title'>{props.data.title}</div>
            <div className='author'>Author: {props.data.author}</div>
            <div className='body'>
            {makePostBody(props.data.body)}
            </div>
            <div className='stats'>
                <div>{props.data.likes} likes</div>
                <div>{props.data.commentAmount} comments</div>
                <div>{props.data.revisionAmount} revisions</div>
            </div>
            <div className='bottom-buttons'>
                {!props.isUser && <div>{liked ? <button onClick={() => likePost()} className='active-button'>like</button> : <button onClick={() => likePost()}>like</button>}</div>}
                {!props.isUser && <div>{disliked ? <button onClick={() => dislikePost()} className='active-button'>dislike</button> : <button onClick={() => dislikePost()}>dislike</button>}</div>}
                <button>discussion</button>
                {!props.isUser ? <button>report</button> : <button>edit</button>}
            </div>
        </div>
    )
}
