import axios from 'axios';

/*
  Axios, which is a popular library is mainly used to send asynchronous 
  HTTP requests(GET,POST,PUT,DELETE) to REST endpoints. 
This library is very useful to perform CRUD operations.
This popular library is used to communicate with the backend. 
Axios supports the Promise API, native to JS ES6.
Using Axios we make API requests in our application. 
Once the request is made we get the data in Return, and then we use this data in our React APPL. 

> npm install axios

*/
// Service class interacts with REST API
export const SESSION_USER_NAME = 'authenticatedUser';
class AuthenticationService {
  
    static async login(dealer) {
        try {
            const response = await axios.post('http://localhost:8085/ims/api/login', dealer);
                console.log('REST API Response : ', response.data);
            if(response.data === true) {
                return true;
            }
            else { return false; }
        }
        catch(error) {
            console.error('Login Error : ', error);
        }
    }
    
    static async registerDealer(dealer) {
        try {
            const response = await axios.post('http://localhost:8085/ims/api/register', dealer);
            return response.data;
        }
        catch(error) {
            console.error('Registration Error :', error);
        }
    }

    static async getDealerInfo() {
        return axios.get('http://localhost:8085/ims/api/dealers')
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error Fetching Dealer Info: ", error);
        });
    }

    // Session
    /*
    * A session is a group of user interactions with your website that take place within a given time frame. 
    * For example a single session can contain multiple page views, events, social interactions, and ecommerce transactions.
    * 
    * Sessionstorage is a predefined Object, allows us to store data in key/value pairs in the browser.
    * The data which we save in session storage will only be persisted in the current browser tab. 
    * If we close the current tab or browser window, the saved data in session storage will be cleared.
    * * */
   
    // Session Management using sessionStorage Object - setItem(), getItem(), removeItem()

    static registerSuccessfulLogin(username) {
        sessionStorage.setItem(SESSION_USER_NAME,username);
        console.log("UserName :"+username);
    }

    static isUserLoggedIn() {
        let user = sessionStorage.getItem(SESSION_USER_NAME);
        if(user === null) return false;
            return true;
    }

    static getLoggedInUserName() {
        let user = sessionStorage.getItem(SESSION_USER_NAME);
        if(user === null) return '';
        return user;
    }

    static logout() {
        sessionStorage.removeItem(SESSION_USER_NAME);
    }
}
export default AuthenticationService;
