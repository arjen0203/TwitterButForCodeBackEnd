import React, { useState } from 'react';
import './post.scss'
import Fetch from '../../../utils/fetchUtil';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';

export default function Post(props) {
    const [liked, setLiked] = useState(props.data.liked);
    const [likeAmount, setLikeAmount] = useState(props.data.postLikesCount);
    const history = useHistory();

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

    async function deletePost() {
        var result = await Fetch.delete("/posts/" + props.data.id);
        if (result.ok) {
            toast.success("Succesfully deleted post");
            history.push("/home");
        }
    }

    return (
        <div className='post'>
            <div className='title'>- {props.data.title}</div>
            {props.data.revision && <div className='revision-link' onClick={() => history.push("/post/" + props.data.revision.originalPost.id)}>Revision of: {props.data.revision.originalPost.title}</div>}
            <div className='author' onClick={() => history.push("/profile/" + props.data.author)}>Author: {props.data.author}</div>
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
                {!props.isExpanded && <button onClick={() => history.push("/post/" + props.data.id)}>discussion</button>}
                {props.isUser && props.isExpanded && <button onClick={() => deletePost()}>Delete</button>}
                {!props.isUser ? <button>report</button> : <button>edit</button>}
            </div>
        </div>
    )
}
