import React, { useState, useEffect } from 'react';
import './Profile.scss';
import Editsvg from './EditSVG';
import Post from './../../component/posts/Post.js';
import { UserContext } from '../../contexts/UserContext';
import { useHistory } from 'react-router';
import Fetch from '../../utils/fetchUtil';
import {useParams} from "react-router-dom";

export default function Profile(props) {
    const {userId} = useParams();

    const history = useHistory();
    const [isLoadingProfile, setIsLoadingProfile] = useState(true);
    const [isLoadingPosts, setIsLoadingPosts] = useState(true);
    const [name, setName] = useState("");
    const [status, setStatus] = useState("");
    const [isUser, setIsUser] = useState(false);
    const [isFollowing, setIsFollowing] = useState(false);
    const [isFriend, setIsFriend] = useState(false);
    const [isFollowerAmount, setFollowerAmount] = useState(0);
    const [posts, setPosts] = useState([]);
    const [nextPage, setNextPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        setName('renssssssss');
        setStatus('This is going to be the big ass status that I want people to have on their account it should be able to be very long and boring so we just gonne typ some placeholder text here just because i can hihi');
        setIsLoadingProfile(false);

        setIsFollowing(false);
        setIsFriend(false);
        setFollowerAmount(69);
        
        async function fetchData() {
            var posts = await getPostsOfUser(0);
        
            if (posts) {
                setPosts(posts);
            }
        }

        fetchData();

        // eslint-disable-next-line react-hooks/exhaustive-deps
      }, []);

      useEffect(() => {
        window.addEventListener('scroll', handleScroll);
        return () => (window.removeEventListener('scroll', handleScroll));

        // eslint-disable-next-line react-hooks/exhaustive-deps
      }, [nextPage, isLoadingPosts, posts]);

      const handleScroll = async () => {

        const bottom = Math.ceil(window.innerHeight + window.scrollY) >= document.documentElement.scrollHeight

        if (bottom && nextPage !== totalPages && !isLoadingPosts) {
            setIsLoadingPosts(true);
            var newPosts = await getPostsOfUser(nextPage);
            if (newPosts) {
                var allPosts = [...posts, ...newPosts];
                setPosts(allPosts);
            }
        }
      };
      
    const getPostsOfUser = async (page) => {
        const postsSize = 4;
        
        var result = await Fetch.get(`/posts/user/${userId}?size=${postsSize}&page=${page}`)
        var posts = null;
        if (result.ok) {
            posts = result.data.content;
            setTotalPages(result.data.totalPages);
            setNextPage(nextPage + 1);
            setIsLoadingPosts(false);
        }
        return posts;
    }

    function follow() {
        setIsFollowing(!isFollowing);
    }

    function addFriend() {
        setIsFriend(!isFriend);
    }

    function goToEditPage() {
        props.history.push("/profile/edit");
    }

    function listPosts(postsData) {
        var output = [];
        for (let i = 0; i < postsData.length; i++) {
            output.push(<Post data={postsData[i]} key={i + 'pst'} pi={i} isUser={isUser}/>);
        }
        return output;
    }

    return (
        <div className='profile-center'>
            {isLoadingProfile ? 
            <div className="loading">Loading...</div> :
            <div className='profile-header'>
                {isUser ? 
                    <div className='title'><div>This is your profile, </div><div className='name'>{name}</div></div> : 
                    <div className='title'><div>profile of </div><div className='name'>{name}</div></div>
                }
                <div className='status'>{status}</div>
                <div className='bottom'>
                    <div className='stats'>
                        <div className='followerAmount'>Followers: {isFollowerAmount}</div>
                    </div>
                    {!isUser ?
                    <div className='buttons'>
                        {!isFollowing ? <button onClick={() => follow()} className='follow-btn'>follow</button> : <button onClick={() => follow()} className='follow-btn'>unfollow</button>}
                        {!isFriend ? <button onClick={() => addFriend()} className='friend-btn'>add friend</button> : <button onClick={() => addFriend()} className='friend-btn'>remove friend</button>}
                    </div> :
                    <div className='buttons'>
                        <button onClick={() => goToEditPage()} className='edit-profile'><Editsvg className='edit-profile'></Editsvg></button>
                    </div>
                    }
                </div>
            </div>
            }
            <div className='user-posts'>
                {listPosts(posts)}
                {isLoadingPosts && <div className="loading">Loading...</div>}
            </div>
            <UserContext>
                {userContext => { 
                    if(userContext.user.id === 0) history.push("/login");
                    setIsUser(userContext.user.id === userId);
            }}
            </UserContext>
        </div>
    )
}


