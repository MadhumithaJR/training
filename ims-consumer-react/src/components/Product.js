import React, {useState,useEffect} from 'react';
import { useNavigate } from 'react-router-dom';

import ProductService from '../service/ProductService';
import AuthenticationService from '../service/AuthenticationService';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

function Product() {

    const history=useNavigate();

    // state management using useState() hook
    const [products,setProducts] = useState([]);
    const [message, setMessage] = useState('');

     /*
    The useEffect hook in React is use to handle the side effects in React such as 
    fetching data, and updating DOM. This hook runs on every render but there is 
    also a way of using a dependency array using which we can control the effect of 
    rendering.

    The motivation behind the introduction of useEffect Hook is to eliminate the 
    side effects of using class-based components.

    Syntax: useEffect(<FUNCTION>, <DEPENDECY>)
     - To run useEffect on every render do not pass any dependency
     - To run useEffect only once on the first render pass any empty array in the dependecy
     - To run useEffect on change of a particular value. Pass the state and props in the dependency array
     */

     useEffect(() => {
        if(!AuthenticationService.isUserLoggedIn()){
            history('/login');
        } else {
        fetchProducts(); }
        });

    const fetchProducts = async () => {
        try {ProductService.getProducts().then((response) => {
            setProducts(response.data); // setting response to state - products
        });
    } catch(error) {
        console.error('Fetch Error: ', error); }
    }

    const addProduct = () => {
        history('/addProduct/_add'); // Navigate to CreateProduct component & pass '_add' as parameter
    }

    const editProduct = (id) => {
        history(`/addProduct/${id}`); // Navigate to 
    }

    const deleteProduct = (id) => {
        ProductService.deleteProduct(id).then(() => {
            fetchProducts();    // Refresh Product List
            setMessage('Product Deleted Successfully.');
            /*setTimeout(() => {
                setMessage('');
            }, 2000);   // Clear the message after 2 seconds*/
            setTimeout(() => {history('/product')}, 500);
        });
        history(`/deleteProduct/${id}`);
        
    }

    const viewProduct = (id) => {
        history(`/viewProduct/${id}`);
    }


    return (
        <div>
    <br/>
   
    <h1 className="text-success">Products List</h1>
    <br/>
        <div className = "row justify-content-center">
            <button className='btn btn-info w-auto' onClick={addProduct}>Add Product</button>
        </div>
    <br/>
    <div className="row justify-content-center" >
        <table className="table table-success w-auto">
         <thead>
            <tr className="table-danger">
                <th> Product Id</th>
                <th> Product Name</th>
                <th> Brand</th>
                <th> MadeIn</th>
                <th> Price</th>
                <th> Actions</th>
            </tr>
        </thead>
        <tbody>
                {products.map(
                        prod => 
                        <tr key={prod.id}>
                            <td> {prod.pid} </td>
                            <td> {prod.name} </td>
                            <td> {prod.brand} </td>
                            <td> {prod.madeIn} </td>
                            <td> {prod.price} </td>
                            <td>
                                <button className='btn btn-success' onClick={() => editProduct(prod.pid)}>
                                <span><FontAwesomeIcon icon="edit"></FontAwesomeIcon></span>
                                    </button>
                                    &nbsp;
                                    <button className='btn btn-danger' onClick={() => deleteProduct(prod.pid)}>
                                    <span><FontAwesomeIcon icon="trash"></FontAwesomeIcon></span>
                                    </button>
                                    &nbsp;
                                    <button className='btn btn-primary' onClick={() => viewProduct(prod.pid)}>
                                    <span><FontAwesomeIcon icon="list"></FontAwesomeIcon></span>
                                    </button>
                            </td>
                          
                        </tr>
                    )
                }
        </tbody>
        </table>
    </div>
   {message && <div className='alert alert-success'>{message}</div>}
</div>
    )
}

export default Product