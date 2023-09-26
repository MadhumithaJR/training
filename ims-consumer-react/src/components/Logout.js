import React, {useEffect} from 'react'
import AuthenticationService from '../service/AuthenticationService';
import { useNavigate } from 'react-router-dom';

function Logout() {
    const history = useNavigate();

  return (
    <div>
        <br/>
        <h1 style={{ color: 'wheat'}}>You are logged out.</h1>
        <div>
            <img style={{ borderRadius:'20px'}}
            src='/images/logout.png' alt='Logout' height={400} width={400}></img>
        </div>
        <div className='container'>
            Thank You for Using Our Application
        </div>
    </div>
  )
}

export default Logout