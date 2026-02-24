import axios from "axios";

export const getCustomers = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/customers`);
    } catch (err) {
        throw err
    }
}

const createCustomer = async (customer) => {
    return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/customers`, customer);
}

const updateCustomer = async (customer, customerId) => {
    return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/customers/${customerId}`, customer);
}

export const customerProfilePictureUrl = (randomUserGender, customerId) => {
    return `https://randomuser.me/api/portraits/${randomUserGender}/${customerId}.jpg`
}

export const saveCustomer = async (customer, customerId) => {
    try {
        if (customerId != null) {
            return await updateCustomer(customer, customerId)
        } else {
            return await createCustomer(customer)
        }
    } catch (err) {
        throw err;
    }
}

export const deleteCustomer = async (customerId) => {
    try {
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/customers/${customerId}`)
    } catch (err) {
        throw err;
    }
}