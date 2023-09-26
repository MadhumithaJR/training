import React from "react";
import {Link} from 'react-router-dom';
import '../styles/NavBar.css'
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import { useNavigate } from "react-router-dom";
import AuthenticationService from "../service/AuthenticationService";

/* In HTMLO we use hyperlink for Navigation - <a href= "">
In ReactJS we use Route for Navigation
*/

// Arrow functional component

const NavBar=() => {

    const history = useNavigate();

    const isUserLoggedIn = AuthenticationService.isUserLoggedIn();
    const userName = AuthenticationService.getLoggedInUserName();

    const handleLogout = () => {
        AuthenticationService.logout();
    }

    return(
        <nav className="navbar">
            <ul className="nav-list">
                { isUserLoggedIn ? ( <>
                    <li className="nav-item">
                        <Link to="/" className="nav-link">
                            <span><FontAwesomeIcon icon="home"></FontAwesomeIcon></span>&nbsp;
                            Home</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/product" className="nav-link">
                            <span><FontAwesomeIcon icon="bomb"></FontAwesomeIcon></span>&nbsp;
                            Products</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/search" className="nav-link">
                            <span><FontAwesomeIcon icon="search"></FontAwesomeIcon></span>&nbsp;
                            Search</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/dealers" className="nav-link">
                            <span><FontAwesomeIcon icon="people-group"></FontAwesomeIcon></span>&nbsp;
                            Dealers Info</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/logout" className="nav-link" onClick = {handleLogout}>
                            <span><FontAwesomeIcon icon="sign-out"></FontAwesomeIcon></span>&nbsp;
                            Logout</Link>
                    </li> 
                    <span className="user-style">Welcome : {userName}
                    <img className="avatar" src="/images/avatar.png" alt="UserAvatar" height={30} width={30}></img>
                    </span>
                    </>) : ( <>
                    <li className="nav-item">
                        <Link to="/register" className="nav-link">
                            <span><FontAwesomeIcon icon="camera-retro"></FontAwesomeIcon></span>&nbsp;
                            Register</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/login" className="nav-link">
                            <span><FontAwesomeIcon icon="sign-in"></FontAwesomeIcon></span>&nbsp;
                            Log In</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/about" className="nav-link">
                            <span><FontAwesomeIcon icon="coffee"></FontAwesomeIcon></span>&nbsp;
                            About Us</Link>
                    </li> </> )
                }
            </ul>
        </nav>
    );
}

export default NavBar;