import React, {useState, useEffect} from 'react';
import './post.scss'
import Fetch from '../../../utils/fetchUtil';
import {useHistory} from 'react-router';
import {toast} from 'react-toastify';

export default function Post(props) {
    const [liked, setLiked] = useState(null);
    const [likeAmount, setLikeAmount] = useState(null);
    const [commentAmount, setCommentAmount] = useState(null);
    const [revisionAmount, setRevisionAmount] = useState(null);
    const history = useHistory();

    useEffect(() => {
        getStats();

        // eslint-disable-next-line react-hooks/exhaustive-deps
      }, []);

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
        if (liked == null) {
            toast.warn("Wait for the post to finish loading");
            return;
        }
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

    async function getStats() {
        var result = await Fetch.get("/posts/" + props.data.id + "/stats");
        if (result.ok) {
            setLiked(result.data.liked);
            setCommentAmount(result.data.commentsCount);
            setLikeAmount(result.data.postLikesCount)
            setRevisionAmount(result.data.revisionsCount)
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
                <div>{likeAmount != null ? likeAmount : "?"} likes</div>
                <div>{commentAmount != null ? commentAmount : "?"} comments</div>
                <div>{revisionAmount != null ? revisionAmount : "?"} revisions</div>
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
