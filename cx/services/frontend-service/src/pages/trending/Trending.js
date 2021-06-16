import React, {useEffect, useState} from 'react';
import './Trending.scss';
import Post from '../../component/posts/post/Post.js';
import {UserContext} from '../../contexts/UserContext';
import {useHistory} from 'react-router';
import Fetch from '../../utils/fetchUtil';
import {TimeFrameEnum} from './TimeFrameEnum';
import {toast} from 'react-toastify';

export default function Profile(props) {
    const history = useHistory();
    const [isLoadingPosts, setIsLoadingPosts] = useState(true);
    const [posts, setPosts] = useState([]);
    const [nextPage, setNextPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [timeFrame, setTimeFrame] = useState(TimeFrameEnum.DAY);

    useEffect(() => {
        async function fetchData() {
            var posts = await getPostsOfTimeFrame(0, TimeFrameEnum.DAY);
        
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
      }, [nextPage, isLoadingPosts, posts, timeFrame]);

      const handleScroll = async () => {

        const bottom = Math.ceil(window.innerHeight + window.scrollY) >= document.documentElement.scrollHeight

        if (bottom && nextPage !== totalPages && !isLoadingPosts) {
            setIsLoadingPosts(true);
            var newPosts = await getPostsOfTimeFrame(nextPage, timeFrame);
            if (newPosts) {
                var allPosts = [...posts, ...newPosts];
                setPosts(allPosts);
            }
        }
      };
      
    const getPostsOfTimeFrame = async (page, postsTimeFrame) => {
        var result = await Fetch.get(createUrl(page, postsTimeFrame));
        var posts = null;
        if (result.ok) {
            posts = result.data.content;
            setTotalPages(result.data.maxPages);
            setNextPage(page + 1);
        }
        setIsLoadingPosts(false);
        return posts;
    }

    function createUrl(page, postsTimeFrame) {
        const postsSize = 4;
        switch(postsTimeFrame) {
            case TimeFrameEnum.DAY:
                return `posts/trending/day?size=${postsSize}&page=${page}`;
            case TimeFrameEnum.WEEK:
                return `posts/trending/week?size=${postsSize}&page=${page}`;
            case TimeFrameEnum.MONTH:
                return `posts/trending/month?size=${postsSize}&page=${page}`;
            case TimeFrameEnum.YEAR:
                return `posts/trending/year?size=${postsSize}&page=${page}`;
            default:
                return;
          }
    }

    function listPosts(postsData) {
        var output = [];
        for (let i = 0; i < postsData.length; i++) {
            output.push(<Post data={postsData[i]} key={i + 'pst'} pi={i}/>);
        }
        return output;
    }

    async function changeTimeFrame(newTimeFrame) {
        if (isLoadingPosts) {
            toast.warn("Please wait till posts are done loading")
            return;
        }
        if (newTimeFrame === timeFrame) return;
        setTimeFrame(newTimeFrame);
        setPosts([]);
        setIsLoadingPosts(true);
        setNextPage(0);
        let posts = await getPostsOfTimeFrame(0, newTimeFrame);
        setPosts(posts);
    }

    return (
        <div className='trending-center'>
            <div className='top-div'>
                <div className="title">Trending posts:</div>
                <div className="change-time-frame-buttons">
                    <button disabled={timeFrame === TimeFrameEnum.DAY} onClick={() => changeTimeFrame(TimeFrameEnum.DAY)}>Day</button>
                    <button disabled={timeFrame === TimeFrameEnum.WEEK} onClick={() => changeTimeFrame(TimeFrameEnum.WEEK)}>Week</button>
                    <button disabled={timeFrame === TimeFrameEnum.MONTH} onClick={() => changeTimeFrame(TimeFrameEnum.MONTH)}>Month</button>
                    <button disabled={timeFrame === TimeFrameEnum.YEAR} onClick={() => changeTimeFrame(TimeFrameEnum.YEAR)}>Year</button>
                </div>
            </div>
            <div className='trending-posts'>
                {listPosts(posts)}
                {isLoadingPosts && <div className="loading">Loading...</div>}
            </div>
            <UserContext.Consumer>
                {userContext => { 
                    if(userContext.user.id === 0) history.push("/login");
                }}
            </UserContext.Consumer>
        </div>
    )
}


