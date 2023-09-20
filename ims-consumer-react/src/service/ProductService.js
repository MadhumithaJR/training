import axios from "axios";

const PRODUCTS_REST_API_URL='http://localhost:8085/ims/api/products';

// service class to manage Products REST API

class ProductService {

    static getProducts() {
        return axios.get(PRODUCTS_REST_API_URL);
    }

    static createProduct(product) {
        return axios.post(PRODUCTS_REST_API_URL,product);
    }

    static getProductById(productId) {
        return axios.get(PRODUCTS_REST_API_URL+'/'+productId);
    }

    static updateProduct(product,productId) {
        return axios.put(PRODUCTS_REST_API_URL+'/'+productId,product);
    }

    static deleteProduct(productId) {
        return axios.delete(PRODUCTS_REST_API_URL+'/'+productId);
    }

    static async searchProductByName(name) {
        try {
            const response = await axios.get(`${PRODUCTS_REST_API_URL}/search?name=${name}`);
            return response.data;
        } catch(error) {
            console.error('Error searching for products.');
        }
    }
}

export default ProductService;