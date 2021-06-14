import React, {useEffect, useState} from 'react';
import './EditProfile.scss';
import {privacyEnum} from './PrivacyEnum';
import {useHistory} from 'react-router';
import Fetch from '../../../utils/fetchUtil';
import {toast} from 'react-toastify';
import {UserContext} from '../../../contexts/UserContext';

export default function EditProfile(props) {
    const history = useHistory();
    const [isLoaded, setIsLoaded] = useState(false);
    const [name, setName] = useState("");
    const [status, setStatus] = useState("");
    const [profilePrivacy, setProfilePrivacy] = useState(0);
    const [postPrivacy, setPostPrivacy] = useState(0);

    useEffect(() => {
        setName('renssssssss');
        setStatus('This is going to be the big ass status that I want people to have on their account it should be able to be very long and boring so we just gonne typ some placeholder text here just because i can hihi');
        setIsLoaded(true);
        setProfilePrivacy(0);
        setPostPrivacy(0);
      }, []);

    function saveProfile() {
        history.push("/profile");
    }

    function changeStatus(stat) {
        setStatus(stat)
    }

    function changeProfilePrivacy(val) {
        setProfilePrivacy(val);
    }

    function changePostPrivacy(val) {
        setPostPrivacy(val);
    }

    async function removeUser(logout) {
        var result = await Fetch.delete("/users");
        if (result.ok) {
            toast.success("succesfully deleted acount");
            logout();
            history.push("/");
        }

    }

    return (
        <UserContext>
        {userContext => { 
            if(userContext.user.id === 0) history.push("/login");
            return (        
            <div className='center'>
                {!isLoaded ? 
                <div>loading...</div> :
                <div className='edit-profile-header'>
                    <div className='title'><div>Edit profile of </div><div className='name'>{name}</div></div>
                    <div className='status'>
                        <div>Edit status:</div>
                        <textarea onChange={(stat) => changeStatus()} value={status} className='input'></textarea>
                    </div>
                    <div className='profile-privacy'>
                        <div>Who can see your profile:</div>
                        <div className='privacy-buttons'>
                            {profilePrivacy === privacyEnum.ANYONE ? <button className='button-active'>Anyone</button> : <button onClick={() => changeProfilePrivacy(0)} className='button-inactive'>Anyone</button>}
                            {profilePrivacy === privacyEnum.FRIENDSONLY ? <button className='button-active'>Friends only</button> : <button onClick={() => changeProfilePrivacy(1)} className='button-inactive'>Friends only</button>}
                            {profilePrivacy === privacyEnum.ONLYUSER ? <button className='button-active'>Only you</button> : <button onClick={() => changeProfilePrivacy(2)} className='button-inactive'>Only you</button>}
                        </div>
                    </div>
                    <div className='post-privacy'>
                        <div>Who can see your posts:</div>
                        <div className='post-buttons'>
                            {postPrivacy === privacyEnum.ANYONE ? <button className='button-active'>Anyone</button> : <button onClick={() => changePostPrivacy(0)} className='button-inactive'>Anyone</button>}
                            {postPrivacy === privacyEnum.FRIENDSONLY ? <button className='button-active'>Friends only</button> : <button onClick={() => changePostPrivacy(1)} className='button-inactive'>Friends only</button>}
                            {postPrivacy === privacyEnum.ONLYUSER ? <button className='button-active'>Only you</button> : <button onClick={() => changePostPrivacy(2)} className='button-inactive'>Only you</button>}
                        </div>
                    </div>
                    <div className='remove-profile'>
                        <div className='remove-button'>
                            <button onClick={() => removeUser(userContext.logoutUser)} className='remove-user'>Remove this account</button>
                        </div>
                    </div>
                    <div className='bottom'>
                        <div className='buttons'>
                            <button onClick={() => saveProfile()} className='save-profile'>Save</button>
                        </div>
                    </div>
                </div>
                }
            </div>)
        }}
        </UserContext>

    )
}


