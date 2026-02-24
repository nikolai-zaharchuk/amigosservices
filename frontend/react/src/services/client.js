import axios from "axios";

export const getCustomers = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/customers`);
    } catch (e) {
        throw e;
    }
}

export const customerProfilePictureUrl = (randomUserGender, customerId) => {
    return `https://randomuser.me/api/portraits/${randomUserGender}/${customerId}.jpg`
}

export const saveCustomer = async (customer) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/customers`, customer);
    } catch (e) {
        throw e;
    }
}