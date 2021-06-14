import React, {useState} from 'react'
import './createComment.scss'
import Fetch from './../../../utils/fetchUtil';
import {toast} from 'react-toastify';

export default function CreateComment(props) {
    const [content, setContent] = useState("");

    async function saveComment() {
        if (content.length < 4 || content.length > 512) {
            toast.warn("Comment should be between 4 and 512 charachters");
            return;
        }
        var result = await Fetch.post(`/posts/${props.postId}/comments`, {content});
        if (result.ok) {
            props.closeCreateComment();
            props.addCommentToList(result.data);
        }
    }

    function cancelAndClearComment() {
        props.closeCreateComment();
        setContent("");
    }

    return (
        <div className='create-comment'>
            <label htmlFor="content">New comment:</label>
            <textarea className='content' placeholder="Write your comment here" onChange={(e) => setContent(e.target.value) } value={content}></textarea>
            <div className='button-wrapper'>
                <button className='saveButton' onClick={(content) => saveComment()}>Save</button>
                <button className='cancelButton' onClick={() => cancelAndClearComment()}>Cancel</button>
            </div>
        </div>
    );
}
