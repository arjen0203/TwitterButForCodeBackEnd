import React from "react";

const UserContext = React.createContext({user: {id: 0, username: "Guest"}});

export {
    UserContext
};