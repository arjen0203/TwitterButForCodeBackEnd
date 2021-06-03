import React, { useState, useEffect } from 'react';
import { UserContext } from '../../contexts/UserContext';
import { useHistory } from 'react-router';
import { useParams } from "react-router-dom";
import Fetch from '../../utils/fetchUtil';
import Post from './../../component/posts/post/Post.js';
import './ExpandedPost.scss';
import Comment from './../../component/posts/comment/Comment'
import CreateComment from '../../component/posts/comment/CreateComment';
import RevisionReference from '../../component/posts/revisionReference/RevisionReference';
import { toast } from 'react-toastify';

export default function ExpandedPost(props) {
    const {postId} = useParams();
    const history = useHistory();

    const [comments, setComments] = useState([]);
    const [revisions, setRevisions] = useState([]);
    const [post, setPost] = useState({});
    const [isUser, setIsUser] = useState(false);
    const [isLoadingPost, setIsLoadingPost] = useState(true);
    const [isLoadingRevisions, setIsLoadingRevisions] = useState(true);
    const [isLoadingComments, setIsLoadingComments] = useState(true);
    const [isShowingComments, setIsShowingComments] = useState(true);
    const [nextPageComments, setNextPageComments] = useState(0);
    const [totalPagesComments, setTotalPagesComments] = useState(0);
    const [nextPageRevisions, setNextPageRevisions] = useState(0);
    const [totalPagesRevisions, setTotalPagesRevisions] = useState(0);
    const [user, setUser] = useState("");
    const [isCreatingComment, setIsCreatingComment] = useState(false);

    useEffect(() => {
        async function fetchData() {
            var result = await Fetch.get('/posts/' + postId);
            if (result.ok) {
                setPost(result.data);
                setIsLoadingPost(false);
                setCommentsOfPost(0);
                setRevisionsOfPost(0);
            }
        }

        fetchData();

        // eslint-disable-next-line react-hooks/exhaustive-deps
      }, []);

      useEffect(() => {
        window.addEventListener('scroll', handleScroll);
        return () => (window.removeEventListener('scroll', handleScroll));

        // eslint-disable-next-line react-hooks/exhaustive-deps
      }, [nextPageComments, isLoadingComments, comments, nextPageRevisions, isLoadingRevisions, revisions]);

      const handleScroll = async () => {

        const bottom = Math.ceil(window.innerHeight + window.scrollY) >= document.documentElement.scrollHeight

        if (isShowingComments) {
            
            if (bottom && nextPageComments !== totalPagesComments && !isLoadingComments) {
                setCommentsOfPost(nextPageComments);
            }
        } else {
            if (bottom && nextPageRevisions !== totalPagesRevisions && !isLoadingRevisions) {
                
                setRevisionsOfPost(nextPageRevisions);
            }
        }

      };

      const setCommentsOfPost = async (page) => {
        setIsLoadingComments(true);
        const commentsSize = 5;
        
        var result = await Fetch.get(`/posts/${postId}/comments?size=${commentsSize}&page=${page}`)
        if (result.ok) {
            var allComments = [...comments, ...result.data.content];
            setComments(allComments);
            setTotalPagesComments(result.data.totalPages);
            setNextPageComments(nextPageComments + 1);
        }

        setIsLoadingComments(false);
    }

    const setRevisionsOfPost = async (page) => {
        setIsLoadingRevisions(true);
        const revisionsSize = 5;
        
        var result = await Fetch.get(`/posts/${postId}/revisions?size=${revisionsSize}&page=${page}`)
        if (result.ok) {
            var allRevisions = [...revisions, ...result.data.content];
            setRevisions(allRevisions);
            setTotalPagesRevisions(result.data.totalPages);
            setNextPageRevisions(nextPageRevisions + 1);
        }

        setIsLoadingRevisions(false);
    }

    function showComments(input) {
        var output = [];
        for (let i = 0; i < input.length; i++) {
            output.push(<Comment data={input[i]} isFromUser={user === input[i].user} deleteFunction={() => deleteComment(input[i].id, i)} key={i +'pc'}/>);
        }
        return output;
    }

    function showRevisions(input) {
        var output = [];
        for (let i = 0; i < input.length; i++) {
            output.push(<RevisionReference data={input[i]} key={i +'prr'}/>);
        }
        return output;
    }

    async function deleteComment(id, commentIndex) {
        var result = await Fetch.delete('posts/comments/'+ id);
        if (result.ok) {
            var newComments = [...comments]
            newComments.splice(commentIndex, 1);
            setComments(newComments);
            toast.success("Succesfully deleted comment");
        }
    }

    function addCommentToList(comment) {
        var newComments = [...comments];
        newComments.unshift(comment);
        setComments(newComments);
    }

    return (
        <div className="expanded-post">
            <div className="post-div">
                {!isLoadingPost ? <Post data={post} pi={0} isUser={isUser} isExpanded={true}/> : <div className="loading">Loading...</div>}
            </div>
            {!isLoadingPost &&<div className="add-buttons-div">
                <button onClick={() => setIsCreatingComment(true)}>Add comment</button>
                <button>Create revision</button>
            </div>}
            {isCreatingComment && <div className="create-commment-div">
                <CreateComment closeCreateComment={() => setIsCreatingComment(false)} addCommentToList={(comment) => addCommentToList(comment)} postId={postId}/>
            </div>}
            {(!isLoadingPost) && <div className="view-buttons-div">
                <button disabled={isShowingComments} onClick={() => setIsShowingComments(true)} >Comments</button>
                <button disabled={!isShowingComments} onClick={() => setIsShowingComments(false)}>Revisions</button>
            </div>}
            {isShowingComments && <div class="comments-div">
                {showComments(comments)}
                {(isLoadingComments && !isLoadingPost) && <div className="loading">Loading...</div>}
            </div>}
            {!isShowingComments && <div class="revisions-div">
                {showRevisions(revisions)}
                {(isLoadingComments && !isLoadingPost) && <div className="loading">Loading...</div>}
            </div>}
            <UserContext>
                {userContext => { 
                    if(userContext.user.id === 0) history.push("/login");
                    setIsUser(userContext.user.id === post.author);
                    setUser(userContext.user.id);
                }}
            </UserContext>
        </div>
    )
}
