import React, {useState} from 'react'
import '../styles/Login.css'
import AuthenticationService from '../service/AuthenticationService';
import { useNavigate } from 'react-router-dom';

const Login = () => {

    const history = useNavigate(); // Object to navigate to another component pragmatically

    const [email,setEmail] = useState('');
    const [password,setPassword] = useState('');
    const [errorMessage,setErrorMessage] = useState('');
    const [successMessage,setSuccessMessage] = useState('');

    const handleLogin = async () => {
        if(!email || !password) {
            setErrorMessage('Please enter both Email and Password.');
            return;
        }

        const dealer = {email, password};
        try {
            const loginSuccess = await AuthenticationService.login(dealer);
            console.log('API response: ',loginSuccess.data);

            if(loginSuccess) {
                setSuccessMessage('Login Successful. Redirecting ...');
                AuthenticationService.registerSuccessfulLogin(email);
                setTimeout(() => {history('/product')}, 500);

            }
            else {
                setErrorMessage('Invalid Email or Password.');
            }
        }
        catch(error) {
            console.log('Login Error : ', error)
            setErrorMessage('Error Occured during Login');
        }
    }
  return (
    <div> <br/> <br/>
        <div className='container'>
            <h2 style={{color:'green'}}>Dealer Login</h2>
            <div className='form-group'>
                <label>Email :</label>
                <input type='email' className='form-control' value = {email} onChange={(e) => setEmail(e.target.value)}></input>
            </div>
            <div className='form-group'>
                <label>Password :</label>
                <input type='password' className='form-control' value = {password} onChange={(e) => setPassword(e.target.value)}></input>
            </div>
            <button className='btn btn-primary' onClick={handleLogin}>Login</button>
            {errorMessage && <p className='error-message'>{errorMessage}</p>}
            {successMessage && <p className='success-message'>{successMessage}</p>}
        </div>
    </div>
  )
}

export default Login