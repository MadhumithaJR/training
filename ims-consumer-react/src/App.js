import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import NavBar from './components/NavBar';
import Registration from './components/Registration';
import Login from './components/Login';
import Product from './components/Product';
import ViewProduct from './components/ViewProduct';

/*
	React Router is a standard library for routing in React. 
	It enables the navigation among views of various components in a React Application, 
  allows changing the browser URL, and keeps the UI in sync with the URL. 

	React Router is a JavaScript framework that lets us handle client and server-side 
  routing in React applications. 
  It enables the creation of single-page web or mobile apps that allow navigating without 
  refreshing the page. 
  It also allows us to use browser history features while preserving the right application
   view.

   Used Version6 of Router

 > npm install react-router-dom --save
*/

import {library} from '@fortawesome/fontawesome-svg-core';
import {faSignIn, faCameraRetro, faCoffee, faBomb, faTrash, faEdit, faList, faPeopleGroup, faSearch} from '@fortawesome/free-solid-svg-icons';
import CreateProduct from './components/CreateProduct';
import DealersInfo from './components/DealersInfo';
import ProductSearch from './components/ProductSearch';
import About from './components/About';
library.add(faSignIn, faCameraRetro, faCoffee, faBomb, faTrash, faEdit, faList, faPeopleGroup, faSearch);
function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Inventory Management System</h1>
      </header>

      <section>
        <div style={{
          backgroundImage: "url(/images/ims-bg.jpg)", backgroundSize: 'cover', backgroundRepeat: 'no-repeat', minHeight: '100vh', minWidth: '100vw'
        }}>
        <Router>
          <NavBar />

          <Routes>
            <Route path='/register' element={<Registration/>} />
            <Route path='/login' element={<Login/>} />
            <Route path='/about' element={<About/>} />

            <Route path='/product' element={<Product/>} />
            <Route path='/dealers' element={<DealersInfo/>} />
            <Route path='/search' element={<ProductSearch/>} />

            <Route path='/addProduct/:id' element={<CreateProduct />} />
            <Route path='/viewProduct/:id' element={<ViewProduct />} />
          </Routes>
        </Router>

        </div>
      </section>

      <footer className="footer">
        <p>&copy; All Rights Reserved to Wells Fargo</p>
      </footer>
    </div>
  );
}

export default App;
