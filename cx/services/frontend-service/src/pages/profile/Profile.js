import React, { useState, useEffect } from 'react';
import './Profile.scss';
import Editsvg from './EditSVG';
import Post from './../../component/posts/Post.js';
import { UserContext } from '../../contexts/UserContext';
import { useHistory } from "react-router";

export default function Profile(props) {
    const history = useHistory();
    const [isLoaded, setIsLoaded] = useState(false);
    const [name, setName] = useState("");
    const [status, setStatus] = useState("");
    const [isUser, setIsUser] = useState(false);
    const [isFollowing, setIsFollowing] = useState(false);
    const [isFriend, setIsFriend] = useState(false);
    const [isFollowerAmount, setFollowerAmount] = useState(0);
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        setName('renssssssss');
        setStatus('This is going to be the big ass status that I want people to have on their account it should be able to be very long and boring so we just gonne typ some placeholder text here just because i can hihi');
        setIsLoaded(true);
        setIsUser(true);
        setIsFollowing(false);
        setIsFriend(false);
        setFollowerAmount(69);
        setPosts([{
            title: 'Test title this is', 
            author:'renssssssss',
            body: [
                {
                subTitle: 'This is subtitle',
                desc: 'This is a small discription of the given block very much wow',
                fileName: 'rens.js',
                code: 'if(rens) rens(RENS);'
                }
            ],
            likes: 420,
            commentAmount: 69,
            revisionAmount: 38
            },
            {
                title: 'Test title this is too', 
                author:'renssssssss',
                body: [
                    {
                        subTitle: 'This is subtitle 2',
                        desc: 'This is a small discription of the given block very much wow this one is even longer just because i can and iwantto see stuff yesss',
                        fileName: 'rens.js',
                        code: `if(rens) {
    rens(Rens);
} else {
    notRens(Rens);
}`
                    },
                    {
                        subTitle: 'This is subtitle 3',
                        desc: 'This is a small discription of the given block very much wow numm three already',
                        fileName: 'rensit.js',
                        code: 'if(!rens) rensss(RENS);'
                    }
                ],
                likes: 420,
                commentAmount: 69,
                revisionAmount: 38
            }]);
      }, []);

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
            {!isLoaded ? 
            <div>loading...</div> :
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
            </div>
            <UserContext>
                {userContext => { if(userContext.user.id === 0) history.push("/login");}}
            </UserContext>
        </div>
    )
}


