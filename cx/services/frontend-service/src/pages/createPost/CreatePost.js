import React, { useState } from 'react';
import { CodeBlockEnum } from './CodeBlockEnum';
import Fetch from '../../utils/fetchUtil';
import { toast } from 'react-toastify';
import { useHistory } from 'react-router';
import './createPost.scss';
import { UserContext } from '../../contexts/UserContext'

export default function CreatePost(props) {
    const history = useHistory();
    const [title, setTitle] = useState("");
    const [contentBlocks, setContentBlocks] = useState([{subTitle: "", description: "", fileName: "", code: ""}]);

    function showContentBlocks() {
        var contentBlockDivs = [];

        for (let i = 0; i < contentBlocks.length; i++) {

            contentBlockDivs.push(<div className="block-div" key={"cntblc" + i}>
                <div className="input-div">
                    <label>Subtitle:</label>
                    <input type="text" value={contentBlocks[i].subTitle} onChange={(e) => {handleContentBlockChange(i, e.target.value, CodeBlockEnum.SUBTITLE)}}></input>
                </div>
                <div className="text-div">
                    <label>Description:</label>
                    <textarea value={contentBlocks[i].description} onChange={(e) => {handleContentBlockChange(i, e.target.value, CodeBlockEnum.DESCRIPTION)}}></textarea>
                </div>
                <div className="input-div">
                    <label>File name:</label>
                    <input type="text" value={contentBlocks[i].fileName} onChange={(e) => {handleContentBlockChange(i, e.target.value, CodeBlockEnum.FILENAME)}}></input>
                </div>
                <div className="code-div">
                    <label>Code:</label>
                    <textarea type="text" value={contentBlocks[i].code} onChange={(e) => {handleContentBlockChange(i, e.target.value, CodeBlockEnum.CODE)}}></textarea>
                </div>
                <button className="remove-button" onClick={() => removeContentBlock(i)}>Remove this block</button>
            </div>);
        }

        return contentBlockDivs;
    }

    function handleContentBlockChange(index, value, propEnum) {
        var newBlocks = [...contentBlocks];
        switch(propEnum) {
            case CodeBlockEnum.SUBTITLE:
                newBlocks[index].subTitle = value;
                break;
            case CodeBlockEnum.DESCRIPTION:
                newBlocks[index].description = value;
                break;
            case CodeBlockEnum.FILENAME:
                newBlocks[index].fileName = value;
                break;
            case CodeBlockEnum.CODE:
                newBlocks[index].code = value;
                break;
            default:
                return;
          }
          setContentBlocks(newBlocks);
    }

    function removeContentBlock(index) {
        var newBlocks = [...contentBlocks];
        newBlocks.splice(index, 1);
        setContentBlocks(newBlocks);
    }

    function addContentBlock() {
        var newBlocks = [...contentBlocks, {subTitle: "", description: "", fileName: "", code: ""}];
        setContentBlocks(newBlocks);
    }

    async function savePost() {
        if (!validInput()) return; 
        var data = {title, contentBlocks}
        var result = await Fetch.post('/posts/', data);
        if (result.ok) {
            toast.success("Succesfully added post")
            history.push("/post/" + result.data.id);
        }
    }

    function validInput() {
        if (title.length < 4 || title.length > 64) {
            toast.warn("Title needs to be between 4 and 64 charachters")
            return false;
        }
        for (let i = 0; i < contentBlocks.length; i++) {
            var block = contentBlocks[i];
            if (block.subTitle.length < 4 || block.subTitle.length > 64) {
                toast.warn("Subtitle needs to be between 4 and 64 charachters long for all blocks")
                return false;
            }
            if (block.description.length < 4 || block.subTitle.length > 512) {
                toast.warn("Description needs to be between 4 and 512 charachters long for all blocks")
                return false;
            }
            if (block.fileName.length < 4 || block.fileName.length > 32) {
                toast.warn("File name needs to be between 4 and 32 charachters long for all blocks")
                return false;
            }
            if (block.code.length < 4 || block.code.length > 512) {
                toast.warn("Code needs to be between 4 and 512 charachters long for all blocks")
                return false;
            }
        };

        return true;
    }

    return (
        <div className="create-post">
            <div className="post-div">
                <div className='head-title'>Create new post:</div>
                <div className="input-div-title">
                    <label>Title:</label>
                    <input type="text" value={title} onChange={(e) => setTitle(e.target.value)}></input>
                </div>
                <div className="content-blocks">
                {showContentBlocks()}
                </div>
                {contentBlocks.length < 5 && <button className="add-block-button" onClick={() => addContentBlock()}>Add contentBlock</button>}
            </div>
            <button className="post-button" onClick={() => savePost()}>Post</button>
            <UserContext>
                {userContext => { 
                    if(userContext.user.id === 0) history.push("/login");
                }}
            </UserContext>
        </div>
    )
}
