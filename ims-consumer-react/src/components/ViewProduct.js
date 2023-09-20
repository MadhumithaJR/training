import React, {useState, useEffect} from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import ProductService from '../service/ProductService';

const ViewProduct = () => {

    const history = useNavigate();

    const {id} = useParams();

    const [product, setProduct] = useState({});
    
    useEffect(() => {   // ueh
        ProductService.getProductById(id).then((response) => {
            setProduct(response.data);
        });
    }, [id]); // id triggers re-render of component when they are updated

    const backProduct = () => {
        history('/product');
    }
    
    return (
        <div>
        <br />
        <div className="card col-md-6 offset-md-3">
            <h3 className="text-center">View Product Details</h3><hr/>
            <div className="card-body">
                <div className="row">
                    <label>Product Id:</label>
                    <div class="text-success fw-bolder">{product.pid}</div><hr/>
                </div>
                <div className="row">
                    <label>Product Name:</label>
                    <div class="text-success fw-bolder">{product.name}</div><hr/>
                </div>
                <div className="row">
                    <label>Product Brand:</label>
                    <div class="text-success fw-bolder">{product.brand}</div><hr/>
                </div>
                <div className="row">
                    <label>Product MadeIn:</label>
                    <div class="text-success fw-bolder">{product.madeIn}</div><hr/>
                </div>
                <div className="row">
                    <label>Product Price:</label>
                    <div class="text-success fw-bolder">{product.price}</div><hr/>
                </div>
            </div>
            <div className = "row justify-content-center">
                    <button className="btn btn-info w-auto" onClick={backProduct}>Back To Products</button>
                </div>
        </div>
    </div>
    )

}

export default ViewProduct