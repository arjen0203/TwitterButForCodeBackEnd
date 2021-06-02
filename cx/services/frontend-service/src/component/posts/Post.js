import React, { useState } from 'react';
import './post.scss'
import Fetch from '../../utils/fetchUtil';

export default function Post(props) {
    const [liked, setLiked] = useState(props.data.liked);
    const [likeAmount, setLikeAmount] = useState(props.data.postLikesCount);

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

    async function likePost() {
        var result;
        if (liked) {
            result = await Fetch.delete(`posts/${props.data.id}/likes`)
            if (result.ok) {
                setLiked(false);
                setLikeAmount(likeAmount - 1);
            }
        } else {
            result = await Fetch.post(`posts/${props.data.id}/likes`, null)
            if (result.ok) {
                setLiked(true);
                setLikeAmount(likeAmount + 1);
            }
        }
    }

    return (
        <div className='post'>
            <div className='title'>{props.data.title}</div>
            <div className='author'>Author: {props.data.author}</div>
            <div className='body'>
                {makePostBody(props.data.contentBlocks)}
            </div>
            <div className='stats'>
                <div>{likeAmount} likes</div>
                <div>{props.data.commentsCount} comments</div>
                <div>{props.data.revisionsCount} revisions</div>
            </div>
            <div className='bottom-buttons'>
                {!props.isUser && <div>{liked ? <button onClick={() => likePost()} className='active-button'>like</button> : <button onClick={() => likePost()}>like</button>}</div>}
                <button>discussion</button>
                {!props.isUser ? <button>report</button> : <button>edit</button>}
            </div>
        </div>
    )
}
